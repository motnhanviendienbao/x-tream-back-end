package com.example.xtream.repository;

import com.example.xtream.dto.response.InvestorSearchDTO;
import com.example.xtream.model.Investor;
import com.example.xtream.model.InvestorAccount;
import com.example.xtream.model.User;
import com.example.xtream.model.enums.Status;
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
    Page<InvestorAccount> findByInvestor_Id(Long id,Pageable pageable);
}
