package com.example.xtream.dto.request;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class InvestorInvestmentRequestDTO {
    @NotBlank
    private String investmentCode;
    @NotBlank
    @Range(min = 1,max = 100)
    private int investmentStrategy;
}
