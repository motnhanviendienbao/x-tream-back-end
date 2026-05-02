package com.example.xtream.constant;

/**
 * Specify the constants being related to authentication and authorization
 */
public class AuthenticationConstant {

    public enum CorsLevel {
        ACCEPT_ALL("*");

        private String message;
        CorsLevel(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum CacheUserDetail {
        CACHE_NAME("user-detail-cache");

        private String message;
        CacheUserDetail(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum SystemEndpoint {
        WHITE_LIST(new String[]{"/auth/login","/auth/register","/swagger-ui.html","/swagger-ui/**","/v3/api-docs","/v3/api-docs/**"}),
        PROTECTED_LIST(new String[]{"/logout","/auth/test"}),
        ADMIN_PRIVILEGE_LIST(new String[]{"/reset-password"});
        private String[] message;
        SystemEndpoint(String[] message) {
            this.message = message;
        }

        public String[] getMessage() {
            return message;
        }
    }

}
