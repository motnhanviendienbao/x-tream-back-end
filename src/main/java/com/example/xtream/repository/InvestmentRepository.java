package com.example.xtream.repository;

import com.example.xtream.model.Investments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentRepository extends JpaRepository<Investments,Long> {
}
