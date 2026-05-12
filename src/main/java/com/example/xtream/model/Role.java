package com.example.xtream.model;

import com.example.xtream.config.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Role")
@Getter
@Setter
public class Role extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", nullable = false, length = 255, unique = true)
    private String code;
    @Column(name = "description", nullable = true, length = 255,unique = false)
    private String description;
}
