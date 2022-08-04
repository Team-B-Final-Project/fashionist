package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.dto.ProductRequestDTO;
import org.springframework.http.ResponseEntity;

public class ProductService {
    ResponseEntity<?> uploadProduct(ProductRequestDTO productRequestDTO) throws ResourceAlreadyFoundException;
}
