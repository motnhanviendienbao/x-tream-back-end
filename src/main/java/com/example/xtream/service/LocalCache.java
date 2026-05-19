package com.example.xtream.service;

import com.example.xtream.security.jwtAuthen.cache.CacheObject;

public interface LocalCache {
    CacheObject getCache(String cacheKey);
    void putCache(CacheObject cacheObject);
    void removeCache(String cacheKey);
}
