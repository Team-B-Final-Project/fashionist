package com.anbit.fashionist.service;


import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.Review;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.ReviewRequestDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.ProductRepository;
import com.anbit.fashionist.repository.ReviewRepository;
import com.anbit.fashionist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public ResponseEntity<?> createReview(ReviewRequestDTO reviewRequestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException{
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findById(userDetails.getId());
        Review review = reviewRepository.findById(reviewRequestDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Review not found!"));

        if(Boolean.TRUE.equals(reviewRepository.existsByProductIdAndUserId(user.get().getId(), review.getProduct().getId()))) {
            throw new ResourceAlreadyExistException("You have already reviewed this product!");
        }

        Review reviewSave = Review.builder()
                .user(user.get())
                .product(productRepository.findById(reviewRequestDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found!")))
                .rating(reviewRequestDTO.getRating())
                .comment(reviewRequestDTO.getComment())
                .build();
        this.reviewRepository.save(reviewSave);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Review created successfully!", null);
    }



}
