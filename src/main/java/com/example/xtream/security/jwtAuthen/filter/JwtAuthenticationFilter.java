package com.example.xtream.security.jwtAuthen.filter;

import com.example.xtream.constant.ErrorMessages;
import com.example.xtream.security.jwtAuthen.handler.JwtAuthenticationConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * AUTH MECHANIC:
 * 1. Token used for verifying token was created from this system or not (ex: jwt)?
 * If Pass?
 * 2. Got first params auth to check identify valid or not (ex: username) [first round]
 * If Pass?
 * 3. Got info related this authenticated like user detail includes the second auth params from source of true.
 * If Pass?
 * 4. Got the second param auth from source of truth to check with params second on the token (ex: password in token vs password in db) [second round]
 * If Pass?
 * 5. Successful authentication, then get the authorities in user detail for purpose of authorize.
 * Done.
 */
@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        try {
            // get token in header and convert
            Authentication authRequestObject = jwtAuthenticationConverter.convert(request);
            // check fast fail to decide process or not
            if(authRequestObject == null)
            {
                logger.info("authRequestObject is null, go next filter");
                filterChain.doFilter(request,response);
                return;
            }
            // process authentication
            Authentication result = authenticationManager.authenticate(authRequestObject);
            SecurityContextHolder.getContext().setAuthentication(result);
            // todo: test checking authorities
            SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().filter(Objects::nonNull).forEach(System.out::println);
            // go next
            filterChain.doFilter(request, response);
            return;
        } catch ( CredentialsExpiredException ex ) {
            response.addHeader("TOKEN_EXPIRE","TRUE");
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }
    }
}
