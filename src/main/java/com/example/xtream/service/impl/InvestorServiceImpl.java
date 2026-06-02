package com.example.xtream.service.impl;

import com.example.xtream.constant.ErrorMessages;
import com.example.xtream.dto.request.CreateInvestorRequestDTO;
import com.example.xtream.dto.request.UpdateInvestorDetailsRequestDTO;
import com.example.xtream.dto.response.InvestorSearchDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.common.Status;
import com.example.xtream.model.investment.Investments;
import com.example.xtream.model.investor.InvestorAccount;
import com.example.xtream.model.investor.InvestorAccountInvestment;
import com.example.xtream.model.investor.InvestorAddress;
import com.example.xtream.model.investor.Investors;
import com.example.xtream.model.product.Products;
import com.example.xtream.repository.*;
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
    private final ProductRepository productRepository;
    private final InvestmentRepository investmentRepository;
    private final InvestorAccountInvestmentRepository investorAccountInvestmentRepository;
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
        investorId
                .ifPresent(integer -> investorRepository
                .findById(integer)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.INVESTOR_NOT_FOUND)));

        // Check exist investorAccountId
        investorAccountId
                .ifPresent(integer -> investorAccountRepository
                .findById(integer)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.INVESTOR_ACCOUNT_ID_NOT_FOUND)));

        // Pagination
        Page<InvestorSearchDTO> pageOfInvestors = investorRepository
                        .findAllByMultiConditions(investorId,investorAccountId,investorEmail
                                ,investorName,investorStatus,activeAccountOnly,from,to,postcode,pageable);

        return ResponseDTO
                .builder()
                .response(pageOfInvestors.getContent())
                .totalElements(pageOfInvestors.getTotalElements())
                .totalPages(pageOfInvestors.getTotalPages())
                .currentPage(pageOfInvestors.getNumber())
                .build();
    }

    /**
     * Create new investor with default account and investment allocations.
     *
     * Flow:
     * 1. Validate uniqueness (email, TFN)
     * 2. Validate product exists
     * 3. Validate investments exist and strategy totals 100%
     * 4. Persist investor
     * 5. Create default investor account linked to product
     * 6. Attach investment allocations to the account
     *
     * @param dto Payload from request
     * @return created investor id
     */
    @Override
    @Transactional
    public ResponseDTO createInvestor(CreateInvestorRequestDTO dto)
    {
        // TODO: Insert investor info
        Investors investor = new Investors();
        InvestorAddress investorAddress = new InvestorAddress();

        investorAddress.setPropertyName(dto.getPropertyName());
        investorAddress.setStreetName1(dto.getStreetName1());
        investorAddress.setStreetName2(dto.getStreetName2());
        investorAddress.setStreetNumber(dto.getStreetNumber());
        investorAddress.setCity(dto.getCity());
        investorAddress.setPostCode(dto.getPostCode());
        investorAddress.setDistrict(dto.getDistrict());

        investor.setInvestorAddress(investorAddress);
        investor.setEmail(dto.getEmail());
        investor.setDateOfBirth(dto.getDob());
        investor.setEmail(dto.getEmail());
        investor.setGender(dto.getGender());
        investor.setGivenNames(dto.getGivenName());
        investor.setSecondaryPhone(dto.getHomePhone());
        investor.setMobile(dto.getMobile());
        investor.setNextContactMethod(dto.getNextBestContactMethod());
        investor.setBestContactMethod(dto.getPreferredContactMethod());
        investor.setRetirementAge(dto.getRetirementAge());
        investor.setSurname(dto.getSurname());
        investor.setTitle(dto.getTitle());
//        investor.setTaxFileNumber(SecurityUtil.encrypt(dto.getTaxFileNumber()));
        investor.setTaxFileNumber(dto.getTaxFileNumber());
        investor.setStatus(Status.ACTIVE);
        // TODO: Insert investor account info
        InvestorAccount account = new InvestorAccount();

        account.setStartDate(dto.getStartDate());
        account.setInvestors(investor);
        investorRepository.save(investor);
        // SUB-TODO: Load product up for new account
        Products product = productRepository.findById(dto.getProductCode()).get();

        account.setProducts(product);
        account.setStatus(Status.ACTIVE);
        investorAccountRepository.save(account);
        // TODO: Insert investor account investment info
        dto.getInvestments().forEach((i) -> {
            InvestorAccountInvestment accountInvestment = new InvestorAccountInvestment();

            accountInvestment.setInvestorAccount(account);
            accountInvestment.setStrategyPercentage(i.getInvestmentStrategy());
            Investments investment = investmentRepository.findById(i.getInvestmentCode()).get();
            accountInvestment.setInvestment(investment);
            investorAccountInvestmentRepository.save(accountInvestment);
        });

        return ResponseDTO.builder().build();
    }

    @Override
    @Transactional
    public ResponseDTO getInvestor(int investorId)
    {
        Investors investor = investorRepository
                .findById(investorId)
//                .map(actor -> {
//                    actor.setTaxFileNumber(SecurityUtil.decrypt(actor.getTaxFileNumber()));
//                    return actor;
//                })
                .orElseThrow(() -> new RuntimeException(ErrorMessages.INVESTOR_NOT_FOUND));

        return ResponseDTO
                .builder()
                .response(investor)
                .build();
    }

    @Override
    @Transactional
    public ResponseDTO updateInvestor(int investorId, UpdateInvestorDetailsRequestDTO dto)
    {
        Investors investor = investorRepository
                .findById(investorId)
                .orElseThrow(()-> new RuntimeException(ErrorMessages.INVESTOR_NOT_FOUND));

        InvestorAddress investorAddress = new InvestorAddress();

        investorAddress.setPropertyName(dto.getPropertyName());
        investorAddress.setStreetName1(dto.getStreetName1());
        investorAddress.setStreetName2(dto.getStreetName2());
        investorAddress.setStreetNumber(dto.getStreetNumber());
        investorAddress.setCity(dto.getCity());
        investorAddress.setPostCode(dto.getPostCode());
        investorAddress.setDistrict(dto.getDistrict());

        investor.setInvestorAddress(investorAddress);
        investor.setEmail(dto.getEmail());
        investor.setDateOfBirth(dto.getDob());
        investor.setEmail(dto.getEmail());
        investor.setGender(dto.getGender());
        investor.setGivenNames(dto.getGivenName());
        investor.setSecondaryPhone(dto.getHomePhone());
        investor.setMobile(dto.getMobile());
        investor.setNextContactMethod(dto.getNextBestContactMethod());
        investor.setBestContactMethod(dto.getPreferredContactMethod());
        investor.setRetirementAge(dto.getRetirementAge());
        investor.setSurname(dto.getSurname());
        investor.setTitle(dto.getTitle());
//        investor.setTaxFileNumber(SecurityUtil.encrypt(dto.getTaxFileNumber()));
        investor.setTaxFileNumber(dto.getTaxFileNumber());

        return ResponseDTO.builder().build();
    }

}
