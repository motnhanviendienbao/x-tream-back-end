package com.example.xtream.repository;

import com.example.xtream.dto.response.InvestorSearchDTO;
import com.example.xtream.model.Investor;
import com.example.xtream.model.common.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<Investor,Long>
{
        Optional<Investor> findByEmail(String email);
        Optional<Investor> findByTaxFileNumber(String taxFileNumber);
        @Query("""
            SELECT DISTINCT
                investor.id as investorId,
                CONCAT(investor.givenNames,' ', investor.surname) as investorName,
                investor.status as investorStatus,
                investor.dateOfBirth as investorDob,
                investor.investorAddress.postCode as investorPostCode,
                investorAccount.status as investorAccountStatus,
                investor.email as investorEmail
            FROM Investor investor
            JOIN InvestorAccount investorAccount ON investor.id = investorAccount.investor.id
            WHERE
            (:investorId is null or investor.id = :investorId)
            AND (:investorAccountId is null or investorAccount.id = :investorAccountId)
            AND (:investorName is null or investor.givenNames LIKE %:investorName% or investor.surname LIKE %:investorName%)
            AND (:investorStatus is null or investor.status = :investorStatus)
            AND (:from is null or :to is null or investor.dateOfBirth between :from and :to)
            AND (:investorEmail is null or investor.email = :investorEmail)
            AND (:postcode is null or investor.investorAddress.postCode like %:postcode%)
            AND (:activeAccountOnly is null or investorAccount.status = :activeAccountOnly)
    """)
    Page<InvestorSearchDTO> findAllByMultiConditions(
            @Param("investorId") Optional<Integer> investorId,
            @Param("investorAccountId") Optional<Integer> investorAccountId,
            @Param("investorEmail") Optional<String> investorEmail,
            @Param("investorName") Optional<String> investorName,
            @Param("investorStatus") Optional<Status> investorStatus,
            @Param("activeAccountOnly") Optional<Status> activeAccountOnly,
            @Param("from") Optional<LocalDate> from,
            @Param("to") Optional<LocalDate> to,
            @Param("postcode") Optional<String> postcode,
            Pageable pageable);
}
