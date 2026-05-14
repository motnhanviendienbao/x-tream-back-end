package com.example.xtream.service;
import com.example.xtream.dto.response.ResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface SystemService {
    ResponseDTO login(String username, String password);
    ResponseDTO register(String username, String password, String code);
    ResponseDTO resetPassword(String username, String newPassword);
}
