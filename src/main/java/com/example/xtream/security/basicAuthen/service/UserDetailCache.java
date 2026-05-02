package com.example.xtream.security.basicAuthen.service;

import com.example.xtream.constant.AuthenticationConstant;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * This defines used-cache for BasicAuthentication
 */
@Service
@Getter
public class UserDetailCache implements UserCache {

    private final Cache cache;
    public static final Logger logger = LogManager.getLogger(UserDetailCache.class);

    /**
     * Constructor injection
     *
     * @Autowired is optional for Constructor injection, but being forced for Setter and field injection
     * @param cacheManager Instance to get cache
     * Spring not create cache object alone, it got create internally and managed by cacheManager
     */
    @Autowired
    public UserDetailCache(CacheManager cacheManager) {
        this.cache = cacheManager.getCache(AuthenticationConstant.CacheUserDetail.CACHE_NAME.getMessage());
        logger.info("[NGOC TU SERVER]: Init UserCacheInBasicAuthentication");
    }

    /**
     * Check fast-fail by Assert (Util function) providing by spring
     */
    @PostConstruct
    public void afterSetProperties() {
        logger.info("[NGOC TU SERVER]: Check Fail-Fast At afterSetProperties() . . . ");
        Assert.notNull(this.getCache(),"UserCache must be exist");
    }

    /**
     * Get UserDetail object from cache
     *
     * @param username the used to place the user in the cache
     * @return object UserDetails
     */
    @Override
    public @Nullable UserDetails getUserFromCache(String username) {
        Cache.ValueWrapper element = (username != null) ? this.cache.get(username) : null;
        logger.info("[NGOC TU SERVER]: Get From Cache: {}", (element != null) ? (UserDetails) element.get() : null);
        return (element != null) ? (UserDetails) element.get() : null;
    }

    /**
     * putUserInCache
     *
     * @param user the fully populated <code>UserDetails</code> to place in the cache
     */
    @Override
    public void putUserInCache(UserDetails user) {
        logger.info("[NGOC TU SERVER]: PutUserInCache: {}", user);
        logger.info("[NGOC TU SERVER]: PutUserInCache - Authorities: {}", user.getAuthorities());
        this.cache.put(user.getUsername(), user);
    }

    /**
     * RemoveUserFromCache
     *
     * @param user UserDetail providing by loadByUsername
     */
    public void removeUserFromCache(UserDetails user) {
        logger.info("[NGOC TU SERVER]: RemoveUserFromCache: {}", user);
        this.removeUserFromCache(user.getUsername());
    }

    /**
     * RemoveUserFromCache
     *
     * @param username to be evicted from the cache
     */
    @Override
    public void removeUserFromCache(String username) {
        logger.info("[NGOC TU SERVER]: RemoveUserFromCache: {}", username );
        this.cache.evict(username);
    }
}
