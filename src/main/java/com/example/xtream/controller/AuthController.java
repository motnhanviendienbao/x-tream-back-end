package com.example.xtream.controller;
import com.example.xtream.dto.request.RegisterRequestDTO;
import com.example.xtream.dto.request.LoginRequestDTO;
import com.example.xtream.dto.request.ResetPasswordRequestDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceImpl authServiceImpl;
    public AuthController(final AuthServiceImpl authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest , HttpServletResponse response) {
        return ResponseEntity.ok(authServiceImpl.login(loginRequest.getUsername(),loginRequest.getPassword(), response));
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        return ResponseEntity.ok(authServiceImpl.register(request.getUsername(), request.getPassword()));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO request) {
        return ResponseEntity.ok(authServiceImpl.resetPassword(request.getUsername(),request.getNewPassword()));
    }
    @GetMapping("/test")
    public String testApi() {
        return "SUCCESS";
    }
}
