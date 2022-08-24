package com.anbit.fashionist.service;

import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface WishlistService {
    ResponseEntity<?> getAllWishlist() throws ResourceNotFoundException;
    ResponseEntity<?> addWishlist(Long id) throws ResourceNotFoundException, ResourceAlreadyExistException;
    ResponseEntity<?> deleteWishlist(Long Id) throws ResourceNotFoundException;
}
