package com.example.xtream.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class SessionRequestFilter extends GenericFilter {
    public static final Logger log = LogManager.getLogger(SessionRequestFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        // set cookie
//        Cookie newCookie = new Cookie("x-time", OffsetDateTime.now().toString());
//        newCookie.setPath("/");
//        newCookie.setHttpOnly(true);
//        newCookie.setMaxAge(60*60);
//
//        // check cookie
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//        String uri = httpRequest.getRequestURI();
//
//        if(
//                !(uri.startsWith("/auth/login") || uri.startsWith("/auth/register")
//                        || uri.startsWith("/reset-password") || uri.startsWith("/user/logout")))
//        {
//            // check session
//            Cookie[] cookies = httpRequest.getCookies();
//            if (cookies != null)
//            {
//                for (Cookie cookie : cookies)
//                {
//                    if ("x-time".equals(cookie.getName()))
//                    {
//                        OffsetDateTime oldTime = OffsetDateTime.parse(cookie.getValue());
//                        if(oldTime.plusMinutes(15).isBefore(OffsetDateTime.now()))
//                        {
//                            httpResponse.setStatus(440);
//                            httpResponse.getWriter().write("Session Expire");
//                            return;
//                        }
//                        else
//                        {
//                            httpResponse.addCookie(newCookie);
//                        }
//                    }
//                }
//            }
//            else
//            {
//                httpResponse.setStatus(440);
//                httpResponse.getWriter().write("Session Expire");
//                return;
//            }
//        }

        filterChain.doFilter(servletRequest,servletResponse);

    }
}
