package com.example.xtream.model;
import com.example.xtream.config.audit.Auditable;
import com.example.xtream.model.common.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "investors")
public class Investor extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "given_names", length = 100, unique = false, nullable = true )
    private String givenNames;

    @Column(name = "surname", length = 100, unique = false, nullable = true)
    private String surname;

    @Column(name = "date_of_birth", unique = false, nullable = true )
    private LocalDate dateOfBirth;

    @Column(name = "status", unique = false, nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private Status status = Status.A;

    @Column(name = "title", unique = false, nullable = true)
    private String title;

    @Column(name = "retirement_age", unique = false, nullable = false, length = 3)
    private String retirementAge;

    @Column(name = "tfn", length = 9, unique = true, nullable = true)
    private  String taxFileNumber;

    @Column(name = "email", length = 255, unique = true,nullable = false)
    private String email;

    @Column(name = "primary_phone", length = 25, nullable = false, unique = true)
    private String primaryPhone;

    @Column(name = "best_contact_method", nullable = false)
    private String bestContactMethod;

    @Column(name = "next_contact_method", nullable = false)
    private String nextContactMethod;

    @Column(name = "mobile", unique = true, nullable = false, length = 25)
    private String mobile;

    @Column(name = "secondary_phone", unique = true, nullable = false, length = 25)
    private String secondaryPhone;


    @Column(name = "gender", unique = false, nullable = false, length = 25)
    private String gender;

    @Embedded
    @JsonUnwrapped
    private InvestorAddress investorAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "investor", fetch = FetchType.LAZY)
    private List<InvestorAccount> investorAccounts = new ArrayList<>();
}
