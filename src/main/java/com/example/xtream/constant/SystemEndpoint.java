package com.example.xtream.constant;

public class SystemEndpoint {
    public static final String[] WHITE_LIST = {"/auth/login","/auth/register"};
    public static final String[] PROTECTED_LIST = {"/logout","/auth/test"};
    public static final String[] ADMIN_PRIVILEGE_LIST = {"/reset-password"};
}
