package com.anbit.fashionist.service;


import com.anbit.fashionist.domain.dao.Review;
import com.anbit.fashionist.domain.dto.ReviewRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.ProductRepository;
import com.anbit.fashionist.repository.ReviewRepository;
import com.anbit.fashionist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public ResponseEntity<?> createReview(Long productId, ReviewRequestDTO reviewRequestDTO) throws ResourceNotFoundException {
        try{

            Review review = Review.builder()
                    .product(productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found")))
                    .user(userRepository.findById(reviewRequestDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found")))
                    .review(reviewRequestDTO.getReview())
                    .build();
            reviewRepository.save(review);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
