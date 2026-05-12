package com.example.xtream.service.impl;
import com.example.xtream.constant.ErrorMessages;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.exception.custom.InvalidUsernamePasswordAuthenticationException;
import com.example.xtream.exception.custom.UsernameExistException;
import com.example.xtream.model.Token;
import com.example.xtream.model.User;
import com.example.xtream.repository.TokenRepository;
import com.example.xtream.repository.UserRepository;
import com.example.xtream.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;

/**
 * Auth resource service
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);

    /**
     * Auth the user
     *
     * @param username  information
     * @param password  information
     * @return  ResponseDTO contains token
     */
    @Override
    @Transactional
    public ResponseDTO login(String username, String password) {
        User user = checkUsernameAndPassword(username,password);
        return createTokenForClient(user);
    }

    /**
     * Create new account
     *
     * @param username  information
     * @param password  information
     * @return  ResponseDTO
     */
    @Transactional
    public ResponseDTO register (String username, String password, String code) {
        validateUsernameAndPassword(username,password);
        doRegister(username,password);
        return ResponseDTO.builder().build();
    }

    /**
     * Reset user password by admin
     * @param username  information
     * @param newPassword   information
     * @return  ResponseDTO
     */
    @Transactional
    public ResponseDTO resetPassword(String username, String newPassword) {
        // check username exist
        User user =
                userRepository
                        .findByUserName(username).orElseThrow(
                                ()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.Auth.INVALID_USERNAME.getMessage()));
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
        try {
            User user =
                    userRepository
                            .findByUserName(username)
                            .orElseThrow(() -> new InvalidUsernamePasswordAuthenticationException(ErrorMessages.Auth.INVALID_USERNAME.getMessage()));
            // check password
            if(!passwordEncoder.matches(password,user.getHashedPassword()))
            {
                throw new InvalidUsernamePasswordAuthenticationException(ErrorMessages.Auth.INVALID_PASSWORD.getMessage());
            }
            return user;
        } catch ( InvalidUsernamePasswordAuthenticationException exception) {

            throw new InvalidUsernamePasswordAuthenticationException(ErrorMessages.Auth.INVALID_USERNAME_OR_PASSWORD.getMessage());
        }
    }

    private ResponseDTO createTokenForClient( User user) {
            // old user means have token assign for user, just get it up to reuse
            Token tokenCustomer =
                    tokenRepository
                            .findById(user.getId())
                            .orElseGet(() ->
                            {
                                // new user means generate new token assign for this user
                                Token tk = new Token();
                                tk.setValue(UUID.randomUUID().toString()
                                        .replace("-", ""));
                                tk.setExpiration(OffsetDateTime.now().plusDays(1));
                                tk.setCustomerId(user.getId());
                                tokenRepository.saveAndFlush(tk);
                                return tk;
                            });

            String rawToken = tokenCustomer.getId() + ":" + tokenCustomer.getValue();
            String createdToken = Base64.getEncoder().encodeToString(rawToken.getBytes());
            return ResponseDTO.builder().response(createdToken).build();
    }

    // register
    private void validateUsernameAndPassword(String username,String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new InvalidUsernamePasswordAuthenticationException(ErrorMessages.Auth.USERNAME_PASSWORD_IS_REQUIRE.getMessage());
        }

        if (userRepository.findByUserName(username).isPresent()) {
            throw new UsernameExistException(ErrorMessages.Auth.USERNAME_ALREADY_EXIST.getMessage());
        }
    }

    private void doRegister(String username, String password) {
        User user = new User();
        user.setUserName(username);
        user.setHashedPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}