package com.example.xtream.repository;

import com.example.xtream.model.Investor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface InvestorRepository extends JpaRepository<Investor,Long>
{
    @Query(
            "select investor from Investor investor where "
                    + "(:investorId is null  or investor.id = :investorId) and"
                    + "(:investorName is null  or investor.name like %:investorName% ) and"
                    + "(:investorStatus is null  or investor.status = :investorStatus) and"
                    + "(:from is null or :to is null or investor.dob between :from and :to) and"
                    + "(:investorEmail is null or investor.email = :investorEmail) and"
                    + "(:postcode is null or investor.postCode like %:postcode%)")
    Page<Investor> findAllByMultiConditions(
            @Param("investorId") Optional<Integer> investorId,
            @Param("investorEmail") Optional<String> investorEmail,
            @Param("investorName") Optional<String> investorName,
            @Param("investorStatus") Optional<Boolean> investorStatus,
            @Param("from") Optional<LocalDate> from,
            @Param("to") Optional<LocalDate> to,
            @Param("postcode") Optional<String> postcode,
            Pageable pageable);
}
