package com.example.xtream.security.basicAuthen.modelUserDetail;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Base class define TTL
 */
public abstract class UserBase extends User {

    /**
     * TTL value
     */
    protected Date TTL;

    /**
     * Constructor
     */
    public UserBase(String username, @Nullable String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.TTL = new Date();
        Calendar var4 ;
        (var4 = Calendar.getInstance()).setTime(this.TTL);
        var4.add(Calendar.MINUTE,2);
        this.TTL = var4.getTime();

    }

    /**
     * check expire status of current instance
     * @return boolean
     */
    public boolean isExpire() {
        if (TTL != null ) {
            return this.TTL.before(new Date());
        }
        return false;
    }
}
