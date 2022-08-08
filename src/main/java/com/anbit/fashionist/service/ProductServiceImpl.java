package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.dao.Product;

import com.anbit.fashionist.domain.dto.SearchProductResponseDTO;
import com.anbit.fashionist.domain.dto.ProductRequestDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    Pageable pageable;

    @Override
    public ResponseEntity<?> searchProducts(String keyword, String category, String locations, String sortBy, String order, Float minPrice, Float maxPrice, int page) throws ResourceNotFoundException {
        try {
            if (sortBy != null) {
                pageable = PageRequest.of(page - 1, 30, Sort.by(sortBy).ascending());
            }
            Page<Product> pageProduct = productRepository.findByName(keyword, pageable);
            if (pageProduct.getContent().isEmpty()) {
                throw new ResourceNotFoundException("Product not found");
            }
            List<SearchProductResponseDTO> searchProductResponseDTOS = new ArrayList<>();
            for (Product product : pageProduct.getContent()) {
                SearchProductResponseDTO responseDTO = SearchProductResponseDTO.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .city(product.getStore().getAddress().getCity())
                        .build();
                searchProductResponseDTOS.add(responseDTO);
            }
            Map<String, Object> pagination = new HashMap<>();
            pagination.put("currentPage", page);
            pagination.put("nextPage", null);
            pagination.put("previousPage", null);
            pagination.put("totalPages", pageProduct.getTotalPages());
            pagination.put("perPage", 30);
            pagination.put("totalElements", pageProduct.getTotalElements());
            return ResponseHandler.generateSuccessResponseWithPagination(HttpStatus.OK, ZonedDateTime.now(), "Successfully reterieve data!", searchProductResponseDTOS, pagination);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ZonedDateTime.now(), e.getMessage(), 500);
        }
    }

    @Override
    public ResponseEntity<?> uploadProduct(ProductRequestDTO productRequestDTO) throws ResourceAlreadyExistException {
        try {
            Product product = productRequestDTO.convertToEntity();
            if(Boolean.TRUE.equals(productRepository.existsById(product.getId()))) {
                throw new ResourceAlreadyExistException("Product already exist!");
            }
            productRepository.save(product);
            return ResponseHandler.generateSuccessResponse(HttpStatus.CREATED, ZonedDateTime.now(), "Product uploaded successfully!", product);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ZonedDateTime.now(), e.getMessage(), 500);
        }

    }
}
