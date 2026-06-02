package com.example.xtream.repository;

import com.example.xtream.dto.response.AccountDTO;
import com.example.xtream.dto.response.InvestorAccountsDTO;
import com.example.xtream.model.common.Status;
import com.example.xtream.model.investor.InvestorAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestorAccountRepository extends JpaRepository<InvestorAccount,Integer> {
    @Query("""
            SELECT DISTINCT
                investorAccount.id as investorAccountId,
                CONCAT(funds.name,' / ',productGroup.name,' / ',products.name) as product,
                products.type as productType,
                investorAccount .status as accountStatus,
                "100" as balance,
                "90" as uncollectedChagres,
                "adviser" as adviser
            FROM InvestorAccount investorAccount
            JOIN Products products ON investorAccount.products.code = products.code
            JOIN ProductGroup productGroup ON products.productGroup.code = productGroup.code
            JOIN Funds funds ON productGroup.fund.code= funds.code
            WHERE (investorAccount.investors.id = :id)
    """)
    Page<InvestorAccountsDTO> findAccountsByInvestorId(@Param("id") int id, Pageable pageable);

    @Query("""
            SELECT DISTINCT
                CONCAT(funds.name,' / ',productGroup.name,' / ', products.name) as productTree,
                investorAccount.createdAt as accountCreationDate,
                investorAccount.startDate as startDate,
                investorAccount.id as investorAccountId,
                "SIPP" as accountName,
                CONCAT(investorAccount.investors.givenNames,' ',investorAccount.investors.surname) as investorName,
                investorAccount.investors.id as investorId
            FROM InvestorAccount investorAccount
            JOIN Products products ON investorAccount.products.code = products.code
            JOIN ProductGroup productGroup ON products.productGroup.code = productGroup.code
            JOIN Funds funds ON productGroup.fund.code= funds.code
            WHERE (investorAccount.investors.id = :investorId) AND (investorAccount.id = :accountId)
    """)
    AccountDTO findAccount(@Param("accountId") int accountId,@Param("investorId") int investorId);

    @Query("""
            SELECT DISTINCT
                investorAccount.id as investorAccountId,
                CONCAT(funds.name,' / ',productGroup.name,' / ',investorAccount.products.name) as product,
                products.type as productType,
                investorAccount .status as accountStatus,
                "100" as balance,
                "90" as uncollectedChagres,
                "adviser" as adviser
            FROM InvestorAccount investorAccount
            JOIN Products products ON investorAccount.products.code = products.code
            JOIN ProductGroup productGroup ON products.productGroup.code = productGroup.code
            JOIN Funds funds ON productGroup.fund.code= funds.code
            WHERE (investorAccount.investors.id = :investorId)
            AND ( :accountId = 0 OR investorAccount.id = :accountId)
            AND (:name IS NULL OR investorAccount.products.name LIKE %:name%)
            AND (:status IS NULL OR investorAccount.status = :status)
    """)
    Page<InvestorAccountsDTO> findAccounts(@Param("investorId") int investorId, @Param("name") String name, @Param("accountId") int accountId, @Param("status") Status status, Pageable pageable);
}
