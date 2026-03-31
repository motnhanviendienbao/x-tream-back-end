package com.example.xtream.security.cacheUserDetail;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

import com.example.xtream.service.impl.AuthServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.Nullable;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.core.serializer.support.SerializationDelegate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class CustomConcurrentMapCache extends AbstractValueAdaptingCache {
    private final String name;
    private final ConcurrentMap<Object, Object> store;
    private final @Nullable SerializationDelegate serialization;
    private static final Logger logger = LogManager.getLogger(CustomConcurrentMapCache.class);
    public CustomConcurrentMapCache(String name) {
        this(name, new ConcurrentHashMap(256), true);
    }

    public CustomConcurrentMapCache(String name, boolean allowNullValues) {
        this(name, new ConcurrentHashMap(256), allowNullValues);
        logger.debug("[NGOC-TU] INIT CustomConcurrentMapCache with name: " + name);

    }
    public CustomConcurrentMapCache(String name, ConcurrentMap<Object, Object> store, boolean allowNullValues) {
        this(name, store, allowNullValues, (SerializationDelegate)null);
    }
    protected CustomConcurrentMapCache(String name, ConcurrentMap<Object, Object> store, boolean allowNullValues, @Nullable SerializationDelegate serialization) {
        super(allowNullValues);
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(store, "Store must not be null");
        this.name = name;
        this.store = store;
        this.serialization = serialization;
    }
    public final boolean isStoreByValue() {
        return this.serialization != null;
    }
    public final String getName() {
        return this.name;
    }
    public final ConcurrentMap<Object, Object> getNativeCache() {
        return this.store;
    }
    protected @Nullable Object lookup(Object key) {
        logger.debug("[NGOC TU]- do in LOOKUP this.store.get(key) with key: "+key+"-"+"value: "+ this.store.get(key));
        return this.store.get(key);
    }
    public <T> @Nullable T get(Object key, Callable<T> valueLoader) {
        return (T) this.fromStoreValue(this.store.computeIfAbsent(key, (k) -> {
            try {
                return this.toStoreValue(valueLoader.call());
            } catch (Throwable var5) {
                throw new ValueRetrievalException(key, valueLoader, var5);
            }
        }));
    }
    protected @Nullable Object fromStoreValue(@Nullable Object storeValue) {
        if (storeValue != null && this.serialization != null) {
            try {
                return super.fromStoreValue(this.serialization.deserializeFromByteArray((byte[])storeValue));
            } catch (Throwable var3) {
                throw new IllegalArgumentException("Failed to deserialize cache value '" + String.valueOf(storeValue) + "'", var3);
            }
        } else {
            return super.fromStoreValue(storeValue);
        }
    }
    protected Object toStoreValue(@Nullable Object userValue) {
        Object storeValue = super.toStoreValue(userValue);
        if (this.serialization != null) {
            try {
                return this.serialization.serializeToByteArray(storeValue);
            } catch (Throwable var4) {
                throw new IllegalArgumentException("Failed to serialize cache value '" + String.valueOf(userValue) + "'. Does it implement Serializable?", var4);
            }
        } else {
            return storeValue;
        }
    }

    public @Nullable CompletableFuture<?> retrieve(Object key) {
        Object value = this.lookup(key);
        return value != null ? CompletableFuture.completedFuture(this.isAllowNullValues() ? this.toValueWrapper(value) : this.fromStoreValue(value)) : null;
    }

    public <T> CompletableFuture<T> retrieve(Object key, Supplier<CompletableFuture<T>> valueLoader) {
        return (CompletableFuture<T>) CompletableFuture.supplyAsync(() -> {
            return this.fromStoreValue(this.store.computeIfAbsent(key, (k) -> {
                return this.toStoreValue(((CompletableFuture)valueLoader.get()).join());
            }));
        });
    }

    public void put(Object key, @Nullable Object value) {
        this.store.put(key, this.toStoreValue(value));
    }

    public Cache.@Nullable ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
        Object existing = this.store.putIfAbsent(key, this.toStoreValue(value));
        return this.toValueWrapper(existing);
    }

    public void evict(Object key) {
        this.store.remove(key);
    }

    public boolean evictIfPresent(Object key) {
        return this.store.remove(key) != null;
    }

    public void clear() {
        this.store.clear();
    }

    public boolean invalidate() {
        boolean notEmpty = !this.store.isEmpty();
        this.store.clear();
        return notEmpty;
    }
}
