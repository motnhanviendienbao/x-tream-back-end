package com.example.xtream.repository;

import com.example.xtream.dto.response.InvestmentsTreeDTO;
import com.example.xtream.model.Investments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investments,Long> {

    @Query("""
        SELECT DISTINCT
            invesments.id as investmentId,
            invesments.investmentShortCode as investmentShortCodeName
        FROM Investments invesments
        WHERE invesments.status = 'A'
    """)
    List<InvestmentsTreeDTO> findAllInvestmentsActive();
}
