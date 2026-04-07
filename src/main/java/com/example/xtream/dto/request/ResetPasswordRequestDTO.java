package com.example.xtream.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String newPassword;
}
