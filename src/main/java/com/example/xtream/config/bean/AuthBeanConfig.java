package com.example.xtream.config.bean;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Turn @EnableCaching
 * For allowing to use cache in spring
 */
//@EnableCaching
@Configuration
public class AuthBeanConfig {

    /*
     * ConcurrentMapCacheManager actually use a concurrent hash map like a cache to store many other caches in side it
     * Main: one parent cache stores many children cache , and identify by key is cache children name and value is other cache.
     * So, you can have many kind of caches in one cache parent.
     *
     * @return CacheManager
     *
     *     @Bean
     *     public CacheManager cacheManager() {
     *         return new ConcurrentMapCacheManager(
     *                 AuthenticationConstant
     *                 .CacheUserDetail
     *                 .CACHE_NAME
     *                 .getMessage());
     *     }
     */


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
