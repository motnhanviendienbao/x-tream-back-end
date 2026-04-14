package com.example.xtream.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "investors")
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",length = 255, nullable = false)
    private String name;

    @Column(name = "status",nullable = false)
    private Boolean status;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "post_code", nullable = false )
    private String postCode;
}
