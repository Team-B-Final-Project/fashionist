package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.dto.ProductRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import com.anbit.fashionist.handler.ResponseHandler;
public interface ProductService {
    public ResponseEntity<?> uploadProduct(ProductRequestDTO productRequestDTO) throws ResourceNotFoundException, ResourceAlreadyExistException;
}