package com.example.xtream.security;

import com.example.xtream.constant.AuthenticationConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

/**
 * @EnableMethodSecurity(prePostEnabled = true,securedEnabled = true)
 * <p>
 * turn on check permission:
 * allow you to use @PreAuthorize, @PostAuthorize, @Secured
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityFilterChainConfig {

    /**
     * Config security chain for authentication and authorize
     *
     * @param http  variable contains rule config
     * @param authenticationEntryPoint  handle exception in auth
     *
     * @return suitable filter depends on config like: basic or bearer with http mode
     * @throws Exception Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationEntryPoint authenticationEntryPoint
    ) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(AuthenticationConstant.CorsLevel.ACCEPT_ALL.getMessage()));
                    config.setAllowedMethods(List.of(AuthenticationConstant.CorsLevel.ACCEPT_ALL.getMessage()));
                    config.setAllowedHeaders(List.of(AuthenticationConstant.CorsLevel.ACCEPT_ALL.getMessage()));
                    return config;
                }))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AuthenticationConstant.SystemEndpoint.WHITE_LIST.getMessage()).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}