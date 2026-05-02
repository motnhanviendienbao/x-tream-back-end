package com.example.xtream.config.beanConfig;

import com.example.xtream.constant.AuthenticationConstant;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Turn @EnableCaching
 * For allowing to use cache in spring
 */
@Configuration
@EnableCaching
public class AuthBeanConfig {

    /**
     * ConcurrentMapCacheManager actually use a concurrent hash map like a cache to store many other caches in side it
     * Main: one parent cache stores many children cache , and identify by key is cache children name and value is other cache.
     * So, you can have many kind of caches in one cache parent.
     *
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(
                AuthenticationConstant
                .CacheUserDetail
                .CACHE_NAME
                .getMessage());
    }

    /**
     * Declare Bean for password encode and decode
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
