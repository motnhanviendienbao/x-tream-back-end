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
        INVESTOR_ACCOUNT_ID_NOT_FOUND("Investor Account Id Not Found"),
        PRODUCT_NOT_FOUND("Product Not Found"),
        INVESTMENT_NOT_FOUND("Investment Not Found"),
        EMAIL_ALREADY_EXISTS("Email Already Exists"),
        TFN_ALREADY_EXISTS("Tax File Number Already Exists"),
        INVESTMENT_STRATEGY_MUST_TOTAL_100("Investment Strategy Must Total 100%"),
        INVESTMENTS_REQUIRED("At Least One Investment Is Required");

        private final String message;
        Investor(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
