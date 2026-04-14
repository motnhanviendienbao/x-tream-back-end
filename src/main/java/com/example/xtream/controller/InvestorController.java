package com.example.xtream.controller;

import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.service.InvestorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/investors")
@AllArgsConstructor
public class InvestorController {

    private InvestorService investorService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getListInvestors(
            @RequestParam("investorId") Optional<Integer> investorId,
            @RequestParam("investorAccountId") Optional<Integer> investorAccountId,
            @RequestParam("investorEmail") Optional<String> investorEmail,
            @RequestParam("investorName") Optional<String> investorName,
            @RequestParam("investorStatus") Optional<Integer> investorStatus,
            @RequestParam("from") Optional<LocalDate> from,
            @RequestParam("to") Optional<LocalDate> to,
            @RequestParam("activeAccountOnly") Optional<Boolean> activeAccountOnly,
            @RequestParam("postcode") Optional<String> postcode,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable)
    {

        return ResponseEntity.ok(investorService.searchInvestors(investorId,investorAccountId,investorEmail,
                investorName,investorStatus,from,to,activeAccountOnly,postcode,pageable));
    }

}
