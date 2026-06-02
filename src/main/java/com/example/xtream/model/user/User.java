package com.example.xtream.model.user;

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
    private Integer id;
    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;
    @Column(name = "user_name", nullable = false,unique = true)
    private String userName;
    @Column(name = "reset_password")
    private Boolean isResetPassword;
}
