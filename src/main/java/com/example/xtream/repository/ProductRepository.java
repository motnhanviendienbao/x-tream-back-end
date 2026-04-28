package com.example.xtream.repository;

import com.example.xtream.dto.response.ProductTreeDTO;
import com.example.xtream.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {

    @Query("""
        SELECT DISTINCT
            products.id as productId,
            funds.fundName as fundName,
            productGroup.productGroupShortCode as productGroupName,
            products.productName as productName
        FROM Funds funds
        JOIN ProductGroup productGroup
            ON funds.id = productGroup.fund.id
        JOIN Products products
            ON products.productGroup.id = productGroup.id
    """)
    List<ProductTreeDTO> findProductTree();
}
