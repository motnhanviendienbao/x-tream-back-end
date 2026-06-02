package com.example.xtream.repository;

import com.example.xtream.dto.response.AccountInvestmentDTO;
import com.example.xtream.dto.response.InvestorAccountsDTO;
import com.example.xtream.model.common.Status;
import com.example.xtream.model.investor.InvestorAccountInvestment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorAccountInvestmentRepository extends JpaRepository<InvestorAccountInvestment, Integer> {
    @Query("""
            SELECT DISTINCT
                investorAccountInvestment.id as accountInvestmentId,
                investments.name as accountInvestmentName,
                investorAccountInvestment.strategyPercentage as strategy,
                "0.00 AUD" as balance,
                "0.00%" as percentage,
                "0.00000" as unit,
                "0.00000" as price,
                "2026-08-09" as priceDate
            FROM InvestorAccountInvestment investorAccountInvestment
            JOIN InvestorAccount investorAccount ON investorAccount.id = investorAccountInvestment.investorAccount.id
            JOIN Investors investors ON investors.id = investorAccountInvestment.investorAccount.investors.id
            JOIN Investments investments ON investments.code = investorAccountInvestment.investment.code
            WHERE (:investorId = 0 OR investors.id = :investorId)
            AND (:accountId = 0 OR investorAccount.id = :accountId)
            AND (:name IS NULL OR investments.name LIKE %:name%)
            AND (:investmentId = 0 OR investorAccountInvestment.id = :investmentId)
    """)
    Page<AccountInvestmentDTO> findInvestments(@Param("investorId") int investorId, @Param("accountId") int accountId, @Param("name") String name, @Param("investmentId") int investmentId , Pageable pageable);


}
