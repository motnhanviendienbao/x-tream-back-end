package com.example.xtream.rest;

import com.example.xtream.dto.request.UpdateAccountInvestmentRequestDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Investment resource
 *
 */
@RestController
@RequestMapping("/api/investment")
@RequiredArgsConstructor
public class InvestmentResource {

    private final InvestmentService investmentService;

    /**
     * Get all type of investments
     *
     * @return response contains array of investment
     */
    @GetMapping("/tree")
    public ResponseEntity<ResponseDTO> allInvestments() {
        ResponseDTO response = investmentService.getAllInvestments();
        return ResponseEntity.ok(response);
    }

    /**
     * Get list investment of account
     */
    @GetMapping("/investor/{investorId}/account/{accountId}")
    public ResponseEntity<ResponseDTO> getInvestments(
            @PathVariable int investorId,
            @PathVariable int accountId,
            @RequestParam(required = false) String search,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    )
    {
        ResponseDTO response = investmentService.getInvestments(investorId,accountId,search,pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * Update strategy investment of account
     */
    @PatchMapping("/{investmentId}/investor/{investorId}/account/{accountId}")
    public ResponseEntity<ResponseDTO> updateInvestment(
            @PathVariable int investmentId,
            @PathVariable int investorId,
            @PathVariable int accountId,
            @RequestBody @Valid UpdateAccountInvestmentRequestDTO dto
            )
    {
        ResponseDTO response = investmentService.updateInvestment(investorId,accountId,investmentId,dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete investment of account
     */
    @DeleteMapping("/{investmentId}/investor/{investorId}/account/{accountId}")
    public ResponseEntity<ResponseDTO> deleteInvestment(
            @PathVariable int investmentId,
            @PathVariable int investorId,
            @PathVariable int accountId
    ) {
        ResponseDTO response = investmentService.deleteInvestment(investorId,accountId,investmentId);
        return ResponseEntity.ok(response);
    }
}
