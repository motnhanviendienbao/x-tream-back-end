package com.example.xtream.security.jwtAuthen.filter;

import com.example.xtream.security.jwtAuthen.handler.JwtAuthenticationConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get token in header and convert
        Authentication authRequestObject = jwtAuthenticationConverter.convert(request);  ;
        // check fast fail to decide process or not
        if(authRequestObject == null) {
            filterChain.doFilter(request,response);
            return;
        }
        // process authentication
        Authentication result = authenticationManager.authenticate(authRequestObject);
         SecurityContextHolder.getContext().setAuthentication(result);
        // go next
        filterChain.doFilter(request, response);
    }
}
