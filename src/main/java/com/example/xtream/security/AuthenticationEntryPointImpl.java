package com.example.xtream.security;
import com.example.xtream.constant.ErrorMessages;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint
{
    private static final Logger logger = LogManager.getLogger(AuthenticationEntryPointImpl.class);

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response, final AuthenticationException authException)
            throws IOException, ServletException
    {
        String error = authException.getMessage();
        logger.info("Got Hold Back In Commence - Message Ex : {}",authException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        if( authException instanceof CredentialsExpiredException){
            error = ErrorMessages.TOKEN_EXPIRE_CODE;
        }
        response.getWriter().write("""
        {
            "error": "%s"
        }
        """.formatted(error));
    }
}
