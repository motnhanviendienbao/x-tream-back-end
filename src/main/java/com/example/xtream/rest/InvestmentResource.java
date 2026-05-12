package com.example.xtream.rest;

import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Investment resource
 *
 */
@RestController
@RequestMapping("/api/investments")
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
}
