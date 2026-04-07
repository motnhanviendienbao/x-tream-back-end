package com.example.xtream.service.iterface;
import com.example.xtream.dto.response.ResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseDTO login(String username, String password, HttpServletResponse response);
    ResponseDTO register(String username, String password);
    ResponseDTO resetPassword(String username, String newPassword);
}
