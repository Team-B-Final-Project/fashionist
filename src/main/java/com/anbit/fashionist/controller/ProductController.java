package com.anbit.fashionist.controller;

import com.anbit.fashionist.domain.dto.UploadProductRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.ProductServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

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
    public ResponseEntity<?> search(@Valid @RequestParam(value = "keyword", required = true) @Pattern(regexp = "^[a-zA-Z0-9-/+()., ?]+$", message = "format is not valid") String keyword,
                                    @Valid @RequestParam(value = "category",required = false) String category,
                                    @Valid @RequestParam(value = "locations", required = false) String locations,
                                    @Valid @RequestParam(value = "sortBy", required = false) String sortBy,
                                    @Valid @RequestParam(value = "order", required = false) String order,
                                    @Valid @RequestParam(value = "minPrice", required = false) Float minPrice,
                                    @Valid @RequestParam(value = "maxPrice", required = false) Float maxPrice,
                                    @Valid @RequestParam(value = "page", required = true, defaultValue = "1") int page) throws ResourceNotFoundException {
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
    public ResponseEntity<?> create(@Valid @RequestBody UploadProductRequestDTO requestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException {
        return productService.createProduct(requestDTO);
    }
}
