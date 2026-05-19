package com.example.xtream.security.jwtAuthen.cache;

import lombok.Getter;

import java.time.Instant;

public class CacheObjectImpl extends CacheObject{

    public CacheObjectImpl(String cacheKey, Object cacheValue, long ttl) {
        super(cacheKey, cacheValue, ttl);
    }

    public boolean isExpire() {
        return this.getTtl() < Instant.now().toEpochMilli();
    }

}
