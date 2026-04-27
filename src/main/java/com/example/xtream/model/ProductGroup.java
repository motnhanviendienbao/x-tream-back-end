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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_group")
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", length = 1)
    @Enumerated(EnumType.STRING)
    private Status productGroupStatus = Status.A;

    @Column(name = "short_code")
    private String productGroupShortCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_id", insertable = false, updatable = false)
    private Funds fund;

    @OneToMany(mappedBy = "productGroup", fetch = FetchType.LAZY)
    private List<Products> products = new ArrayList<>();
}
