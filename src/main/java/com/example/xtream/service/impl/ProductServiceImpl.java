package com.example.xtream.service.impl;

import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.repository.ProductRepository;
import com.example.xtream.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    @Transactional
    public ResponseDTO getProductTree() {
        return null;
    }
}
