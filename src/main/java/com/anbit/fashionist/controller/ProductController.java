package com.anbit.fashionist.controller;

import com.anbit.fashionist.domain.dto.UploadProductRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.ProductServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "2. Product Controller")
@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearer-key")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    /***
     * Search product for customer with keyword and do some filter and sorting
     * @param keyword
     * @param category
     * @param locations
     * @param sortBy
     * @param order
     * @param minPrice
     * @param maxPrice
     * @param page
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Search product for customer with keyword and do some filter and sorting")
    @GetMapping("/products/search")
    public ResponseEntity<?> search(@RequestParam(value = "keyword", required = true) String keyword,
                                    @RequestParam(value = "category",required = false) String category,
                                    @RequestParam(value = "locations", required = false) String locations,
                                    @RequestParam(value = "sortBy", required = false) String sortBy,
                                    @RequestParam(value = "order", required = false) String order,
                                    @RequestParam(value = "minPrice", required = false) Float minPrice,
                                    @RequestParam(value = "maxPrice", required = false) Float maxPrice,
                                    @RequestParam(value = "page", required = true, defaultValue = "1") int page) throws ResourceNotFoundException {
        return productService.searchProducts(keyword, category, locations, sortBy, order, minPrice, maxPrice, page);
    }

    /***
     * Upload a product for seller
     * @param productRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     * @throws ResourceNotFoundException
     * @throws IOException
     */
    @Operation(summary = "Upload a product for seller")
    @PostMapping("/product/upload")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<?> create(@RequestBody UploadProductRequestDTO requestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException {
        return productService.createProduct(requestDTO);
    }
}
