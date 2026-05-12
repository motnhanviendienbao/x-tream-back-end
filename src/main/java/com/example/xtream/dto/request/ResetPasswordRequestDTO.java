package com.example.xtream.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ResetPasswordRequestDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String newPassword;
}
