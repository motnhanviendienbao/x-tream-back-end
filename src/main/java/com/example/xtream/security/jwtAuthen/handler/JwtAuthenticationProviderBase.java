package com.example.xtream.security.jwtAuthen.handler;
import com.example.xtream.constant.Configurations;
import com.example.xtream.security.jwtAuthen.token.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class JwtAuthenticationProviderBase implements AuthenticationProvider {
    private final static Logger logger = LogManager.getLogger(JwtAuthenticationProviderBase.class);

    public @Nullable Authentication authenticate(Authentication authentication)
            throws AuthenticationException
    {
        // get plain text token
        String tokenPlainText = authentication.getDetails().toString();
        logger.info("Plain Text Token: {}",tokenPlainText);
        // valid token
        boolean tokenValidStatus = validateToken(tokenPlainText);
        logger.info("Token Validate Status: {}",tokenValidStatus);
        if(!tokenValidStatus) return null;

        Claims claims = getJwtClaims(tokenPlainText);
        if (Objects.isNull(claims)) return null;

        Long userId = claims.get(Configurations.TOKEN_PAYLOAD_KEY_1, Long.class);
        String userRole = claims.get(Configurations.TOKEN_PAYLOAD_KEY_2, String.class);

        // get authorities
        List<SimpleGrantedAuthority> authorities = getAuthorities(userId,userRole);
        authorities = Objects.requireNonNullElse(authorities,Collections.emptyList());

        // return success authentication object
        return new JwtAuthenticationToken(authorities);
    }

    public boolean supports(Class<?> authentication)
    {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    protected abstract boolean validateToken(String tokenPlainText);
    protected abstract List<SimpleGrantedAuthority> getAuthorities(Long userId, String userType);
    protected abstract Claims getJwtClaims(String token);
}
