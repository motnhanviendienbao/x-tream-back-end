package com.example.xtream.model;

import com.example.xtream.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "funds")
public class Funds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String fundName;

    @Column(name = "status", length = 1)
    @Enumerated(EnumType.STRING )
    private Status status = Status.A;

    @Column(name = "short_code", length = 10)
    private String shortCode;

    @JsonIgnore
    @OneToMany(mappedBy = "fund", fetch = FetchType.LAZY)
    private List<ProductGroup> productGroups = new ArrayList<>();
}
