package com.anbit.fashionist.service;


import com.anbit.fashionist.domain.dto.UploadProductRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
public interface ProductService {
    ResponseEntity<?> searchProducts(String keyword, String category, String locations, String sortBy, String order, Float minPrice, Float maxPrice, int page) throws ResourceNotFoundException;

    ResponseEntity<?> getProduct(Long id) throws  ResourceNotFoundException;
    
    ResponseEntity<?> createProduct(UploadProductRequestDTO productRequestDTO, List<MultipartFile> files) throws ResourceNotFoundException, ResourceAlreadyExistException, IOException;
}
