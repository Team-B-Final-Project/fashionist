package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.Cart;
import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.AddCartRequestDTO;
import com.anbit.fashionist.domain.dto.EditCartTotalItemRequestDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.CartRepository;
import com.anbit.fashionist.repository.ProductRepository;
import com.anbit.fashionist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        this.cartRepository.save(cart);
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
        this.cartRepository.save(cart);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Cart item unit updated!", null);
    }

    @Override
    public ResponseEntity<?> deleteCart(Long id) throws  ResourceNotFoundException {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!userDetails.getId().equals(cart.getUser().getId())){
            throw new ResourceNotFoundException("You are not allowed to delete this cart!");
        }
        this.cartRepository.delete(cart);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK , "Product deleted from cart", null);
    }
}
