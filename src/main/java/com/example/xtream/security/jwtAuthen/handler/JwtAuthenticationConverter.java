package com.example.xtream.security.jwtAuthen.handler;
import com.example.xtream.constant.Configurations;
import com.example.xtream.constant.ErrorMessages;
import com.example.xtream.security.jwtAuthen.token.JwtAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;

@Service
public class JwtAuthenticationConverter implements AuthenticationConverter {
    private static final Logger logger = LogManager.getLogger(JwtAuthenticationConverter.class);

    @Override
    public @Nullable Authentication convert(HttpServletRequest request)
    {
        // extract auth data from Authorization header
        String header = request.getHeader(Configurations.AUTHORIZE_HEADER);
        if (header == null) {
            logger.info("AUTHORIZE_HEADER Is Null");
            return null;
        }
        // checking schema: such as basic, bearer, this using custom schema
        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, Configurations.AUTHORIZE_SCHEMA_CUSTOM)) {
            logger.info("AUTHORIZE_SCHEMA_CUSTOM Is Wrong");
            return null;
        }
        // checking having schema without payload
        if (header.equalsIgnoreCase(Configurations.AUTHORIZE_SCHEMA_CUSTOM)) {
            logger.info("AUTHORIZE_SCHEMA_CUSTOM Payload Not Exist");
            throw new BadCredentialsException(ErrorMessages.EMPTY_AUTHORIZE_SCHEMA_CUSTOM);
        }

        String token = header;
        int delim = token.indexOf(Configurations.DELIM_AUTH);
        if (delim == -1) {
            logger.info("INVALID_AUTHORIZE_SCHEMA_CUSTOM");
            throw new BadCredentialsException(ErrorMessages.INVALID_AUTHORIZE_SCHEMA_CUSTOM);
        }

        String schemaAuthentication = token.substring(0, delim);
        logger.info("AUTHORIZE_SCHEMA_CUSTOM: {}",schemaAuthentication);
        String tokenPlainText = token.substring(delim + 1);
        logger.info("AUTHORIZE_SCHEMA_CUSTOM Payload (jwt): {}",tokenPlainText);

        // return the Token Auth Object in status still not being auth for process in the next steps
        return new JwtAuthenticationToken(Collections.emptyList(),tokenPlainText);
    }
}
