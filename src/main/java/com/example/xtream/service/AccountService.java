package com.example.xtream.service;

import com.example.xtream.dto.request.UpdateAccountRequestDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.common.Status;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    ResponseDTO getAccount(int accountId,int investorId);
    ResponseDTO updateAccount(int accountId, int investorId, UpdateAccountRequestDTO info);
    ResponseDTO getAccounts(int investorId, String searchParam, Status status, Pageable pageable);
}
