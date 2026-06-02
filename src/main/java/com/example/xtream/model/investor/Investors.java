package com.example.xtream.model.investor;
import com.example.xtream.config.audit.Auditable;
import com.example.xtream.model.common.Status;
import com.example.xtream.model.investor.InvestorAddress;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "investors")
public class Investors extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "given_names")
    private String givenNames;

    @Column(name = "surname")
    private String surname;

    @Column(name = "dbo")
    private LocalDate dateOfBirth;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "title")
    private String title;

    @Column(name = "retirement_age")
    private Integer retirementAge;

    @Column(name = "tfn")
    private  String taxFileNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "primary_phone")
    private String primaryPhone;

    @Column(name = "best_contact_method")
    private String bestContactMethod;

    @Column(name = "next_contact_method")
    private String nextContactMethod;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "secondary_phone")
    private String secondaryPhone;


    @Column(name = "gender", unique = false, nullable = false, length = 25)
    private String gender;

    @Embedded
    @JsonUnwrapped
    private InvestorAddress investorAddress;
}
