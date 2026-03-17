package com.example.xtream.api.controllers;

import com.example.xtream.api.dto.request.LoginRequest;
import com.example.xtream.api.dto.request.RegisterRequest;
import com.example.xtream.api.dto.response.LoginResponse;
import com.example.xtream.api.dto.response.RegisterResponse;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest ) {
        LoginResponse result = userAuthService.login(loginRequest.getUsername(),loginRequest.getPassword());
        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {

        userAuthService.register(request.getUsername(), request.getPassword());

        RegisterResponse response = new RegisterResponse();
        response.setMessage("User registered successfully");

        return ResponseEntity.status(201).body(response);
    }
}
