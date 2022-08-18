package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.dto.ReviewRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<?> createReview(Long productId, ReviewRequestDTO reviewRequestDTO) throws ResourceNotFoundException;
}
