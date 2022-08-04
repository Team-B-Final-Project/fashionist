package com.anbit.fashionist.controller;


import com.anbit.fashionist.domain.dto.ProductRequestDTO;
import com.anbit.fashionist.domain.dto.ProductResponseDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.service.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private final ProductServiceImpl productService;

    @PostMapping("/post-product")
    public ResponseEntity<?> uploadProduct(ProductRequestDTO productRequestDTO) throws ResourceAlreadyExistException {
        return productService.uploadProduct(productRequestDTO);
    }
}
