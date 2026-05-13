package com.example.xtream.dto.response;

import com.example.xtream.model.common.Status;

import java.time.LocalDate;

/**
 * Interface projection
 * <p>
 * Spring creates proxy class runtime implement interface InvestorSearchDTO
 * Binding data from query to getter
 */
public interface InvestorSearchDTO {
    Long getInvestorId();
    String getInvestorName();
    Status getInvestorStatus();
    LocalDate getInvestorDob();
    String getInvestorPostCode();
    Status getInvestorAccountStatus();
    String getInvestorEmail();
}
