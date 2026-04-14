package com.example.xtream.security.service;

import com.example.xtream.constant.SystemRole;
import com.example.xtream.security.modelUserDetail.AdminUserDetail;
import com.example.xtream.model.Token;
import com.example.xtream.repository.TokenRepository;
import com.example.xtream.security.modelUserDetail.CustomerUserDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;
@Service
public class TokenBasedUserDetailService implements UserDetailsService {
    private final TokenRepository tokenRepository;
    public TokenBasedUserDetailService(final TokenRepository tokenRepository ) {
        this.tokenRepository = tokenRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String tokenID) throws UsernameNotFoundException {
        Token token = tokenRepository.findById(Long.valueOf(tokenID)).orElseThrow(()-> new UsernameNotFoundException("Token Not Found"));
        return makeUserDetail(token);
    }
    private UserDetails makeUserDetail(Token token) {
        // system just has only 2 role: customer,admin.
        // get customerID from token
        Optional<Long> customerID = token.getCustomerId();
        // if present means this is customer
        if (customerID.isPresent()) {
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(SystemRole.CUSTOMER));
            return new CustomerUserDetail(token.getId().toString(),token.getValue(),authorities,customerID.get());
        }
        // get adminID from token
        Optional<Long> adminID = token.getAdminId();
        // if present means this is admin
        if (adminID.isPresent()) {
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(SystemRole.ADMIN));
            return new AdminUserDetail(token.getId().toString(),token.getValue(),authorities,adminID.get());
        }
        return null;
    }
}
