package com.example.xtream.service.impl;

import com.example.xtream.constant.ErrorMessages;
import com.example.xtream.dto.request.UpdateAccountRequestDTO;
import com.example.xtream.dto.response.AccountDTO;
import com.example.xtream.dto.response.InvestorAccountsDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.common.Status;
import com.example.xtream.model.investor.InvestorAccount;
import com.example.xtream.model.investor.Investors;
import com.example.xtream.repository.InvestorAccountRepository;
import com.example.xtream.repository.InvestorRepository;
import com.example.xtream.service.AccountService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final InvestorAccountRepository investorAccountRepository;
    @Override
    @Transactional
    public ResponseDTO getAccount(int accountId, int investorId) {
        AccountDTO account = investorAccountRepository.findAccount(accountId,investorId);
        return ResponseDTO.builder().response(account).build();
    }

    @Override
    @Transactional
    public ResponseDTO updateAccount(int accountId, int investorId, UpdateAccountRequestDTO info) {
        InvestorAccount account = investorAccountRepository.findById(accountId).orElseThrow(()-> new RuntimeException("updateAccount"));
        if( account.getInvestors().getId() != investorId ) {
            throw new RuntimeException(ErrorMessages.NOT_EXIST_INVESTOR_FOR_THIS_ACCOUNT);
        }
        account.setStartDate(info.getStartDate());

        return ResponseDTO.builder().build();
    }

    @Override
    @Transactional
    public ResponseDTO getAccounts(int investorId, String search, Status status, Pageable pageable) {
        String name = null;
        int accountId = 0;

        try {
            accountId = Integer.parseInt(search);
        } catch (NumberFormatException ex) {
            name = search;
        }

        Page<InvestorAccountsDTO> accountFiltered = investorAccountRepository.findAccounts(investorId,name,accountId,status,pageable);

        return ResponseDTO.builder()
                .response(accountFiltered.getContent())
                .totalElements(accountFiltered.getTotalElements())
                .totalPages(accountFiltered.getTotalPages())
                .currentPage(accountFiltered.getNumber())
                .build();
    }

}
