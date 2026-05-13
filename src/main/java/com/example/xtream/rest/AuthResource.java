package com.example.xtream.rest;
import com.example.xtream.dto.request.RegisterDTO;
import com.example.xtream.dto.request.LoginDTO;
import com.example.xtream.dto.request.ResetPasswordDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDTO> login(@RequestBody @Valid LoginDTO loginRequest ) {
        return ResponseEntity.ok(authServiceImpl.login(loginRequest.getUsername(),loginRequest.getPassword()));
    }

    /**
     * Create new account
     *
     * @param request   information
     * @return  status
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid RegisterDTO request) {
        return ResponseEntity.ok(authServiceImpl.register(request.getUsername(), request.getPassword(), request.getCode()));
    }

    /**
     * Reset password for user by admin
     *
     * @param request   information
     * @return  status
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody @Valid ResetPasswordDTO request) {
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
