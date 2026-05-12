package com.example.xtream.dto.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateInvestorDetailsDTO {
    @NotBlank
    private Long investorId;
    @NotBlank
    private String city;
    private String country;
    @NotBlank
    private String district;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotBlank
    private LocalDate dob;
    @Email
    private String email;
    @NotBlank
    private String gender;
    @NotBlank
    private String givenName;
    private String homePhone;
    private String mobile;
    private String nextBestContactMethod;
    @NotBlank
    private String postCode;
    private String preferredContactMethod;
    private String propertyName;
    private String retirementAge;
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
