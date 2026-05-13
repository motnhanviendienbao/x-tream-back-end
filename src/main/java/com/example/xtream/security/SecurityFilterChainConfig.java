package com.example.xtream.security;

import com.example.xtream.constant.Configuration;
import org.springframework.context.annotation.Bean;
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
 * This class: config filter for spring security,
 * what you define here would appear on spring security filter chain,
 * that request would go through. It always has some filter being set default like:
 * DEFAULT FILTERS:
 * 1. SecurityContextHolderFilter: Loads/stores SecurityContext,Makes authentication available via SecurityContextHolder
 * 2. AnonymousAuthenticationFilter: If no user → creates anonymous authentication,Prevents null authentication.
 * 3. ExceptionTranslationFilter: Catches authentication/authorization errors,Calls your AuthenticationEntryPoint.
 * 4. AuthorizationFilter/FilterSecurityInterceptor: checks .anyRequest().authenticated().
 * 5. CsrfFilter: Special case:Enabled by default,Exists unless you explicitly disable.
 * OPTIONAL FILTERS:
 * These DO NOT exist unless you configure them:
 * 1. httpBasic()
 * 2. formLogin()
 * 3. rememberMe()
 * 4. OAuth2
 * 5. JWT (custom)
 * OTHER OPTIONAL FILTERS:
 * 1. CorsFilter: cors()
 * 2. LogoutFilter
 *
 *
 * @EnableMethodSecurity(prePostEnabled = true,securedEnabled = true)
 * <p>
 * turn on check permission:
 * allow you to use @PreAuthorize, @PostAuthorize, @Secured
 */
@org.springframework.context.annotation.Configuration
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
                // csrf is default filter got set to enable
                .csrf(AbstractHttpConfigurer::disable)
                // cors is optional filter, but must have for working with browser
                // by setting what cors config you want to.
                // CORS allows different origins — but only if backend explicitly permits them
                // same origin no need Cors.
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(Configuration.CORS_ACCEPT_ALL));
                    config.setAllowedMethods(List.of(Configuration.CORS_ACCEPT_ALL));
                    config.setAllowedHeaders(List.of(Configuration.CORS_ACCEPT_ALL));
                    return config;
                }))
                // sessionManagement is config session mode.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // exceptionHandling is default filter, got the implement bean for commence function.
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(Configuration.WHITE_LIST).permitAll()
                        .anyRequest().permitAll()
                )
                // httpBasic is an optional filter, using when app use basic authentication mechanic
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}