package com.example.xtream.service.impl;

import com.example.xtream.constant.ErrorMessages;
import com.example.xtream.dto.request.CreateInvestorDTO;
import com.example.xtream.dto.request.InvestorInvestmentDTO;
import com.example.xtream.dto.request.UpdateInvestorDetailsDTO;
import com.example.xtream.dto.response.InvestorAccountsDTO;
import com.example.xtream.dto.response.InvestorSearchDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.*;
import com.example.xtream.model.common.Status;
import com.example.xtream.repository.*;
import com.example.xtream.service.InvestorService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
        investorId.ifPresent(integer -> investorRepository
                .findById(integer.longValue())
                .orElseThrow(() -> new RuntimeException(ErrorMessages.INVESTOR_ID_NOT_FOUND)));

        // Check exist investorAccountId
        investorAccountId.ifPresent(integer -> investorAccountRepository
                .findById(integer.longValue())
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
    public ResponseDTO createInvestor(CreateInvestorDTO dto)
    {
        validateUniqueness(dto);
        Products product = resolveProduct(dto.getProductId());
        List<Investments> investments = resolveAndValidateInvestments(dto.getInvestments());

        Investor investor = buildInvestor(dto);
        investorRepository.save(investor);

        InvestorAccount account = buildDefaultAccount(investor, product);
        investorAccountRepository.save(account);

        createInvestmentAllocations(account, dto.getInvestments(), investments);

        return ResponseDTO.builder().response(investor.getId()).build();
    }

    @Override
    @Transactional
    public ResponseDTO getInvestorDetails(Long investorId)
    {
        Investor investor = investorRepository
                .findById((investorId))
                .orElseThrow(()-> new RuntimeException("investor not exist"));

        return ResponseDTO.builder().response(investor).build();
    }

    @Override
    @Transactional
    public ResponseDTO updateInvestorDetails(UpdateInvestorDetailsDTO updateInvestorDetailsDTO)
    {
        Investor investor = investorRepository
                .findById(updateInvestorDetailsDTO.getInvestorId())
                .orElseThrow(()-> new RuntimeException(ErrorMessages.INVESTOR_ID_NOT_FOUND));

        investor.setEmail(updateInvestorDetailsDTO.getEmail());
        investor.getInvestorAddress().setCity(updateInvestorDetailsDTO.getCity());
        investor.getInvestorAddress().setDistrict(updateInvestorDetailsDTO.getDistrict());
        investor.setDateOfBirth(updateInvestorDetailsDTO.getDob());
        investor.setEmail(updateInvestorDetailsDTO.getEmail());
        investor.setGender(updateInvestorDetailsDTO.getGender());
        investor.setGivenNames(updateInvestorDetailsDTO.getGivenName());
        investor.setSecondaryPhone(updateInvestorDetailsDTO.getHomePhone());
        investor.setMobile(updateInvestorDetailsDTO.getMobile());
        investor.setNextContactMethod(updateInvestorDetailsDTO.getNextBestContactMethod());
        investor.getInvestorAddress().setPostCode(updateInvestorDetailsDTO.getPostCode());
        investor.setBestContactMethod(updateInvestorDetailsDTO.getPreferredContactMethod());
        investor.setRetirementAge(updateInvestorDetailsDTO.getRetirementAge());
        investor.getInvestorAddress().setPropertyName(updateInvestorDetailsDTO.getPropertyName());
        investor.getInvestorAddress().setStreetName1(updateInvestorDetailsDTO.getStreetName1());
        investor.getInvestorAddress().setStreetName2(updateInvestorDetailsDTO.getStreetName2());
        investor.getInvestorAddress().setStreetNumber(updateInvestorDetailsDTO.getStreetNumber());
        investor.setSurname(updateInvestorDetailsDTO.getSurname());
        // todo: required hash this field in db and decrypt when load up
        investor.setTaxFileNumber(updateInvestorDetailsDTO.getTaxFileNumber());
        investor.setTitle(updateInvestorDetailsDTO.getTitle());

        return ResponseDTO.builder().build();
    }

    @Override
    @Transactional
    public ResponseDTO getInvestorAccountsByInvestorId(Long investorId,Pageable pageable) {
        Investor investor = investorRepository
                .findById(investorId)
                .orElseThrow(()-> new RuntimeException("Investor Not Found"));

        ResponseDTO accounts = searchInvestorAccounts(investorId,pageable);

        return ResponseDTO.builder().response(accounts).build();
    }

    public ResponseDTO searchInvestorAccounts(Long investorId ,Pageable pageable)
    {
        Optional<Long> investorIdOp = Optional.of(investorId);
        // Check exist investorId
        investorIdOp.ifPresent(integer -> investorRepository
                .findById(integer)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.INVESTOR_ID_NOT_FOUND)));
        // Pagination
        Page<InvestorAccountsDTO> pageOfInvestorAccounts = investorAccountRepository.findAccountsByInvestorId(investorId,pageable);

        return ResponseDTO
                .builder()
                .response(pageOfInvestorAccounts.getContent())
                .totalElements(pageOfInvestorAccounts.getTotalElements())
                .totalPages(pageOfInvestorAccounts.getTotalPages())
                .currentPage(pageOfInvestorAccounts.getNumber())
                .build();
    }


    private void validateUniqueness(CreateInvestorDTO dto) {
        investorRepository.findByEmail(dto.getEmail()).ifPresent(existing -> {
            throw new RuntimeException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        });
        if (dto.getTaxFileNumber() != null && !dto.getTaxFileNumber().isBlank()) {
            investorRepository.findByTaxFileNumber(dto.getTaxFileNumber()).ifPresent(existing -> {
                throw new RuntimeException(ErrorMessages.TFN_ALREADY_EXISTS);
            });
        }
    }

    private Products resolveProduct(String productId) {
        if (productId == null || productId.isBlank()) {
            throw new RuntimeException(ErrorMessages.PRODUCT_NOT_FOUND);
        }
        return productRepository.findById(Long.valueOf(productId))
                .orElseThrow(() -> new RuntimeException(ErrorMessages.PRODUCT_NOT_FOUND));
    }

    private List<Investments> resolveAndValidateInvestments(List<InvestorInvestmentDTO> investmentDTOs) {
        if (investmentDTOs == null || investmentDTOs.isEmpty()) {
            throw new RuntimeException(ErrorMessages.INVESTMENTS_REQUIRED);
        }

        int totalStrategy = investmentDTOs.stream()
                .mapToInt(InvestorInvestmentDTO::getInvestmentStrategy)
                .sum();
        if (totalStrategy != 100) {
            throw new RuntimeException(ErrorMessages.INVESTMENT_STRATEGY_MUST_TOTAL_100);
        }

        return investmentDTOs.stream()
                .map(dto -> investmentRepository.findById((long) dto.getInvestmentNameOrId())
                        .orElseThrow(() -> new RuntimeException(ErrorMessages.INVESTMENT_NOT_FOUND)))
                .toList();
    }

    private Investor buildInvestor(CreateInvestorDTO dto) {
        Investor investor = new Investor();
        investor.setGivenNames(dto.getGivenName());
        investor.setSurname(dto.getSurname());
        investor.setDateOfBirth(dto.getDob());
        investor.setEmail(dto.getEmail());
        investor.setGender(dto.getGender());
        investor.setTitle(dto.getTitle());
        investor.setRetirementAge(dto.getRetirementAge());
        investor.setTaxFileNumber(dto.getTaxFileNumber());
        investor.setPrimaryPhone(dto.getHomePhone());
        investor.setMobile(dto.getMobile());
        investor.setSecondaryPhone(dto.getHomePhone());
        investor.setBestContactMethod(dto.getPreferredContactMethod());
        investor.setNextContactMethod(dto.getNextBestContactMethod());
        investor.setStatus(Status.A);

        InvestorAddress address = new InvestorAddress();
        address.setStreetNumber(dto.getStreetNumber());
        address.setStreetName1(dto.getStreetName1());
        address.setStreetName2(dto.getStreetName2());
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setPostCode(dto.getPostCode());
        address.setPropertyName(dto.getPropertyName());
        investor.setInvestorAddress(address);

        return investor;
    }

    private InvestorAccount buildDefaultAccount(Investor investor, Products product) {
        InvestorAccount account = new InvestorAccount();
        account.setInvestor(investor);
        account.setProduct(product);
        account.setStatus(Status.A);
        return account;
    }

    private void createInvestmentAllocations(
            InvestorAccount account,
            List<InvestorInvestmentDTO> investmentDTOs,
            List<Investments> investments
    ) {
        for (int i = 0; i < investments.size(); i++) {
            Investments investment = investments.get(i);
            InvestorInvestmentDTO dto = investmentDTOs.get(i);

            InvestorAccountInvestment allocation = new InvestorAccountInvestment();
            allocation.setInvestorAccount(account);
            allocation.setInvestment(investment);
            allocation.setStrategyPercentage(BigDecimal.valueOf(dto.getInvestmentStrategy()));
            allocation.setBalance(BigDecimal.ZERO);
            allocation.setUnits(BigDecimal.ZERO);

            investorAccountInvestmentRepository.save(allocation);
        }
    }
}
