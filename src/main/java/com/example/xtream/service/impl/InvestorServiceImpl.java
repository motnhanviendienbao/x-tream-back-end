package com.example.xtream.service.impl;

import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.Investor;
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

    private InvestorAccountRepository investorAccountRepository;
    private InvestorRepository investorRepository;

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
                                       Optional<Integer> investorStatus, Optional<LocalDate> from,
                                       Optional<LocalDate> to, Optional<Boolean> activeAccountOnly,
                                       Optional<String> postcode, Pageable pageable)
    {
        // Get investorId corresponding to investor account id if exist
        if(investorAccountId.isPresent()) {
            investorId = investorAccountRepository.findInvestorIdByAccountId(investorAccountId.get().longValue());
        }
        if(investorStatus.isPresent() && investorStatus.get().equals(-1)) {
            investorStatus = Optional.empty();
        }
        if (activeAccountOnly.isPresent() && activeAccountOnly.get().equals(Boolean.TRUE)) {
            investorStatus = Optional.of(1);
        }

        Optional<Boolean> investorStatusConverted = Optional.empty();
        if(investorStatus.isPresent()) {
            if(investorStatus.get().equals(1)) {
                investorStatusConverted = Optional.of(true);
            } else if (investorStatus.get().equals(0)) {
                investorStatusConverted = Optional.of(false);
            }
        }

        // Pagination result
        Page<Investor> pageOfInvestors =
                investorRepository
                        .findAllByMultiConditions(investorId,investorEmail
                                ,investorName,investorStatusConverted,from,to,postcode,pageable);


        return ResponseDTO
                .builder()
                .items(pageOfInvestors.getContent())
                .totalElements(pageOfInvestors.getTotalElements())
                .totalPages(pageOfInvestors.getTotalPages())
                .currentPage(pageOfInvestors.getNumber())
                .build();
    }
}
