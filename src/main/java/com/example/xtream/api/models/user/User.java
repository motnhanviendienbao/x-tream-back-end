package com.example.xtream.api.models.user;

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

    @Column(name = "user_name", nullable = false, length = 255)
    private String userName;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "reset_pass")
    private Boolean resetPass;

    @Column(name = "failed_count")
    private Integer failedCount;

    @Column(name = "locked")
    private Boolean locked;

    @Column(name = "lock_until")
    private OffsetDateTime lockUntil;

    @Column(name = "failed_window_start")
    private OffsetDateTime failedWindowStart;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "last_access")
    private OffsetDateTime lassAccess;

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getLassAccess() {
        return lassAccess;
    }

    public void setLassAccess(OffsetDateTime lassAccess) {
        this.lassAccess = lassAccess;
    }

    public OffsetDateTime getLastAccess() {
        return lassAccess;
    }

    public void setLastAccess(OffsetDateTime lastAccess) {
        this.lassAccess = lastAccess;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getResetPass() {
        return resetPass;
    }

    public void setResetPass(Boolean resetPass) {
        this.resetPass = resetPass;
    }

    public Integer getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(Integer failedCount) {
        this.failedCount = failedCount;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public OffsetDateTime getLockUntil() {
        return lockUntil;
    }

    public void setLockUntil(OffsetDateTime lockUntil) {
        this.lockUntil = lockUntil;
    }

    public OffsetDateTime getFailedWindowStart() {
        return failedWindowStart;
    }

    public void setFailedWindowStart(OffsetDateTime failedWindowStart) {
        this.failedWindowStart = failedWindowStart;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        ADMIN,
        USER
    }
}
