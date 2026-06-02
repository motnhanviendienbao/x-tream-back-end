package com.example.xtream.model.investor;

import com.example.xtream.config.audit.Auditable;
import com.example.xtream.model.investment.Investments;
import com.example.xtream.model.investor.InvestorAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "investor_account_investment")
public class InvestorAccountInvestment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "strategy")
    private int strategyPercentage;

    @ManyToOne
    @JoinColumn(name = "investment_code")
    private Investments investment;

    @ManyToOne
    @JoinColumn(name = "investor_account_id")
    private InvestorAccount investorAccount;

}
