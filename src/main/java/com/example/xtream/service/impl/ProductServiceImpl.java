package com.example.xtream.service.impl;

import com.example.xtream.dto.response.ProductTreeDTO;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.repository.ProductRepository;
import com.example.xtream.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Product resource service
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    /**
     * Get product tree
     *
     * @return ResponseDTO contains List<ProductTreeDTO>
     */
    @Override
    @Transactional
    public ResponseDTO getProductTree() {
        List<ProductTreeDTO> response = productRepository.findProductTree();
        return ResponseDTO
                .builder()
                .response(response)
                .build();
    }
}
