package com.example.xtream.constant;

import org.apache.catalina.authenticator.SavedRequest;

public final class Configurations {
    public static final String CORS_ACCEPT_ALL = "*";
    public static final String[] WHITE_LIST =
            {
            "/auth/login",
            "/auth/refresh",
            "/auth/register",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**"
            };
    public static final String SWAGGER_SECURITY_SCHEMA = "basicAuth";
    public static final String SWAGGER_TYPE_SCHEMA ="basic";
    public static final String SWAGGER_INVESTORS_PATH = "/api/investors/**";
    public static final String SWAGGER_INVESTORS_GROUP = "Investors";
    public static final String SWAGGER_AUTH_PATH = "/auth/**";
    public static final String SWAGGER_AUTH_GROUP = "Auth";
    public static final String AUTHORIZE_HEADER = "x-verify";
    public static final String AUTHORIZE_SCHEMA_CUSTOM = "check:";
    public static final String DELIM_AUTH = ":";
    public static final String TOKEN_PAYLOAD_KEY_1 = "userId";
    public static final String TOKEN_PAYLOAD_KEY_2 = "userType";
    public static final String TOKEN_PAYLOAD_KEY_3 = "token_create_date";
    public static final int TOKEN_EXPIRE_PERIOD = 60*24;
    public static final String TOKEN_CACHE_KEY = "token@";
    public static final String USER_CACHE_KEY = "user@system@";
    public static final String ROLE_CACHE_KEY = "user@system@role@";
    public static final String INVESTOR_CACHE_KEY = "investor@system@";
    public static final String INVESTMENT_CACHE_KEY = "investment@system@";
    public static final String USER_AUTHORITIES_CACHE_KEY = "user@authorities@";


}
