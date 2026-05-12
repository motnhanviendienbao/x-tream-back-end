package com.example.xtream.model;

import com.example.xtream.config.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hashed_password", nullable = false, length = 255)
    private String hashedPassword;
    @Column(name = "user_name", nullable = false, length = 255,unique = true)
    private String userName;
    @Column(name = "reset_password", nullable = true)
    private Boolean isResetPassword;
}
