package com.example.xtream.rest;

import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    @GetMapping("/tree")
    public ResponseEntity<ResponseDTO> productTree() {
        ResponseDTO response = productService.getProductTree();
        return ResponseEntity.ok(response);
    }
}
