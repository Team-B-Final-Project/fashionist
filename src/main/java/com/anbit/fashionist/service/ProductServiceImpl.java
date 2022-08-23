package com.anbit.fashionist.service;

import com.anbit.fashionist.constant.ECategory;
import com.anbit.fashionist.domain.dao.Category;
import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.Store;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.SearchProductResponseDTO;
import com.anbit.fashionist.domain.dto.UploadProductRequestDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.CategoryRepository;
import com.anbit.fashionist.repository.ProductPictureRepository;
import com.anbit.fashionist.repository.ProductRepository;
import com.anbit.fashionist.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.anbit.fashionist.helper.FileNameHelper;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    AuthServiceImpl authService;
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileNameHelper fileNameHelper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductPictureRepository productPictureRepository;

    @Override
    public ResponseEntity<?> searchProducts(
            String keyword, 
            String category, 
            String locations, 
            String sortBy, 
            String order, 
            Float minPrice, 
            Float maxPrice, 
            int page) throws ResourceNotFoundException {
        Pageable pageable;
        if (sortBy != null){
            pageable = PageRequest.of(page -1,30, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page -1,30, Sort.by("name").ascending());
        }
        Page<Product> pageProduct = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        if (pageProduct.getContent().isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        List<SearchProductResponseDTO> searchProductResponseDTOS = new ArrayList<>();
        for (Product product : pageProduct.getContent()) {
            SearchProductResponseDTO responseDTO = SearchProductResponseDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .city(product.getStore().getAddress().getVillage().getDistrict().getRegency().getName())
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
        return ResponseHandler.generateSuccessResponseWithPagination(HttpStatus.OK, "Successfully reterieve data!", searchProductResponseDTOS, pagination);
    }

    @Override
    public ResponseEntity<?> createProduct(UploadProductRequestDTO requestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException {
        User user = authService.getCurrentUser();
        Store store = user.getStore();
        if (store.equals(null)) {
            throw new ResourceNotFoundException("You don't have any store yet!");
        }
        Category category = categoryRepository.findByName(ECategory.valueOf(requestDTO.getCategoryName().toUpperCase())).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        Product product = Product.builder()
            .name(requestDTO.getName())
            .price(requestDTO.getPrice())
            .stock(requestDTO.getStock())
            .store(store)
            .description(requestDTO.getDescription())
            .category(category)
            .build();
        productRepository.save(product);
        return ResponseHandler.generateSuccessResponse(HttpStatus.CREATED, "Product created successfully!", null);
    }
}