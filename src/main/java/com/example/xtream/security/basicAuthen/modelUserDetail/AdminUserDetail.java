package com.example.xtream.security.basicAuthen.modelUserDetail;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.OffsetDateTime;
import java.util.Collection;

/**
 * This extending aims to check type in runtime by instance of
 *  object <belong to this class> instance of AdminUserDetail = true
 *  CustomerUserDetail instance of AdminUserDetail = false
 */
public class AdminUserDetail extends User {

    public AdminUserDetail(String tokenID, @Nullable String tokenValue, Collection<? extends GrantedAuthority> authorities) {
        super(tokenID, tokenValue, authorities);
    }
}
