package com.example.xtream.rest;
import com.example.xtream.dto.request.RegisterRequestDTO;
import com.example.xtream.dto.request.LoginRequestDTO;
import com.example.xtream.dto.request.ResetPasswordRequestDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * Auth resource
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthResource {

    private final AuthServiceImpl authServiceImpl;

    /**
     * Check Authentication
     *
     * @param loginRequest  auth information
     * @return token if success, opposite is error status
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest ) {
        return ResponseEntity.ok(authServiceImpl.login(loginRequest.getUsername(),loginRequest.getPassword()));
    }

    /**
     * Create new account
     *
     * @param request   information
     * @return  status
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        return ResponseEntity.ok(authServiceImpl.register(request.getUsername(), request.getPassword(), request.getCode()));
    }

    /**
     * Reset password for user by admin
     *
     * @param request   information
     * @return  status
     */
    @Secured("AUTH:UPDATE")
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO request) {
        return ResponseEntity.ok(authServiceImpl.resetPassword(request.getUsername(),request.getNewPassword()));
    }

    /**
     * Test access resource
     *
     * @return string
     */
    @GetMapping("/ping")
    public String pingAuth() {
        return "welcome";
    }
}
