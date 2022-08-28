package com.anbit.fashionist.service;


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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        User user = authService.getCurrentUser();
        List<Wishlist> wishlists = wishlistRepository.findByUser(user);
        
        if(wishlists.isEmpty()) {
            throw new ResourceNotFoundException("You have no wishlist yet!");
        }

        List<WishlistResponseDTO> wishlistProducts = new ArrayList<>();
        wishlists.forEach(wishlist -> {
            List<String> productPictureUrl = new ArrayList<>();
            wishlist.getProduct().getPictures().forEach(picture -> {
                productPictureUrl.add(picture.getUrl());
            });
            WishlistResponseDTO responseDTO = WishlistResponseDTO.builder()
                    .id(wishlist.getId())
                    .productId(wishlist.getProduct().getId())
                    .productPictureUrl(productPictureUrl)
                    .name(wishlist.getProduct().getName())
                    .price(wishlist.getProduct().getPrice())
                    .city(wishlist.getProduct().getStore().getAddress().getVillage().getDistrict().getRegency().getName())
                    .build();
                    wishlistProducts.add(responseDTO);
        });


        logger.info(loggerLine);
        logger.info("Successfully retrieved data!");
        logger.info(wishlistProducts.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully retrieved data!", wishlistProducts);
    }

    @Override
    public ResponseEntity<?> addWishlist(WishlistRequestDTO requestDTO) throws ResourceNotFoundException, ResourceAlreadyExistException {
        User user = authService.getCurrentUser();
        Product product = productRepository.findById(requestDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        if (Boolean.TRUE.equals(wishlistRepository.existsByUserAndProduct(user, product))) {
            throw new ResourceAlreadyExistException("This product has been in the wishlist!");
        }
        Wishlist wishlistSave = Wishlist.builder()
                .user(user)
                .product(product)
                .build();
        this.wishlistRepository.save(wishlistSave);
        logger.info(loggerLine);
        logger.info(wishlistSave.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Product added successfully to the wishlist!" , null);
    }

    @Override
    public ResponseEntity<?> deleteWishlist(Long id) throws ResourceNotFoundException {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Wishlist not found!"));
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

