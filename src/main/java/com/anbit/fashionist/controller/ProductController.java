package com.anbit.fashionist.controller;


import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.ProductService;
import com.anbit.fashionist.service.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
     private final ProductServiceImpl productService;

    @PostMapping("/products/search")
    public ResponseEntity<?> search(@RequestParam(value = "keyword", required = true) String keyword,
                                    @RequestParam(value = "category",required = false) String category,
                                    @RequestParam(value = "locations", required = false) String locations,
                                    @RequestParam(value = "sortBy", required = false) String sortBy,
                                    @RequestParam(value = "order", required = false) String order,
                                    @RequestParam(value = "minPrice", required = false) Float minPrice,
                                    @RequestParam(value = "maxPrice", required = false) Float maxPrice,
                                    @RequestParam(value = "page", required = true) int page) throws ResourceNotFoundException {
        return productService.searchProducts(keyword, category, locations, sortBy, order, minPrice, maxPrice, page);
    }
}
