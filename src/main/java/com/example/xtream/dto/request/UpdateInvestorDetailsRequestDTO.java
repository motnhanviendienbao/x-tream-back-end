package com.example.xtream.dto.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateInvestorDetailsRequestDTO {
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    private String district;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "(MALE|FEMALE)")
    private String gender;
    @NotBlank
    private String givenName;
    @NotBlank
    private String homePhone;
    private String mobile;
    @Pattern(regexp = "PHONE|EMAIL")
    private String nextBestContactMethod;
    @NotBlank
    @Pattern(regexp = "^\\d{6}$")
    private String postCode;
    private String preferredContactMethod;
    private String propertyName;
    private Integer retirementAge;
    @NotBlank
    private String streetName1;
    private String streetName2;
    @NotBlank
    private String streetNumber;
    @NotBlank
    private String surname;
    @NotBlank
    @Pattern(regexp = "^\\d{10}(-\\d{3})?$")
    private String taxFileNumber;
    private String title;

}
