package com.anbit.fashionist.service;


import com.anbit.fashionist.domain.dto.ProductRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
public interface ProductService {
    ResponseEntity<?> searchProducts(String keyword, String category, String locations, String sortBy, String order, Float minPrice, Float maxPrice, int page) throws ResourceNotFoundException;
    
    ResponseEntity<?> uploadProduct(ProductRequestDTO productRequestDTO) throws ResourceNotFoundException, ResourceAlreadyExistException;
}
