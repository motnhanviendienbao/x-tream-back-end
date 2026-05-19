package com.example.xtream.service.impl;
import com.example.xtream.constant.ErrorMessages;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.model.User;

import com.example.xtream.repository.SystemRepository;
import com.example.xtream.security.jwtAuthen.cache.CacheObject;
import com.example.xtream.security.jwtAuthen.cache.CacheObjectImpl;
import com.example.xtream.service.LocalCache;
import com.example.xtream.service.TokenAuthenticationService;
import com.example.xtream.service.SystemService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.webresources.Cache;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Auth resource service
 */
@Service
@RequiredArgsConstructor
public class SystemServiceImpl implements SystemService {

    private final PasswordEncoder passwordEncoder;
    private final SystemRepository systemRepository;
    private final TokenAuthenticationService tokenAuthenticationService;
    private final LocalCache localCache;
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
    public ResponseDTO login(String username, String password)
    {
        // check username
        User user = systemRepository.findByUserName(username).orElseThrow(() -> new BadCredentialsException(ErrorMessages.INVALID_USERNAME));
        // check password
        if(!passwordEncoder.matches(password,user.getHashedPassword())) throw new BadCredentialsException(ErrorMessages.INVALID_PASSWORD);

        String userType = user.getRole();
        Long userId= user.getId();

        String accessToken = tokenAuthenticationService.createToken(userId,userType);
        String refreshToken = UUID.randomUUID().toString().replace("-","");

        long ttl = Instant.now().plus(10, ChronoUnit.MINUTES).toEpochMilli();
        CacheObject refreshTokenObject = new CacheObjectImpl(refreshToken,userId,ttl);
        localCache.putCache(refreshTokenObject);

        return ResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Create new account
     *
     * @param username  information
     * @param password  information
     * @return  ResponseDTO
     */
    @Transactional
    public ResponseDTO register (String username, String password, String role)
    {
        User user = new User();
        user.setUserName(username);
        user.setHashedPassword(passwordEncoder.encode(password));
        user.setRole(role);
        systemRepository.save(user);

        return ResponseDTO.builder()
                .build();
    }

    /**
     * Reset user password by admin
     * @param username  information
     * @param newPassword   information
     * @return  ResponseDTO
     */
    @Transactional
    public ResponseDTO resetPassword(String username, String newPassword)
    {
        // check username exist
        User user = systemRepository.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException(ErrorMessages.INVALID_USERNAME));
        // hash and replace pass + reset state record
        user.setHashedPassword(passwordEncoder.encode(newPassword));
        systemRepository.saveAndFlush(user);

        return ResponseDTO.builder()
                .build();
    }

    @Override
    @Transactional
    public List<SimpleGrantedAuthority> getAuthoritiesByUserId(Long userId)
    {
        return systemRepository.findAuthoritiesByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResponseDTO getAccessToken(String refreshToken)
    {
        CacheObject cacheObject = localCache.getCache(refreshToken);

        if (StringUtils.isBlank(refreshToken)) throw new BadCredentialsException(ErrorMessages.TOKEN_EXPIRE_CODE);
        if (Objects.isNull(cacheObject)) throw new BadCredentialsException(ErrorMessages.TOKEN_CREDENTIAL_NOT_FOUND);

        Long userId = (Long) cacheObject.getCacheValue();
        String userType = systemRepository.findById(userId).orElseThrow(() -> new BadCredentialsException(ErrorMessages.USER_NOT_EXIST)).getRole();

        String newAccessToken = tokenAuthenticationService.createToken(userId,userType);
        if (StringUtils.isBlank(newAccessToken)) throw new RuntimeException(ErrorMessages.TOKEN_EXPIRE_CODE);

        return ResponseDTO.builder()
                .accessToken(newAccessToken)
                .build();
    }
}