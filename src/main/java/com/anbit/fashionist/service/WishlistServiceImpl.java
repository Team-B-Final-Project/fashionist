package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dao.Wishlist;
import com.anbit.fashionist.domain.dto.WishlistRequestDTO;
import com.anbit.fashionist.domain.dto.WishlistResponseDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.ProductRepository;
import com.anbit.fashionist.repository.UserRepository;
import com.anbit.fashionist.repository.WishlistRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    AuthServiceImpl authService;
    
    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger("ResponseHandler");
    
    private static final String loggerLine = "---------------------------------------";

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
        logger.info(loggerLine);
        logger.info("Successfully retrieved data!");
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully retrieved data!", responseDTOS);
    }

    @Override
    public ResponseEntity<?> addWishlist(WishlistRequestDTO wishlistRequestDTO) throws ResourceNotFoundException, ResourceAlreadyExistException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        Product product = productRepository.findById(wishlistRequestDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        if (Boolean.TRUE.equals(wishlistRepository.existsByUserAndProduct(user.get(), product))) {
            throw new ResourceAlreadyExistException("This product has been in the wishlist!");
        }
        Wishlist wishlist = Wishlist.builder()
                .user(user.get())
                .product(product)
                .build();
        this.wishlistRepository.save(wishlist);
        logger.info(loggerLine);
        logger.info(wishlist.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Product added successfully to the wishlist!" , null);
    }

    @Override
    public ResponseEntity<?> deleteWishlist(Long id) throws ResourceNotFoundException {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        User user = authService.getCurrentUser();
        if(!user.getId().equals(wishlist.getUser().getId())){
            throw new ResourceNotFoundException("You are not allowed to delete this wishlist!");
        }
        this.wishlistRepository.delete(wishlist);
        logger.info(loggerLine);
        logger.info(wishlist.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK , "Product deleted from wishlist", null);
    }
}

