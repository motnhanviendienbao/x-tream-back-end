package com.example.xtream.service;

import com.example.xtream.dto.request.CreateInvestorDTO;
import com.example.xtream.dto.request.UpdateInvestorDetailsDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.common.Status;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface InvestorService {
    ResponseDTO searchInvestors(Optional<Integer> investorId, Optional<Integer> investorAccountId,
                                Optional<String> investorEmail, Optional<String> investorName,
                                Optional<Status> investorStatus, Optional<LocalDate> from, Optional<LocalDate> to,
                                Optional<Status> activeAccountOnly, Optional<String> postcode, Pageable pageable);

    ResponseDTO createInvestor(CreateInvestorDTO createInvestorRequestDTO);
    ResponseDTO getInvestorDetails(Long investorId);
    ResponseDTO updateInvestorDetails(UpdateInvestorDetailsDTO updateInvestorDetailsDTO);
    ResponseDTO getInvestorAccountsByInvestorId(Long investorId,Pageable pageable);
}
