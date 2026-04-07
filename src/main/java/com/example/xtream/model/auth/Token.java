package com.example.xtream.model.auth;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Optional;

@Entity
@Table(name = "tokens")
public class Token {
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Getter
    private String value;

    private OffsetDateTime expiration;

    private Long customerId;

    private Long adminId;

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public OffsetDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(OffsetDateTime expiration) {
        this.expiration = expiration;
    }

    public Optional<Long> getCustomerId() {
        return Optional.ofNullable(customerId);
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Optional<Long> getAdminId() {
        return Optional.ofNullable(adminId);
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

}

