package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.dto.ReviewRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface ReviewService {

    ResponseEntity<?> getReviews(Long transactionId) throws ResourceNotFoundException;
    ResponseEntity<?> createReview(ReviewRequestDTO reviewRequestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException;
}
