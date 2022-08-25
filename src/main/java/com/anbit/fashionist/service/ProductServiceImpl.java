package com.anbit.fashionist.service;

import com.anbit.fashionist.constant.ECategory;
import com.anbit.fashionist.domain.dao.Category;
import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.ProductPicture;
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
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.anbit.fashionist.helper.FileNameHelper;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    Cloudinary cloudinaryConfig;

    @Value("${com.anbit.fashionist.domain}")
    String domain;

    @Value("${com.anbit.fashionist.upload-dir}")
    String uploadDirectory;

    private static final Logger logger = LoggerFactory.getLogger("ResponseHandler");
    
    private static final String loggerLine = "---------------------------------------";

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
        Page<Product> pageProduct;
        if (sortBy != null){
            pageable = PageRequest.of(page -1,30, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page -1,30, Sort.by("name").ascending());
        }
        if (category != null) {
            Category categoryByName = categoryRepository.findByName(ECategory.valueOf(category.toUpperCase())).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            pageProduct = productRepository.findByCategoryAndNameContainingIgnoreCase(categoryByName, keyword, pageable);
        }else {
            pageProduct = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }
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
        pagination.put("nextPage", pageProduct.hasNext());
        pagination.put("previousPage", pageProduct.hasPrevious());
        pagination.put("totalPages", pageProduct.getTotalPages());
        pagination.put("perPage", 30);
        pagination.put("totalElements", pageProduct.getTotalElements());
        logger.info(loggerLine);
        logger.info("Successfully reterieve data!");
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponseWithPagination(HttpStatus.OK, "Successfully reterieve data!", searchProductResponseDTOS, pagination);
    }

    @Override
    public ResponseEntity<?> createProduct(UploadProductRequestDTO requestDTO, List<MultipartFile> files) throws ResourceAlreadyExistException, ResourceNotFoundException, IOException {
        User user = authService.getCurrentUser();
        Store store = user.getStore();
        if (store.equals(null)) {
            throw new ResourceNotFoundException("You don't have any store yet!");
        }
        ECategory eCategory;
        try {
            eCategory = ECategory.valueOf(requestDTO.getCategoryName().toUpperCase());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Category name is not valid");
        }
        Category category = categoryRepository.findByName(eCategory).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        Product product = Product.builder()
            .name(requestDTO.getName())
            .price(requestDTO.getPrice())
            .stock(requestDTO.getStock())
            .store(store)
            .description(requestDTO.getDescription())
            .category(category)
            .build();
        Product newProduct = productRepository.save(product);
        List<ProductPicture> productPictureList = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            String fileName = StringUtils.cleanPath(files.get(i).getOriginalFilename());
            Map<?,?> uploadResult = cloudinaryConfig.uploader().upload(files.get(i).getBytes(), ObjectUtils.asMap(
                "public_id", newProduct.getId() + Integer.toString(i) + "-" + fileName.replace("[.].*", ""), 
                "folder", "/image/product_picture/"));
            ProductPicture productPicture = ProductPicture.builder()
                .publicId(uploadResult.get("public_id").toString())
                .name(fileName)
                .product(newProduct)
                .level(i+1)
                .type(uploadResult.get("resource_type").toString() + "/" + uploadResult.get("format").toString())
                .size(files.get(i).getSize())
                .url(uploadResult.get("secure_url").toString())
                .build();
            productPictureList.add(productPicture);
        }
        productPictureRepository.saveAll(productPictureList);
        logger.info(loggerLine);
        logger.info("Product created successfully!");
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.CREATED, "Product created successfully!", null);
    }
}