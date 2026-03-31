package com.example.xtream.service.impl;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.auth.Token;
import com.example.xtream.model.user.User;
import com.example.xtream.repository.TokenRepository;
import com.example.xtream.repository.UserRepository;
import com.example.xtream.service.iterface.AuthService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;
@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(final PasswordEncoder passwordEncoder, final UserRepository userRepository, final TokenRepository tokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }
    @Transactional
    public ResponseDTO login(String username, String password) {
        User user = checkUsernameAndPassword(username,password);
        String specifyRole = getSpecificRole(user);
        return createTokenForClient(specifyRole,user);
    }
    @Transactional
    public ResponseDTO register (String username, String password) {
        validateUsernameAndPassword(username,password);
        doRegister(username,password);
        return ResponseDTO.builder().response("created").build();
    }
    @Override
    @Transactional
    public ResponseDTO resetPassword(String username, String newPassword) {
        // check username exist
        User user =
                userRepository
                .findByUserName(username).orElseThrow(
                ()-> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid username")
                );
        // hash and replace pass + reset state record
        user.setHashedPassword(passwordEncoder.encode(newPassword));
        userRepository.saveAndFlush(user);
        return ResponseDTO.builder()
                .response("reset password success")
                .build();
    }
    // sub-function
    // login
    private User checkUsernameAndPassword(String username, String password) {
        // check username
        User user =
                userRepository
                .findByUserName(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid username"));
        // check password
        if(!passwordEncoder.matches(password,user.getHashedPassword()))
        {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid password");
        }
        return user;
    }
    private String getSpecificRole(User user) {
        return "admin".equalsIgnoreCase(user.getRole().toString()) ? "admin" : "customer";
    }
    private ResponseDTO createTokenForClient(String specifyRole, User user) {
        if("customer".equalsIgnoreCase(specifyRole)) {
            // old user means have token assign for user, just get it up to reuse
            Token tokenCustomer =
                    tokenRepository
                            .findById(user.getId())
                            .orElseGet(() ->
                            {
                                // new user means generate new token assign for this user
                                Token tk = new Token();
                                tk.setValue(UUID.randomUUID().toString()
                                        .replace("-",""));
                                tk.setExpiration(OffsetDateTime.now().plusDays(1));
                                tk.setCustomerId(user.getId());
                                tokenRepository.saveAndFlush(tk);
                                return tk;
                            });

            String rawToken = tokenCustomer.getId()+":"+tokenCustomer.getValue();
            String createdToken = Base64.getEncoder().encodeToString(rawToken.getBytes());
            return ResponseDTO.builder().response(createdToken).build();
        }
        // for admin case
        Token tokenAdmin = tokenRepository.findById(user.getId()).orElseGet(
                () -> {
                    Token tk = new Token();
                    tk.setValue(UUID.randomUUID().toString().replace("-",""));
                    tk.setExpiration(OffsetDateTime.now().plusDays(1));
                    tk.setAdminId(user.getId());
                    tokenRepository.saveAndFlush(tk);
                    return tk;
                }
        );

        String rawToken = tokenAdmin.getId()+":"+tokenAdmin.getValue();
        String createdToken = Base64.getEncoder().encodeToString(rawToken.getBytes());
        return ResponseDTO.builder().response(createdToken).build();
    }
    // register
    private void validateUsernameAndPassword(String username,String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username/password is required");
        }
        if (userRepository.findByUserName(username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
    }
    private void doRegister(String username, String password) {
        User user = new User();
        user.setUserName(username);
        user.setHashedPassword(passwordEncoder.encode(password));

        String prefixCheck = username.substring(0,5);

        if ("admin".equalsIgnoreCase(prefixCheck)) {
            user.setRole(User.Role.ADMIN);
        } else {
            user.setRole(User.Role.CUSTOMER);
        }
        userRepository.save(user);
    }
}
