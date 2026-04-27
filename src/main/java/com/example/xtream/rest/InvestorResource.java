package com.example.xtream.rest;

import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.enums.Status;
import com.example.xtream.service.InvestorService;
import com.example.xtream.service.impl.AuthServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        logger.info
                (investorId + "\n"
                        + investorAccountId + "\n"
                        + investorEmail + "\n"
                        + investorName + "\n"
                        + investorStatus + "\n"
                        + from + "\n"
                        + to + "\n"
                        + activeAccountOnly + "\n"
                        + postcode + "\n"
                );

        return ResponseEntity.ok(investorService.searchInvestors(investorId,investorAccountId,investorEmail,
                investorName,investorStatus,from,to,activeAccountOnly,postcode,pageable));
    }

}
