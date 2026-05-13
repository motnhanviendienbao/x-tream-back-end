package com.example.xtream.model;

import com.example.xtream.model.common.Status;
import com.example.xtream.model.common.UnitPriceSelectionMethod;
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
@Table(name = "investments")
public class Investments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", length = 1)
    @Enumerated(EnumType.STRING)
    private Status status = Status.A;

    @Column(name = "short_code")
    private String investmentShortCode;

    @Column(name = "selection_method")
    @Enumerated(EnumType.STRING)
    private UnitPriceSelectionMethod unitPriceSelectionMethod = UnitPriceSelectionMethod.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products product;

    @OneToMany(mappedBy = "investment", fetch = FetchType.LAZY)
    private List<UnitPrice> unitPrices = new ArrayList<>();

    @OneToMany(mappedBy = "investment", fetch = FetchType.LAZY)
    private List<InvestorAccountInvestment> investorAccountInvestments = new ArrayList<>();

}
