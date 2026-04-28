package com.example.xtream;
import com.example.xtream.constant.CacheUserDetail;
import com.example.xtream.security.basicAuthen.cacheUserDetail.CustomConcurrentMapCache;
import com.example.xtream.security.basicAuthen.cacheUserDetail.CustomSpringCacheBasedUserCache;
import com.example.xtream.security.basicAuthen.service.CustomDaoAuthenticationProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@SpringBootApplication
public class XtreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(XtreamApplication.class, args);
    }

}
