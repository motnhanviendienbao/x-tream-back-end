package com.example.xtream.security.jwtAuthen.cache;

import com.example.xtream.service.LocalCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public final class LocalCacheSystem implements LocalCache {
    private final Map<String, CacheObject> cacheResource = new HashMap<String,CacheObject>(500);
    private final Object lock = new Object();
    public static LocalCacheSystem localCacheSystem;

    private LocalCacheSystem()
    {
        // create worker do TTL attach
        Thread workerTTL = new Thread(new Worker(this.getCacheResource()));
        // start async in this case,
        // was call in terminology: independence run task
        workerTTL.start();
    }

    public static LocalCacheSystem getInstance() {
        return Objects.requireNonNullElseGet(localCacheSystem, LocalCacheSystem::new);
    }

    @Override
    public CacheObject getCache(String cacheKey)
    {
        synchronized (lock) {
            return this.cacheResource.get(cacheKey);
        }
    }

    @Override
    public void putCache(CacheObject cacheObject)
    {

        if(!(cacheObject.getCacheKey() != null
                && cacheObject.getCacheValue() != null
                && cacheObject.ttl != 0L)) return;
        synchronized (lock) {
            this.cacheResource.put(cacheObject.cacheKey, cacheObject);
        }
    }

    @Override
    public void removeCache(String cacheKey)
    {
        if (StringUtils.isEmpty(cacheKey)) return;
        synchronized (lock) {
            this.cacheResource.remove(cacheKey);
        }
    }

    public Map<String, CacheObject> getCacheResource() {
        return this.cacheResource;
    }
}
