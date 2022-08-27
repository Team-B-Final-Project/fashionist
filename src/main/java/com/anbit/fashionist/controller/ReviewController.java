package com.anbit.fashionist.controller;

import com.anbit.fashionist.domain.dto.ReviewRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.ReviewServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "9. Review Controller")
@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearer-key")
public class ReviewController {

    @Autowired
    ReviewServiceImpl reviewService;

    @Operation(summary = "Create review for product")
    @PostMapping("/review/create")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewRequestDTO requestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException {
        return this.reviewService.createReview(requestDTO);
    }

    @Operation(summary = "Get review for product")
    @GetMapping("/review/get/{productId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> getReviews(@Valid @PathVariable Long productId) throws ResourceNotFoundException {
        return this.reviewService.getReviews(productId);
    }
}
