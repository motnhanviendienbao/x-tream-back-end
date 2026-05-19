package com.example.xtream.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAccessTokenDTO {
    @NotBlank
    public String refresh;
}
