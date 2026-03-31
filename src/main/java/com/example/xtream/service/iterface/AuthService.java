package com.example.xtream.service.iterface;
import com.example.xtream.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseDTO login(String username, String password);
    ResponseDTO register(String username, String password);
    ResponseDTO resetPassword(String username, String newPassword);
}
