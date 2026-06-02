package com.example.xtream.security.jwtAuthen.handler;

import com.example.xtream.service.SystemService;
import com.example.xtream.service.TokenAuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationProvider extends JwtAuthenticationProviderBase {

    private final TokenAuthenticationService tokenService;
    private final SystemService systemService;
    private static final Logger logger = LogManager.getLogger(JwtAuthenticationProvider.class);

    @Override
    protected boolean validateToken(String tokenPlainText)
    {
        return tokenService.validateToken(tokenPlainText);
    }

    @Override
    protected List<SimpleGrantedAuthority> getAuthorities(int userId, String userType) {
        return systemService.getAuthoritiesByUserId(userId);
    }

    @Override
    protected Claims getJwtClaims(String token) {
        if (StringUtils.isBlank(token)) return null;
        Jws<Claims> claimsJwt = tokenService.parseToken(token);
        return claimsJwt.getPayload();
    }
}
