package com.example.xtream.model;

import com.example.xtream.model.enums.ProductType;
import com.example.xtream.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String productName;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ProductType productType = ProductType.LOW;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.A;

    @Column(name = "short_code")
    private String productShortCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_group_id")
    private ProductGroup productGroup;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Investments> investments = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<InvestorAccount> investorAccounts = new ArrayList<>();
}
