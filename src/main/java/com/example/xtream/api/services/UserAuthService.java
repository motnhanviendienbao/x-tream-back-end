package com.example.xtream.api.services;

import com.example.xtream.api.DTO.response.LoginResponse;
import com.example.xtream.api.models.Auth.Token;
import com.example.xtream.api.models.user.User;
import com.example.xtream.api.repositories.TokenRepository;
import com.example.xtream.api.repositories.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class UserAuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private TokenRepository tokenRepository;

    public LoginResponse login(String username, String password) {

        User user = userAuthRepository
                .findByUserName(username)
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Invalid username"));

        OffsetDateTime now = OffsetDateTime.now();
        // If account is locked, unlock automatically after 5 minutes.
        // check isLock?
        if (Boolean.TRUE.equals(user.getIsLocked())) {
            OffsetDateTime lockUntil = user.getLockUntil();
            if (lockUntil != null && lockUntil.isAfter(now)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is locked");
            }
            user.setIsLocked(false);
            user.setLockUntil(null);
            user.setFailedCount(0);
            user.setFailedSessionStart(null);
            user.setUpdatedAt(now);
            userAuthRepository.save(user);
        }

        // check is forced reset hash pass
        if (Boolean.TRUE.equals(user.getIsResetHashedPass())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password reset required");
        }

        //
        if (!passwordEncoder.matches(password, user.getHashedPassword())) {
            OffsetDateTime failedSessionStart = user.getFailedSessionStart();
            if (failedSessionStart == null || ChronoUnit.MINUTES.between(failedSessionStart, now) >= 10) {
                failedSessionStart = now;
                user.setFailedSessionStart(null);
                user.setFailedCount(0);
            }

            int failed = (user.getFailedCount() == null ? 0 : user.getFailedCount()) + 1;
            user.setFailedCount(failed);
            user.setUpdatedAt(now);

            // If failed attempts within 10 minutes > 5, lock for 5 minutes and reset attempts to 0.
            if (failed > 5) {
                user.setIsLocked(true);
                user.setLockUntil(now.plusMinutes(5));
                user.setFailedCount(0);
            }
            userAuthRepository.save(user);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        user.setFailedCount(0);
        user.setFailedSessionStart(null);
        user.setIsLocked(false);
        user.setLockUntil(null);
        user.setLastAccess(now);
        user.setUpdatedAt(now);
        userAuthRepository.save(user);

        String rawToken = UUID.randomUUID().toString().replace("-", "");
        Token token = new Token();
        token.setValue(passwordEncoder.encode(rawToken));
        token.setExpiration(OffsetDateTime.now().plusDays(7));
        if (user.getRole() == User.Role.ADMIN) {
            token.setAdminId(user.getId());
        } else {
            token.setCustomerId(user.getId());
        }
        tokenRepository.save(token);

        LoginResponse result = new LoginResponse();
        result.setToken(token.getId() + ":" + rawToken);
        return result;
    }

    public void register (String username, String password) {

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username/password is required");
        }
        if (userAuthRepository.findByUserName(username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        User user = new User();
        user.setUserName(username);
        user.setHashedPassword(passwordEncoder.encode(password));
        user.setIsLocked(false);
        user.setFailedCount(0);
        user.setFailedSessionStart(null);
        user.setLockUntil(null);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        user.setIsResetHashedPass(false);
        user.setRole(User.Role.USER);

        userAuthRepository.save(user);

    }

    public void resetPassword(String username, String replacePlainTextPass) {
        try{

            User user = userAuthRepository.findByUserName(username).orElseThrow(
                    () -> new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED,
                            "Invalid username"));
            // replace hash pass
            user.setHashedPassword(passwordEncoder.encode(replacePlainTextPass));
            // reset is_reset_hashed_password
            user.setIsResetHashedPass(false);
            userAuthRepository.save(user);

        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "something went wrong, contact your admin");
        }
    }

}
