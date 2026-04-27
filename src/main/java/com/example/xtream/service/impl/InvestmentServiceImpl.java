package com.example.xtream.service.impl;

import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.repository.InvestmentRepository;
import com.example.xtream.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;

    @Override
    @Transactional
    public ResponseDTO getAllInvestments() {
        return null;
    }
}
