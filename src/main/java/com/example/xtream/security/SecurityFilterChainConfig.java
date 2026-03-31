package com.example.xtream.security;
import com.example.xtream.constant.CorsLevel;
import com.example.xtream.constant.SystemEndpoint;
import com.example.xtream.constant.SystemRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;
public class SecurityFilterChainConfig {
    @Configuration
    @EnableMethodSecurity(prePostEnabled = true)
    public static class WebSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(
                HttpSecurity http,
                AuthenticationManager authenticationManager,
                AuthenticationEntryPoint authenticationEntryPoint) throws Exception
        {
                http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(cors -> cors.configurationSource(request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(List.of(CorsLevel.ACCEPT_ALL));
                        config.setAllowedMethods(List.of(CorsLevel.ACCEPT_ALL));
                        config.setAllowedHeaders(List.of(CorsLevel.ACCEPT_ALL));
                        return config;
                    }))
                    .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .exceptionHandling(configurer -> configurer.authenticationEntryPoint(authenticationEntryPoint))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(SystemEndpoint.ADMIN_PRIVILEGE_LIST).hasAuthority(SystemRole.ADMIN)
                            .requestMatchers(SystemEndpoint.WHITE_LIST).permitAll()
                            .requestMatchers(SystemEndpoint.PROTECTED_LIST).authenticated()
                            .anyRequest().authenticated()
                    )
                    .authenticationManager(authenticationManager)
                    .httpBasic(Customizer.withDefaults());
                return http.build();
        }
    }
}
