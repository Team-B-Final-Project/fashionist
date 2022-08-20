package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dao.Wishlist;
import com.anbit.fashionist.domain.dto.WishlistRequestDTO;
import com.anbit.fashionist.domain.dto.WishlistResponseDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.UserRepository;
import com.anbit.fashionist.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> getAllWishlist() throws ResourceNotFoundException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getReferenceById(userDetails.getId());
        List<Wishlist> wishlists = wishlistRepository.findByUser(user);
        if (wishlists.isEmpty()) {
            throw new ResourceNotFoundException("You have no wishlist yet!");
        }
        List<WishlistResponseDTO> responseDTOS = new ArrayList<>();
        wishlists.forEach(wishlist -> {
            WishlistResponseDTO responseDTO = WishlistResponseDTO.builder()
                    .id(wishlist.getId())
                    .product(wishlist.getProduct())
                    .build();
            responseDTOS.add(responseDTO);
        });
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully retrieved data!", responseDTOS);
    }

    @Override
    public ResponseEntity<?> addWishlist(WishlistRequestDTO wishlistRequestDTO) throws ResourceNotFoundException, ResourceAlreadyExistException {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteWishlist(Long Id) throws ResourceNotFoundException {
        return null;
    }
}

