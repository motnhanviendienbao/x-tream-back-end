package com.example.xtream.rest;

import com.example.xtream.dto.request.UpdateAccountRequestDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.common.Status;
import com.example.xtream.model.investor.InvestorAccount;
import com.example.xtream.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountResource {

    private final AccountService accountService;

    /**
     * Accounts of investors
     *
     * @param investorId identify investor owed accounts
     * @param search id or name of account
     * @param pageable paging
     * @return list accounts of investor
     */
    @GetMapping("/investor/{investorId}")
    public ResponseEntity<ResponseDTO> getAccounts(
            @PathVariable int investorId,
            @RequestParam(required = false) String search ,
            @RequestParam(required = false) Status status ,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)

    {
        ResponseDTO response = accountService.getAccounts(investorId,search,status,pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * Get account info of investor
     *
     * @param accountId identify account
     * @param investorId identify investor owed account
     * @return account info of investor
     */
    @GetMapping("/{accountId}/investor/{investorId}")
    public ResponseEntity<ResponseDTO> getAccount(@PathVariable int accountId, @PathVariable int investorId)
    {
        ResponseDTO response = accountService.getAccount(accountId,investorId);
        return ResponseEntity.ok(response);
    }

    /**
     * Update account of investor
     * @param accountId identify account
     * @param investorId identify investor owed account
     * @param info new info for updating
     * @return update api
     */
    @PatchMapping("/{accountId}/investor/{investorId}")
    public ResponseEntity<ResponseDTO> updateAccount(@PathVariable int accountId, @PathVariable int investorId, @RequestBody UpdateAccountRequestDTO info)
    {
        ResponseDTO response = accountService.updateAccount(accountId,investorId,info);
        return ResponseEntity.ok(response);
    }


}
