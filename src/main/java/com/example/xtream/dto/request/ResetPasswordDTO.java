package com.example.xtream.dto.request;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPasswordDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String newPassword;
}
