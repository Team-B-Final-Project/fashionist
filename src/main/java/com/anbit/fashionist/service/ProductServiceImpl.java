package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dto.ProductRequestDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ResponseEntity<?> uploadProduct(ProductRequestDTO productRequestDTO) throws ResourceAlreadyExistException {
        try {
            Product product = productRequestDTO.convertToEntity();
            if(Boolean.TRUE.equals(productRepository.existsById(product.getId()))){
                throw new ResourceAlreadyExistException("Product already exist!");
            }
            productRepository.save(product);
            return ResponseHandler.generateSuccessResponse(HttpStatus.CREATED, ZonedDateTime.now(), "Product uploaded successfully!", product);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ZonedDateTime.now(), e.getMessage(), 500);
        }

    }
}
