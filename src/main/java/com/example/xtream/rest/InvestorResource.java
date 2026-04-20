package com.example.xtream.rest;

import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.service.InvestorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Investors resource
 */
@RestController
@RequestMapping("api/investors")
@AllArgsConstructor
public class InvestorResource {

    private InvestorService investorService;

    /**
     * get list investors
     *
     * @param investorId    params
     * @param investorAccountId params
     * @param investorEmail params
     * @param investorName  params
     * @param investorStatus    params
     * @param from  params local date time
     * @param to    params local date time
     * @param activeAccountOnly params
     * @param postcode params
     * @param pageable  params
     * @return  page of investors
     */
    @GetMapping("/search")
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
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
    {

        return ResponseEntity.ok(investorService.searchInvestors(investorId,investorAccountId,investorEmail,
                investorName,investorStatus,from,to,activeAccountOnly,postcode,pageable));
    }

}
