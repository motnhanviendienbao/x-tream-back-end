package com.example.xtream.security.basicAuthen.cache;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Manual Cache
 */
@Service
@Getter
@Lazy
public final class BasicUserCache implements UserCache {

    /**
     * Cache element
     */
    private final Map<String, UserDetails> cache = new HashMap<>(200);

    /**
     * Monitor lock for manipulating cache
     */
    private final Object lock = new Object();

    /**
     * Logger
     */
    private final Logger logger = LogManager.getLogger(BasicUserCache.class);

    /**
     * Constructor attach to new worker thread
     */
    public BasicUserCache() {
        logger.info("[NgocTu-Server]: Initial Cache Basic . . .");
        CleanupTTL task = new CleanupTTL(this);
        Thread worker = new Thread(task);
        worker.setPriority(1);
        worker.start();
    }

    /**
     * getUserFromCache
     *
     * @param username used to place the user in the cache
     * @return  UserDetails or null
     */
    @Override
    public @Nullable UserDetails getUserFromCache(String username) {
        logger.info("[NgocTu-Server]:Cache get user with username: {} . . .", username);
        synchronized (this.lock) {
            return (UserDetails) this.getCache().get(username);
        }
    }

    /**
     * putUserInCache
     *
     * @param user the fully populated <code>UserDetails</code> to place in the cache
     */
    @Override
    public void putUserInCache(UserDetails user) {
        logger.info("[NgocTu-Server]:Cache put user with: {} . . .", user.toString());
        synchronized (this.lock) {
            this.getCache().put(user.getUsername(),user);
        }
    }

    /**
     * removeUserFromCache
     *
     * @param username to be evicted from the cache
     */
    @Override
    public void removeUserFromCache(String username) {
        logger.info("[NgocTu-Server]:Cache remove user with: {} . . .", username);
        synchronized (this.lock) {
            this.getCache().remove(username);
        }
    }


}
