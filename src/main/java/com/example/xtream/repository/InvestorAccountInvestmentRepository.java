package com.example.xtream.repository;

import com.example.xtream.model.InvestorAccountInvestment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorAccountInvestmentRepository extends JpaRepository<InvestorAccountInvestment, Long> {
}
