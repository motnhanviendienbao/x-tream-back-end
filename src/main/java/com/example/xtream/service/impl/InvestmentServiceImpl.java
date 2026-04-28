package com.example.xtream.service.impl;

import com.example.xtream.dto.response.InvestmentsTreeDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.repository.InvestmentRepository;
import com.example.xtream.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
}
