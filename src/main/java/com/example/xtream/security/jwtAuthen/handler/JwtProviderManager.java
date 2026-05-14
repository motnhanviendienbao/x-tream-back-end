package com.example.xtream.security.jwtAuthen.handler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Getter
@RequiredArgsConstructor
public class JwtProviderManager implements AuthenticationManager {

    private final Set<AuthenticationProvider> providers;
    private static final Logger logger = LogManager.getLogger(JwtProviderManager.class);
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException
    {
        AuthenticationProvider provider = getProviders()
                .stream()
                .filter((pro)-> pro.supports(authentication.getClass()))
                .findFirst().orElseThrow(()-> new RuntimeException("No Provider supports at all") );

        return provider.authenticate(authentication);
    }
}
