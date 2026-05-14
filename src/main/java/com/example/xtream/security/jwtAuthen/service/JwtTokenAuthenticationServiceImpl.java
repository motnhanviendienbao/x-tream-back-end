package com.example.xtream.security.jwtAuthen.service;

import com.example.xtream.service.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtTokenAuthenticationServiceImpl implements TokenAuthenticationService {

    private final ObjectMapper objectMapper;
    @Value("${spring.app.private}")
    private String privateKey;
    private final static Logger logger = LogManager.getLogger(JwtTokenAuthenticationServiceImpl.class);

    public String createToken(final String username) {
        try {
            // step 1: prepare payload.
            Map<String, Object> tokenData = new HashMap<>();
            tokenData.put("clientType", "user");
            tokenData.put("username", username);
            tokenData.put("token_create_date", LocalDateTime.now());
            // step 2: prepare expiration
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 15);
            // step 3: get private key
            SecretKey secKey = Keys.hmacShaKeyFor(privateKey.getBytes());

            // final: generate token
            return Jwts.builder()
                    .expiration(calendar.getTime())
                    .claims(tokenData)
                    .signWith(secKey, Jwts.SIG.HS512)
                    .json(new JacksonSerializer(objectMapper)).compact();

        } catch (InvalidKeyException e) {
            logger.error(e.getMessage());
        }
        return StringUtils.EMPTY;
    }

    public Boolean validateToken(final String token) {
        // step 1: set default status
        boolean flag = false;

        try {
            // step 2: get payload from jwt
            Jws<Claims> claimToken = parseToken(token);

            // if token is not valid, parse return null
            if (ObjectUtils.isEmpty(claimToken)) return flag;

            // passed all validate
            flag = true;

        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SecurityException
                 | IllegalArgumentException e) {
            logger.error(e.getMessage());
        }

        return flag;
    }

    public Jws<Claims> parseToken( String token) {
        if (ObjectUtils.isEmpty(token)) return null;

        try {
            // get private key
            SecretKey secKey = Keys.hmacShaKeyFor(privateKey.getBytes());
            // get jwt parser engine
            @SuppressWarnings({ "rawtypes", "unchecked" })
            JwtParser jwtParser = Jwts.parser()
                    .verifyWith(secKey)
                    .json(new JacksonDeserializer(objectMapper))
                    .build();
            // parse jwt process
            return jwtParser.parseSignedClaims(token);
        } catch (ExpiredJwtException | UnsupportedJwtException
                 | MalformedJwtException | IllegalArgumentException e) {
            logger.error("Error during parsing token: {}", e);
            return null;
        }
    }
}
