package com.example.xtream.security.jwtAuthen.handler;
import com.example.xtream.constant.ErrorMessages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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
                .filter(Objects::nonNull)
                .filter((pro) -> pro.supports(authentication.getClass()))
                .findFirst().orElseThrow(()-> new BadCredentialsException(ErrorMessages.NO_AUTHENTICATION_PROVIDER) );

        return provider.authenticate(authentication);
    }
}
