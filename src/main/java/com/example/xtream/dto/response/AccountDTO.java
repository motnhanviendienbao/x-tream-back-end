package com.example.xtream.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AccountDTO {
    String getProductTree();
    LocalDateTime getAccountCreationDate();
    LocalDate getStartDate();
    Integer getInvestorAccountId();
    String getAccountName();
    String getInvestorName();
    Integer getInvestorId();

}
