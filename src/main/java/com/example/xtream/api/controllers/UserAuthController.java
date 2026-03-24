package com.example.xtream.api.controllers;

import com.example.xtream.api.DTO.request.LoginRequest;
import com.example.xtream.api.DTO.request.RegisterRequest;
import com.example.xtream.api.DTO.request.ResetPasswordRequest;
import com.example.xtream.api.DTO.response.LoginResponse;
import com.example.xtream.api.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    UserAuthService userAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest ) {
        LoginResponse token = userAuthService.login(loginRequest.getUsername(),loginRequest.getPassword());
        return ResponseEntity.status(200).body(token);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userAuthService.register(request.getUsername(), request.getPassword());
        return ResponseEntity.status(201).build();
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        userAuthService.resetPassword(request.getUsername() ,request.getNewPassword());
        return ResponseEntity.ok().build();
    }
}
