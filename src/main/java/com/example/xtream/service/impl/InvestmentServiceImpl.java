package com.example.xtream.service.impl;

import com.example.xtream.constant.ErrorMessages;
import com.example.xtream.dto.request.UpdateAccountInvestmentRequestDTO;
import com.example.xtream.dto.response.AccountInvestmentDTO;
import com.example.xtream.dto.response.InvestmentsTreeDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.investor.InvestorAccountInvestment;
import com.example.xtream.repository.InvestmentRepository;
import com.example.xtream.repository.InvestorAccountInvestmentRepository;
import com.example.xtream.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Investment resource service
 */
@Service
@RequiredArgsConstructor
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final InvestorAccountInvestmentRepository investorAccountInvestmentRepository;
    private static final Logger logger = LogManager.getLogger(InvestmentServiceImpl.class);


    /**
     * Get all type of investments
     * @return ResponseDTO contains List of investment
     */
    @Override
    @Transactional
    public ResponseDTO getAllInvestments() {
        List<InvestmentsTreeDTO> response = investmentRepository.findAllInvestmentsActive();
        return ResponseDTO
                .builder()
                .response(response)
                .build();
    }

    @Override
    @Transactional
    public ResponseDTO getInvestments(int investorId, int accountId, String search, Pageable pageable) {
        String name = null;
        int accountInvestmentId = 0;

        try {
            accountInvestmentId = Integer.parseInt(search);
        } catch (NumberFormatException ex) {
            name = search;
        }

        Page<AccountInvestmentDTO> accountInvestments = investorAccountInvestmentRepository.findInvestments(investorId,accountId,name,accountInvestmentId,pageable);

        return ResponseDTO.builder()
                .response(accountInvestments.getContent())
                .totalElements(accountInvestments.getTotalElements())
                .totalPages(accountInvestments.getTotalPages())
                .currentPage(accountInvestments.getNumber())
                .build();
    }

    @Override
    @Transactional
    public ResponseDTO updateInvestment(int investorId, int accountId, int investmentId, UpdateAccountInvestmentRequestDTO dto) {
        InvestorAccountInvestment investment = investorAccountInvestmentRepository
                .findById(investmentId)
                .orElseThrow(()->  new RuntimeException(ErrorMessages.NOT_EXIST_INVESTMENT));

        if (investment.getInvestorAccount().getInvestors().getId() != investorId ||
                investment.getInvestorAccount().getId() != accountId) {
            throw new RuntimeException(ErrorMessages.NOT_EXIST_INVESTOR_OR_ACCOUNT_FOR_THIS_INVESTMENT);
        }

        investment.setStrategyPercentage(dto.getStrategy());
        return ResponseDTO.builder().build();
    }

    @Override
    @Transactional
    public ResponseDTO deleteInvestment(int investorId, int accountId, int investmentId) {
        InvestorAccountInvestment investment = investorAccountInvestmentRepository
                .findById(investmentId)
                .orElseThrow(()->  new RuntimeException(ErrorMessages.NOT_EXIST_INVESTMENT));

        if (investment.getInvestorAccount().getInvestors().getId() != investorId ||
                investment.getInvestorAccount().getId() != accountId) {
            throw new RuntimeException(ErrorMessages.NOT_EXIST_INVESTOR_OR_ACCOUNT_FOR_THIS_INVESTMENT);
        }

        investorAccountInvestmentRepository.deleteById(investmentId);
        return ResponseDTO.builder().build();
    }
}
