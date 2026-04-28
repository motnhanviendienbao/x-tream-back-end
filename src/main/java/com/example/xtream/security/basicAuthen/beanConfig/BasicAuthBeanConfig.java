package com.example.xtream.security.basicAuthen.beanConfig;

import com.example.xtream.constant.CacheUserDetail;
import com.example.xtream.security.basicAuthen.cacheUserDetail.CustomConcurrentMapCache;
import com.example.xtream.security.basicAuthen.cacheUserDetail.CustomSpringCacheBasedUserCache;
import com.example.xtream.security.basicAuthen.service.CustomDaoAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BasicAuthBeanConfig {

    private final UserDetailsService tokenBasedUserDetailService;

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new CustomDaoAuthenticationProvider(tokenBasedUserDetailService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserCache(new CustomSpringCacheBasedUserCache(new CustomConcurrentMapCache(CacheUserDetail.CACHE_NAME,false)));
        ProviderManager manager = new ProviderManager(provider);
        manager.setEraseCredentialsAfterAuthentication(false);
        return manager;
    }
}
