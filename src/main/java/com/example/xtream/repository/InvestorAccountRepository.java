package com.example.xtream.repository;

import com.example.xtream.model.Investor;
import com.example.xtream.model.InvestorAccount;
import com.example.xtream.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface InvestorAccountRepository extends JpaRepository<InvestorAccount,Long> {
    @Query("select ia.investor.id from InvestorAccount ia where ia.id = :investorAccountId")
    Optional<Integer> findInvestorIdByAccountId(@Param("investorAccountId") Long investorAccountId);

}
