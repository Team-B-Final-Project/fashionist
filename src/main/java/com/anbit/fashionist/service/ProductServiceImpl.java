package com.anbit.fashionist.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl {

    @Autowired
    ProductService productService;

    @Override
    public ResponseEntity<?> uploadProduct(ProductRequestDTO productRequestDTO) throws ResourceAlreadyFoundException {
        try {
            productService.uploadProduct(productRequestDTO);
        } catch (ResourceAlreadyFoundException e) {
            throw new ResourceAlreadyFoundException(e.getMessage());
        }
    }
}
