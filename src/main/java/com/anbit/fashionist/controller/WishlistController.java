package com.anbit.fashionist.controller;

import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.WishlistServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Tag(name = "7. Wishlist Controller")
@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor
public class WishlistController {

    @Autowired
    WishlistServiceImpl wishlistService;

    @Operation(summary = "Add product to your wishlist")
    @PostMapping("/wishlist/add/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> productListCreate(@Valid @PathVariable("id") Long id) throws ResourceNotFoundException, ResourceAlreadyExistException {
        return wishlistService.addWishlist(id);
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
