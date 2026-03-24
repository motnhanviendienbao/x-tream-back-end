package com.example.xtream;

import com.example.xtream.api.models.Auth.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.time.OffsetDateTime;
import java.util.List;

public class UserDetails extends User {
    private Long userId;
    private String hashedPassword;
    private String username;
    private Boolean resetPass;
    private int failedCount;
    private Boolean locked;
    private String role;
    private OffsetDateTime lastAccess;
    public UserDetails(
            Token token,
            List<GrantedAuthority> permissions,
            Long userId,
            String hashedPassword,
            String username,
            Boolean resetPass,
            int failedCount,
            Boolean locked,
            String role,
            OffsetDateTime lastAccess
    ) {
        super(token.getId().toString(), token.getValue(), permissions);
        this.userId = userId;
        this.hashedPassword = hashedPassword;
        this.failedCount = failedCount;
        this.username = username;
        this.resetPass = resetPass;
        this.locked = locked;
        this.role = role;
        this.lastAccess = lastAccess;

    }



    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getResetPass() {
        return resetPass;
    }

    public void setResetPass(Boolean resetPass) {
        this.resetPass = resetPass;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public OffsetDateTime getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(OffsetDateTime lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Long getCustomerId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDetails that = (UserDetails) o;

        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }
}

