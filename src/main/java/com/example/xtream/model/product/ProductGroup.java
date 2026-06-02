package com.example.xtream.model.product;

import com.example.xtream.config.audit.Auditable;
import com.example.xtream.model.common.Status;
import com.example.xtream.model.fund.Funds;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_group")
public class ProductGroup extends Auditable {
    @Column(name = "code")
    @Id
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "fund_code")
    private Funds fund;
}
