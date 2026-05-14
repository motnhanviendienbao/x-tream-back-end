package com.example.xtream.security.jwtAuthen.handler;

import com.example.xtream.service.TokenAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationProvider extends JwtAuthenticationProviderBase {

    private final TokenAuthenticationService tokenService;
    private static final Logger logger = LogManager.getLogger(JwtAuthenticationProvider.class);

    @Override
    protected boolean validateToken(String tokenPlainText)
    {
        return tokenService.validateToken(tokenPlainText);
    }
}
