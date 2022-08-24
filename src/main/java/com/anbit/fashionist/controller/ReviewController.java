package com.anbit.fashionist.controller;

import com.anbit.fashionist.domain.dto.ReviewRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.ReviewServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private ReviewServiceImpl reviewService;

    /**
     * @param requestDTO
     * @throws ResourceAlreadyExistException
     * @throws ResourceNotFoundException
     */
    @PostMapping("/review/create")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequestDTO requestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException {
        return reviewService.createReview(requestDTO);
    }

    /***
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/review/get_all")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> getReviews() throws ResourceNotFoundException {
        return reviewService.getReviews();
    }
}
