package com.example.xtream.repository;

import com.example.xtream.dto.response.InvestorAccountsDTO;
import com.example.xtream.model.InvestorAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorAccountRepository extends JpaRepository<InvestorAccount,Long> {
    @Query("""
            SELECT DISTINCT
                investorAccount.id as investorAccountId,
                CONCAT(funds.fundName,' / ',productGroup.productGroupShortCode,' / ',products.productName) as product,
                products.productType as productType,
                investorAccount .status as accountStatus,
                "100" as balance,
                "90" as uncollectedChagres,
                "adviser" as adviser
            FROM InvestorAccount investorAccount
            JOIN Products products ON investorAccount.product.id = products.id
            JOIN ProductGroup productGroup ON products.productGroup.id = productGroup.id
            JOIN Funds funds ON productGroup.fund.id = funds.id
            WHERE (investorAccount.investor.id = :id)
    """)
    Page<InvestorAccountsDTO> findAccountsByInvestorId(Long id, Pageable pageable);
}
