package com.example.xtream.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String newPassword;
}
