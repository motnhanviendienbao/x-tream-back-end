package com.example.xtream.model.investment;

import com.example.xtream.config.audit.Auditable;
import com.example.xtream.model.product.Products;
import com.example.xtream.model.common.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "investments")
public class Investments extends Auditable {

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "product_code")
    private Products product;
}
