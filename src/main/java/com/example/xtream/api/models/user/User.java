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

    @Column(name = "user_name", nullable = false, length = 255,unique = true)
    private String userName;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "is_reset_hashed_password")
    private Boolean isResetHashedPass;

    @Column(name = "failed_count")
    private Integer failedCount;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "lock_until")
    private OffsetDateTime lockUntil;

    @Column(name = "failed_session_start")
    private OffsetDateTime failedSessionStart;

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

    public Boolean getIsResetHashedPass() {
        return isResetHashedPass;
    }

    public void setIsResetHashedPass(Boolean resetPass) {
        this.isResetHashedPass = isResetHashedPass;
    }

    public Integer getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(Integer failedCount) {
        this.failedCount = failedCount;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public OffsetDateTime getLockUntil() {
        return lockUntil;
    }

    public void setLockUntil(OffsetDateTime lockUntil) {
        this.lockUntil = lockUntil;
    }

    public OffsetDateTime getFailedSessionStart() {
        return failedSessionStart;
    }

    public void setFailedSessionStart(OffsetDateTime failedSessionStart) {
        this.failedSessionStart = failedSessionStart;
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
