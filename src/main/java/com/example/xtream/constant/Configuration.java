package com.example.xtream.constant;

public final class Configuration {
    public static final String CORS_ACCEPT_ALL = "*";
    public static final String[] WHITE_LIST = {"/auth/login","/auth/register","/swagger-ui.html","/swagger-ui/**","/v3/api-docs","/v3/api-docs/**"};
    public static final String SWAGGER_SECURITY_SCHEMA = "basicAuth";
    public static final String SWAGGER_TYPE_SCHEMA ="basic";
    public static final String SWAGGER_INVESTORS_PATH = "/api/investors/**";
    public static final String SWAGGER_INVESTORS_GROUP = "Investors";
    public static final String SWAGGER_AUTH_PATH = "/auth/**";
    public static final String SWAGGER_AUTH_GROUP = "Auth";


}
