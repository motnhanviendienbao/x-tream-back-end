package com.example.xtream.model.investor;

import com.example.xtream.config.audit.Auditable;
import com.example.xtream.model.product.Products;
import com.example.xtream.model.common.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "investor_accounts")
@NoArgsConstructor
@Getter
@Setter
public class InvestorAccount extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "investor_id")
    private Investors investors;

    @ManyToOne
    @JoinColumn(name = "product_code")
    private Products products;

    @Column(name = "start_date")
    private LocalDate startDate;
}
