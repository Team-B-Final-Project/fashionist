package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.dao.Cart;
import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.AddCartRequestDTO;
import com.anbit.fashionist.domain.dto.CartResponseDTO;
import com.anbit.fashionist.domain.dto.EditCartTotalItemRequestDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.CartRepository;
import com.anbit.fashionist.repository.ProductRepository;
import com.anbit.fashionist.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    AuthServiceImpl authService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger("ResponseHandler");
    
    private static final String loggerLine = "---------------------------------------";

    @Override
    public ResponseEntity<?> addCart(AddCartRequestDTO requestDTO) throws ResourceNotFoundException, ResourceAlreadyExistException {
        User user = authService.getCurrentUser();
        Product product = productRepository.findById(requestDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        if (Boolean.TRUE.equals(cartRepository.existsByUserAndProduct(user, product))) {
            throw new ResourceAlreadyExistException("This product has been in the cart!");
        }
        Cart cart = Cart.builder()
                .user(user)
                .product(product)
                .itemUnit(requestDTO.getItemUnit())
                .build();
        Float totalPrice = product.getPrice() * requestDTO.getItemUnit();
        cart.setTotalPrice(totalPrice);
        Cart newCart = this.cartRepository.save(cart);
        logger.info(loggerLine);
        logger.info(newCart.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Product added successfully to the cart!" , null);
    }

    @Override
    public ResponseEntity<?> editCartTotalItem(EditCartTotalItemRequestDTO requestDTO) throws ResourceNotFoundException {
        Cart cart =  cartRepository.findById(requestDTO.getCartId()).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        User user = authService.getCurrentUser();
        if(!user.getId().equals(cart.getUser().getId())){
            throw new ResourceNotFoundException("You are not allowed to edit this cart!");
        }
        Float totalPrice = cart.getProduct().getPrice() * requestDTO.getItemUnit();
        cart.setTotalPrice(totalPrice);
        cart.setItemUnit(requestDTO.getItemUnit());
        Cart updatedCart = this.cartRepository.save(cart);
        logger.info(loggerLine);
        logger.info(updatedCart.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Cart item unit updated!", null);
    }

    @Override
    public ResponseEntity<?> deleteCart(Long id) throws  ResourceNotFoundException {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        User user = authService.getCurrentUser();
        if(!user.getId().equals(cart.getUser().getId())){
            throw new ResourceNotFoundException("You are not allowed to delete this cart!");
        }
        this.cartRepository.delete(cart);
        logger.info(loggerLine);
        logger.info(cart.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK , "Product deleted from cart", null);
    }

    @Override
    public ResponseEntity<?> getCart() throws ResourceNotFoundException {
        User user = authService.getCurrentUser();
        List<Cart> carts = cartRepository.findByUser(user);
        
        if(carts.isEmpty()) {
            throw new ResourceNotFoundException("You have no cart yet!");
        }

        List<CartResponseDTO> cartProducts = new ArrayList<>();
        carts.forEach(cart -> {
            CartResponseDTO responseDTO = CartResponseDTO.builder()
                    .id(cart.getId())
                    .productId(cart.getProduct().getId())
                    .userId(cart.getUser().getId())
                    .itemUnit(cart.getItemUnit())
                    .totalPrice(cart.getTotalPrice())
                    .build();
            cartProducts.add(responseDTO);
        });

        logger.info(loggerLine);
        logger.info("Successfully retrieved data!");
        logger.info(cartProducts.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully retrieved data!", cartProducts);
    }
}
