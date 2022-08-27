package com.anbit.fashionist.service;


import com.anbit.fashionist.controller.AddressController;
import com.anbit.fashionist.domain.common.UserDetailsImpl;

import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.Review;
import com.anbit.fashionist.domain.dao.User;

import com.anbit.fashionist.domain.dto.ReviewRequestDTO;
import com.anbit.fashionist.domain.dto.ReviewResponseDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.ProductRepository;
import com.anbit.fashionist.repository.ReviewRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    AuthServiceImpl authService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;

   private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

   private static final String loggerLine = "---------------------------------------";

    @Override
    public ResponseEntity<?> createReview(ReviewRequestDTO reviewRequestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException{
        User user = authService.getCurrentUser();

        Product product = productRepository.findById(reviewRequestDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        if(Boolean.TRUE.equals(reviewRepository.existsByProductAndUser(product, user))) {
            throw new ResourceAlreadyExistException("You have already reviewed this product!");
        }

        Review reviewSave = Review.builder()
                .user(user)
                .product(productRepository.findById(reviewRequestDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found!")))
                .rating(reviewRequestDTO.getRating())
                .comment(reviewRequestDTO.getComment())
                .build();
        this.reviewRepository.save(reviewSave);
       logger.info(loggerLine);
       logger.info("Current User Review " + reviewSave.getId() + " successfully created!");
       logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Review created successfully!", null);
    }

    @Override
    public ResponseEntity<?> getReviews(Long productId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        List<Review> reviews = reviewRepository.findByProduct(product);
        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("You have no review yet!");
        }

        List<ReviewResponseDTO> reviewDTO = new ArrayList<>();
        reviews.forEach(review -> {
            ReviewResponseDTO responseDTO = ReviewResponseDTO.builder()
                    .id(review.getId())
                    .rating(review.getRating())
                    .comment(review.getComment())
                    .build();
            reviewDTO.add(responseDTO);
        });
       logger.info(loggerLine);
       logger.info("Current User Review " + reviewDTO);
       logger.info(loggerLine);

        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully retrieved data!", reviewDTO);
    }

    


}
