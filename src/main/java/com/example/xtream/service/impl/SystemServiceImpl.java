package com.example.xtream.service.impl;
import com.example.xtream.constant.ErrorMessages;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.exception.custom.InvalidUsernamePasswordAuthenticationException;
import com.example.xtream.model.User;
import com.example.xtream.repository.TokenRepository;
import com.example.xtream.repository.UserRepository;
import com.example.xtream.service.TokenAuthenticationService;
import com.example.xtream.service.SystemService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Auth resource service
 */
@Service
@RequiredArgsConstructor
public class SystemServiceImpl implements SystemService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TokenAuthenticationService tokenAuthenticationService;
    private static final Logger logger = LogManager.getLogger(SystemServiceImpl.class);

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
        String token = tokenAuthenticationService.createToken(username);
        return ResponseDTO.builder().response(token).build();
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
        User user = new User();
        user.setUserName(username);
        user.setHashedPassword(passwordEncoder.encode(password));
        userRepository.save(user);

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
                                ()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_USERNAME));
        // hash and replace pass + reset state record
        user.setHashedPassword(passwordEncoder.encode(newPassword));
        userRepository.saveAndFlush(user);

        return ResponseDTO.builder().build();
    }

    private User checkUsernameAndPassword(String username, String password) {
        // check username
        User user =
                userRepository
                        .findByUserName(username)
                        .orElseThrow(() -> new InvalidUsernamePasswordAuthenticationException(ErrorMessages.INVALID_USERNAME));
        // check password
        if(!passwordEncoder.matches(password,user.getHashedPassword()))
        {
            throw new InvalidUsernamePasswordAuthenticationException(ErrorMessages.INVALID_PASSWORD);
        }
        return user;
    }
}