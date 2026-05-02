package com.example.xtream.security.basicAuthen.service;

import com.example.xtream.constant.AuthenticationConstant;
import com.example.xtream.constant.ErrorMessages;
import com.example.xtream.security.basicAuthen.modelUserDetail.AdminUserDetail;
import com.example.xtream.model.Token;
import com.example.xtream.repository.TokenRepository;
import com.example.xtream.security.basicAuthen.modelUserDetail.CustomerUserDetail;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Apply in: BasicAuthentication
 * Implement user detail service for providing loadUserByUsername() in spring security
 * This is one of steps doing check username in authentication process
 * if username not exist, throw exception
 * if username exist,load user information return, includes:
 * username,password,GrantedAuthority <ps: list of user permission>
 * <p>
 * This project utilizes the principal of basic authentication to apply on token-based auth
 * with cheating replace:
 * username = token id
 * password = token value
 */
@Service
@RequiredArgsConstructor
public class TokenBasedUserDetailService implements UserDetailsService {

    private final TokenRepository tokenRepository;
    private static final Logger logger = LogManager.getLogger(TokenBasedUserDetailService.class);


    /**
     * Return UserDetails
     * @param tokenID take role as username
     * @return UserDetails as is principal in Authentication object if success
     * @throws UsernameNotFoundException checked exception for not found token id
     */
    @Override
    public UserDetails loadUserByUsername(String tokenID) throws UsernameNotFoundException {
        logger.info("[NGOC TU SERVER]: Load User By Username Function Run Check With TokenID Is: {}",tokenID);
        Token token = tokenRepository.findById(Long.valueOf(tokenID)).orElseThrow(()-> new UsernameNotFoundException(ErrorMessages.Auth.CREDENTIAL_NOT_FOUND.getMessage()));
        return makeUserDetail(token);
    }

    /**
     * Separate method from big to small for clear
     * @param token Token entity from DB
     * @return UserDetail object for processing in next steps
     */
    private UserDetails makeUserDetail(Token token) {
        logger.info("[NGOC TU SERVER]: Load User By Username Function Processing . . . ");
        // system just has only 2 role: customer,admin.
        // get customerID from token
        // if present means this is customer
        logger.info("[NGOC TU SERVER]: Customer ID Belongs to Token Entity After Loaded: " + token.getCustomerId());
        if (token.getCustomerId() != null) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
            logger.info("[NGOC TU SERVER]: Load User By Username Function Return CustomerUserDetail ");
            return new CustomerUserDetail(token.getId().toString(),token.getValue(),authorities);
        }
        // get adminID from token
        // if present means this is admin
        logger.info("[NGOC TU SERVER]: Customer ID Belongs to Token Entity After Loaded: " + token.getAdminId());
        if (token.getAdminId() != null) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            logger.info("[NGOC TU SERVER]: Load User By Username Function Return AdminUserDetail");
            return new AdminUserDetail(token.getId().toString(),token.getValue(),authorities);
        }
        logger.info("[NGOC TU SERVER]: Load User By Username Function Return Null!!! ");
        return null;
    }
}
