//package com.example.xtream.security.basicAuthen.service;
//
//import com.example.xtream.constant.ErrorMessages;
//import com.example.xtream.repository.TokenRepository;
//import com.example.xtream.security.basicAuthen.userDetail.UserDetail;
//import lombok.RequiredArgsConstructor;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
///**
// * Apply in: BasicAuthentication
// * Implement user detail service for providing loadUserByUsername() in spring security
// * This is one of steps doing check username in authentication process
// * if username not exist, throw exception
// * if username exist,load user information return, includes:
// * username,password,GrantedAuthority <ps: list of user permission>
// * <p>
// * This project utilizes the principal of basic authentication to apply on token-based auth
// * with cheating replace:
// * username = token id
// * password = token value
// */
//@Service
//@RequiredArgsConstructor
//public final class TokenBasedUserDetailService implements UserDetailsService {
//
//    private final TokenRepository tokenRepository;
//    private static final Logger logger = LogManager.getLogger(TokenBasedUserDetailService.class);
//
//
//    /**
//     * Return UserDetails
//     * @param tokenID take role as username
//     * @return UserDetails as is principal in Authentication object if success
//     * @throws UsernameNotFoundException checked exception for not found token id
//     */
//    @Override
//    public UserDetails loadUserByUsername(String tokenID) throws UsernameNotFoundException {
//        logger.info("[NGOC TU SERVER]: [Load User By Username Function] Run Check With TokenID Is: {}",tokenID);
//        Token token = tokenRepository.findById(Long.valueOf(tokenID)).orElseThrow(()-> new UsernameNotFoundException(ErrorMessages.TOKEN_CREDENTIAL_NOT_FOUND));
//        return new UserDetail(token.getId().toString(),token.getValue(), Collections.emptyList() );
//    }
//}
