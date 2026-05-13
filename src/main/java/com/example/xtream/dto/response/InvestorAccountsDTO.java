package com.example.xtream.dto.response;

/**
 * This technique was called projection
 */
public interface InvestorAccountsDTO {
    String getInvestorAccountId();

    String getProduct();

    String getProductType();

    String getAccountStatus();

    String getBalance();

    String getUncollectedChagres();

    String getAdviser();
}
