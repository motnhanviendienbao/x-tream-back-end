package com.example.xtream.constant;

public final class ErrorMessages {

    public enum Auth {
        INVALID_USERNAME("Invalid Username"),
        INVALID_PASSWORD("Invalid Password"),
        INVALID_USERNAME_OR_PASSWORD("Invalid Username Or Password"),
        USERNAME_ALREADY_EXIST("Username Already Exist"),
        USERNAME_PASSWORD_IS_REQUIRE("Username Or Password Is Require"),
        CREDENTIAL_NOT_FOUND("Credential Not Found"),


        USER_NOT_EXIST("User Not Exist");


        private String message;
        Auth(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum Investor {
        INVESTOR_ID_NOT_FOUND("Investor Id Not Found"),
        INVESTOR_ACCOUNT_ID_NOT_FOUND("Investor Account Id Not Found");




        private String message;
        Investor(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
