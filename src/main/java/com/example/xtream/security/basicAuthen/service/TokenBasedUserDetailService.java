package com.example.xtream.security.basicAuthen.service;

import com.example.xtream.constant.SystemRole;
import com.example.xtream.security.basicAuthen.modelUserDetail.AdminUserDetail;
import com.example.xtream.model.Token;
import com.example.xtream.repository.TokenRepository;
import com.example.xtream.security.basicAuthen.modelUserDetail.CustomerUserDetail;
import com.example.xtream.service.impl.AuthServiceImpl;
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
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class TokenBasedUserDetailService implements UserDetailsService {

    private final TokenRepository tokenRepository;
    private static final Logger logger = LogManager.getLogger(TokenBasedUserDetailService.class);


    @Override
    public UserDetails loadUserByUsername(String tokenID) throws UsernameNotFoundException {
        Token token = tokenRepository.findById(Long.valueOf(tokenID)).orElseThrow(()-> new UsernameNotFoundException("Token Not Found"));
        return makeUserDetail(token);
    }
    private UserDetails makeUserDetail(Token token) {
        // system just has only 2 role: customer,admin.
        // get customerID from token
        // if present means this is customer
        logger.debug("token.getCustomerId(): " + token.getCustomerId());
        if (token.getCustomerId() != null) {
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(SystemRole.CUSTOMER));
            return new CustomerUserDetail(token.getId().toString(),token.getValue(),authorities);
        }
        // get adminID from token
        // if present means this is admin
        logger.debug("token.getAdminId() " + token.getAdminId());
        if (token.getAdminId() != null) {
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(SystemRole.ADMIN));
            return new AdminUserDetail(token.getId().toString(),token.getValue(),authorities);
        }
        return null;
    }
}
