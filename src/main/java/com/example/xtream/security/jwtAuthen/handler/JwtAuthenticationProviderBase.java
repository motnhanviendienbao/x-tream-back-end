package com.example.xtream.security.jwtAuthen.handler;
import com.example.xtream.security.jwtAuthen.token.JwtAuthenticationToken;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import java.util.Collections;

public abstract class JwtAuthenticationProviderBase implements AuthenticationProvider {

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException
    {
        // get plain text token
        String tokenPlainText = authentication.getDetails().toString();
        System.out.printf("NGOC TU LOGGER: tokenPlainText: %s%n",tokenPlainText);
        // valid token
        boolean tokenValidStatus = validateToken(tokenPlainText);
        System.out.printf("NGOC TU LOGGER: tokenValidStatus: %b%n",tokenValidStatus);

        if(!tokenValidStatus) return null;
        return new JwtAuthenticationToken(Collections.emptyList());
    }

    public boolean supports(Class<?> authentication)
    {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    protected abstract boolean validateToken(String tokenPlainText);
}
