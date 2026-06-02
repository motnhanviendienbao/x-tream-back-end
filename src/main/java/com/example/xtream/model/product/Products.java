package com.example.xtream.model.product;

import com.example.xtream.config.audit.Auditable;
import com.example.xtream.model.common.Status;
import com.example.xtream.model.product.ProductGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Products extends Auditable {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type ;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "product_group_code")
    private ProductGroup productGroup;
}
