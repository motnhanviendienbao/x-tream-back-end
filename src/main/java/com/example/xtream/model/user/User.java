package com.example.xtream.model.user;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hashed_password", nullable = false, length = 255)
    private String hashedPassword;
    @Column(name = "user_name", nullable = false, length = 255,unique = true)
    private String userName;
    @Column(name = "reset_password", nullable = true)
    private Boolean isResetPassword;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;


    public Boolean getResetPassword() {
        return isResetPassword;
    }
    public void setResetPassword(Boolean resetPassword) {
        isResetPassword = resetPassword;
    }
    public Long getId() {
        return id;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        ADMIN,
        CUSTOMER
    }
}
