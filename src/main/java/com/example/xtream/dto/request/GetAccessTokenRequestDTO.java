package com.example.xtream.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAccessTokenRequestDTO {
    @NotBlank
    public String refresh;
}
