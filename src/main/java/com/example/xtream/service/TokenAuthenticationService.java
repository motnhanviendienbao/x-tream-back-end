package com.example.xtream.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface TokenAuthenticationService {
    String createToken(String username);
    Boolean validateToken(String token);
    Jws<Claims> parseToken( String token);

}
