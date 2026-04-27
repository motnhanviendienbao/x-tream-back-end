package com.example.xtream.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "investor_account_investment")
public class InvestorAccountInvestment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "strategy")
    private BigDecimal strategyPercentage;

    @Column(name = "units")
    private BigDecimal units;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investment_id")
    private Investments investment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investor_account_id")
    private InvestorAccount investorAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_price_id")
    private UnitPrice unitPrice;

}
