package com.example.xtream.rest;

import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investments")
@RequiredArgsConstructor
public class InvestmentResource {

    private final InvestmentService investmentService;
    @GetMapping("/tree")
    public ResponseEntity<ResponseDTO> allInvestment() {
        ResponseDTO response = investmentService.getAllInvestments();
        return ResponseEntity.ok(response);
    }
}
