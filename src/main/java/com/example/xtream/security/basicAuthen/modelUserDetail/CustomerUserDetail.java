package com.example.xtream.security.basicAuthen.modelUserDetail;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.OffsetDateTime;
import java.util.Collection;

public class CustomerUserDetail extends User {

    public CustomerUserDetail(String username, @Nullable String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomerUserDetail(String username, @Nullable String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(" [");
        sb.append("Username=").append(this.getUsername()).append(", ");
        sb.append("Password=").append(this.getPassword()).append(", ");
        sb.append("Enabled=").append(this.isEnabled()).append(", ");
        sb.append("AccountNonExpired=").append(this.isAccountNonExpired()).append(", ");
        sb.append("CredentialsNonExpired=").append(this.isCredentialsNonExpired()).append(", ");
        sb.append("AccountNonLocked=").append(this.isAccountNonLocked()).append(", ");
        sb.append("Granted Authorities=").append(this.getAuthorities()).append("]");
        return sb.toString();
    }

}
