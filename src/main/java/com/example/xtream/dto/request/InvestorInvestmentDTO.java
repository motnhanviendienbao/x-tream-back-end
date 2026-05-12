package com.example.xtream.dto.request;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class InvestorInvestmentDTO {
    private int investmentNameOrId;
    private int investmentStrategy;


}
