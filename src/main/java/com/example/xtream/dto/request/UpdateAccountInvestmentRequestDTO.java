package com.example.xtream.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class UpdateAccountInvestmentRequestDTO {
    @NotNull @Range(min = 1,max = 100)
    Integer strategy;
}
