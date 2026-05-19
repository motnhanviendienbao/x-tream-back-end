package com.example.xtream.security.jwtAuthen.cache;

import lombok.Getter;

public abstract class CacheObject {

    @Getter
    protected long ttl;
    @Getter
    protected Object cacheValue;
    @Getter
    protected String cacheKey;

    public CacheObject(String cacheKey, Object cacheValue, long ttl) {
        this.cacheKey = cacheKey;
        this.cacheValue = cacheValue;
        this.ttl = ttl;
    }

    public abstract boolean isExpire();
}
