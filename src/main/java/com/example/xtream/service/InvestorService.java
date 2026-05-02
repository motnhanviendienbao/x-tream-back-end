package com.example.xtream.service;

import com.example.xtream.dto.request.CreateInvestorRequestDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.enums.Status;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface InvestorService {
    ResponseDTO searchInvestors(Optional<Integer> investorId, Optional<Integer> investorAccountId,
                                Optional<String> investorEmail, Optional<String> investorName,
                                Optional<Status> investorStatus, Optional<LocalDate> from, Optional<LocalDate> to,
                                Optional<Status> activeAccountOnly, Optional<String> postcode, Pageable pageable);

    ResponseDTO createInvestor(CreateInvestorRequestDTO createInvestorRequestDTO);
}
