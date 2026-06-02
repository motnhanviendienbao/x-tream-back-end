package com.example.xtream.repository;

import com.example.xtream.dto.response.ProductTreeDTO;
import com.example.xtream.model.product.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,String> {

    @Query("""
        SELECT DISTINCT
            products.code as productCode,
            funds.name as fundName,
            productGroup.name as productGroupName,
            products.name as productName
        FROM Funds funds
        JOIN ProductGroup productGroup ON funds.code = productGroup.fund.code
        JOIN Products products ON products.productGroup.code = productGroup.code
    """)
    List<ProductTreeDTO> findProductTree();
}
