package com.example.xtream.service;
import com.example.xtream.dto.response.ResponseDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public interface SystemService {
    ResponseDTO login(String username, String password);
    ResponseDTO register(String username, String password, String code);
    ResponseDTO resetPassword(String username, String newPassword);
    List<SimpleGrantedAuthority> getAuthoritiesByUserId(int userId);
    ResponseDTO getAccessToken(String refreshToken);
}
