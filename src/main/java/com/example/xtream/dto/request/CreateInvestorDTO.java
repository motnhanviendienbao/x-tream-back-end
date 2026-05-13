package com.example.xtream.dto.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CreateInvestorDTO {
    @NotBlank
    private String city;
    private String country;
    @NotBlank
    private String district;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate dob;
    @Email
    private String email;
    @NotBlank
    private String gender;
    @NotBlank
    private String givenName;
    private String homePhone;
    private List<InvestorInvestmentDTO> investments;
    private String mobile;
    private String nextBestContactMethod;
    @NotBlank
    private String postCode;
    private String preferredContactMethod;
    private String productId;
    private String productName;
    private String propertyName;
    private String retirementAge;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotBlank
    private String streetName1;
    private String streetName2;
    @NotBlank
    private String streetNumber;
    @NotBlank
    private String surname;
    @NotBlank
    private String taxFileNumber;
    private String title;
}
