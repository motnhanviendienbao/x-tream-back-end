package com.example.xtream.security.basicAuthen.userDetail;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * This extending aims to check type in runtime by instance of
 *  object <belong to this class> instance of AdminUserDetail = true
 *  CustomerUserDetail instance of AdminUserDetail = false
 * <p>
 * User detail is going to be assigned for principal in Authentication object if auth success.
 * if the forcePrincipalAsString flag was set to true, principal was username in user detail
 */
public class UserDetail extends UserBase {

    public UserDetail(String tokenID, @Nullable String tokenValue, Collection<? extends GrantedAuthority> authorities) {
        super(tokenID, tokenValue, authorities);
    }
}
