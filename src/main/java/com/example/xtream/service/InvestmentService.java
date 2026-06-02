package com.example.xtream.service;

import com.example.xtream.dto.request.UpdateAccountInvestmentRequestDTO;
import com.example.xtream.dto.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

public interface InvestmentService {
    ResponseDTO getAllInvestments();
    ResponseDTO getInvestments(int investorId, int accountId, String search, Pageable pageable);
    ResponseDTO updateInvestment(int investorId, int accountId, int investmentId, UpdateAccountInvestmentRequestDTO dto);
    ResponseDTO deleteInvestment(int investorId, int accountId, int investmentId);


}
