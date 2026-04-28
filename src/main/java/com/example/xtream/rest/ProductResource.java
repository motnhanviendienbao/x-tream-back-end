package com.example.xtream.rest;

import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource product
 */
@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    /**
     * Product tree
     *
     * @return response contains array of product tree
     */
    @GetMapping("/tree")
    public ResponseEntity<ResponseDTO> productTree() {
        ResponseDTO response = productService.getProductTree();
        return ResponseEntity.ok(response);
    }
}
