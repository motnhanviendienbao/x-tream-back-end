package com.example.xtream.service.impl;

import com.example.xtream.dto.response.InvestorSearchDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.Investor;
import com.example.xtream.model.enums.Status;
import com.example.xtream.repository.InvestorAccountRepository;
import com.example.xtream.repository.InvestorRepository;
import com.example.xtream.service.InvestorService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvestorServiceImpl implements InvestorService {

    private final InvestorAccountRepository investorAccountRepository;
    private final InvestorRepository investorRepository;

    public static final Logger logger = LogManager.getLogger(InvestorServiceImpl.class);

    /**
     * Get list page of investors by multi conditions
     *
     * @param investorId    investorId
     * @param investorAccountId investorAccountId
     * @param investorEmail investorEmail
     * @param investorName  investorName
     * @param investorStatus    investorStatus
     * @param from  from
     * @param to    to
     * @param activeAccountOnly activeAccountOnly
     * @param postcode  postcode
     * @param pageable  pageable
     * @return List of items
     */

    @Override
    @Transactional
    public ResponseDTO searchInvestors(Optional<Integer> investorId, Optional<Integer> investorAccountId,
                                       Optional<String> investorEmail, Optional<String> investorName,
                                       Optional<Status> investorStatus, Optional<LocalDate> from,
                                       Optional<LocalDate> to, Optional<Status> activeAccountOnly,
                                       Optional<String> postcode, Pageable pageable)
    {
        // Check exist investorId
        investorId.ifPresent(integer -> investorRepository
                .findById(integer.longValue())
                .orElseThrow(() -> new RuntimeException("Investor Id Not found")));

        // Check exist investorAccountId
        investorAccountId.ifPresent(integer -> investorAccountRepository
                .findById(integer.longValue())
                .orElseThrow(() -> new RuntimeException("Investor Account Id Not found")));

        // Pagination
        Page<InvestorSearchDTO> pageOfInvestors = investorRepository
                        .findAllByMultiConditions(investorId,investorAccountId,investorEmail
                                ,investorName,investorStatus,activeAccountOnly,from,to,postcode,pageable);

        logger.info(pageOfInvestors.toString());

        return ResponseDTO
                .builder()
                .response(pageOfInvestors.getContent())
                .totalElements(pageOfInvestors.getTotalElements())
                .totalPages(pageOfInvestors.getTotalPages())
                .currentPage(pageOfInvestors.getNumber())
                .build();
    }
}
