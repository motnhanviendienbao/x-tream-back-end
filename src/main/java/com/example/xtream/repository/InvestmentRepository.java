package com.example.xtream.repository;

import com.example.xtream.dto.response.InvestmentsTreeDTO;
import com.example.xtream.model.investment.Investments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investments,String> {

    @Query("""
        SELECT DISTINCT
            invesments.code as investmentCode,
            invesments.name as investmentName
        FROM Investments invesments
        WHERE invesments.status = 'ACTIVE'
    """)
    List<InvestmentsTreeDTO> findAllInvestmentsActive();

}
