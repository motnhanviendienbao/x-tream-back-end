package com.example.xtream.service;

import com.example.xtream.dto.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface InvestorService {
    ResponseDTO searchInvestors(Optional<Integer> investorId, Optional<Integer> investorAccountId,
                                Optional<String> investorEmail, Optional<String> investorName,
                                Optional<Integer> investorStatus, Optional<LocalDate> from, Optional<LocalDate> to,
                                Optional<Boolean> activeAccountOnly, Optional<String> postcode, Pageable pageable);
}
