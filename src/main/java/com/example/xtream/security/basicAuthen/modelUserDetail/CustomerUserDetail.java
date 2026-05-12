package com.example.xtream.security.basicAuthen.modelUserDetail;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.OffsetDateTime;
import java.util.Collection;

/**
 * User Detail Object could be custom for field
 * <p>
 * User detail is going to be assigned for principal in Authentication object if auth success.
 * if the forcePrincipalAsString flag was set to true, principal was username in user detail
 */
public class CustomerUserDetail extends UserBase {

    public CustomerUserDetail(String username, @Nullable String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
