package com.anbit.fashionist.controller;

import com.anbit.fashionist.domain.dto.WishlistRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.WishlistServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Tag(name = "Wishlist Controller")
@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearer-key")

public class WishlistController {

    @Autowired
    WishlistServiceImpl wishlistService;

    @Operation(summary = "Add product to your wishlist")
    @PostMapping("/wishlist/add")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> productListCreate(@RequestBody WishlistRequestDTO request) throws ResourceNotFoundException, ResourceAlreadyExistException {
        return wishlistService.addWishlist(request);
    }

    @Operation(summary = "Get all your wishlist")
    @GetMapping("/wishlist/all")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> findAllWishlist() throws ResourceNotFoundException {
        return wishlistService.getAllWishlist();
    }

    @Operation(summary = "Delete your wishlist")
    @DeleteMapping("/wishlist/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> deleteProductList(@Valid @PathVariable Long id) throws ResourceNotFoundException {
        return wishlistService.deleteWishlist(id);
    }
}
