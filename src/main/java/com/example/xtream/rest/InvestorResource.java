package com.example.xtream.rest;

import com.example.xtream.dto.request.CreateInvestorDTO;
import com.example.xtream.dto.request.UpdateInvestorDetailsDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.common.Status;
import com.example.xtream.service.InvestorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Investors resource
 */
@RestController
@RequestMapping("api/investors")
@RequiredArgsConstructor
public class InvestorResource {

    private final InvestorService investorService;
    private static final Logger logger = LogManager.getLogger(InvestorResource.class);

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
            @RequestParam("investorStatus") Optional<Status> investorStatus,
            @RequestParam("from") Optional<LocalDate> from,
            @RequestParam("to") Optional<LocalDate> to,
            @RequestParam("activeAccountOnly") Optional<Status> activeAccountOnly,
            @RequestParam("postcode") Optional<String> postcode,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
    {
        ResponseDTO response = investorService.searchInvestors(investorId,investorAccountId,investorEmail,
                investorName,investorStatus,from,to,activeAccountOnly,postcode,pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * Get details of specific investor without investment info
     *
     * @param investorId param
     * @return investor details
     */
    @GetMapping("/detail/{investorId}")
    public ResponseEntity<ResponseDTO> getInvestorDetails(@PathVariable Long investorId)
    {
        ResponseDTO response = investorService.getInvestorDetails(investorId);
        return ResponseEntity.ok(response);
    }

    /**
     * Add Investor with investment allocation
     */
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> createInvestor(
            @RequestBody @Valid CreateInvestorDTO createInvestorRequestDTO)
    {
        ResponseDTO response = investorService.createInvestor(createInvestorRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Update investor details info without investment info
     * @param updateInvestorDetailsDTO request
     * @return status
     */
    @PostMapping("/update")
    public ResponseEntity<ResponseDTO> updateInvestorDetails(
            @RequestBody @Valid UpdateInvestorDetailsDTO updateInvestorDetailsDTO)
    {
        ResponseDTO response = investorService.updateInvestorDetails(updateInvestorDetailsDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/accounts/{investorId}")
    public ResponseEntity<ResponseDTO> updateInvestorDetails(
            @PathVariable Long investorId,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
    {
        ResponseDTO response = investorService.getInvestorAccountsByInvestorId(investorId,pageable);
        return ResponseEntity.ok(response);
    }

}
