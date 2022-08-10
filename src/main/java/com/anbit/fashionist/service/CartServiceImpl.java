package com.anbit.fashionist.service;

import com.anbit.fashionist.constant.EErrorCode;
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

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> addCart(AddCartRequestDTO requestDTO) throws ResourceNotFoundException, ResourceAlreadyExistException {
        try{
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
            Product product = productRepository.findById(requestDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
            if (Boolean.TRUE.equals(cartRepository.existsByUser(user.get())) && Boolean.TRUE.equals(cartRepository.existsByProduct(product))) {
                throw new ResourceAlreadyExistException("This product has been in the cart!");
            }
            Cart cart = Cart.builder()
                    .user(user.get())
                    .product(product)
                    .itemUnit(requestDTO.getItemUnit())
                    .build();
            Float totalPrice = product.getPrice() * requestDTO.getItemUnit();
            cart.setTotalPrice(totalPrice);
            this.cartRepository.save(cart);
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(),"Product added successfully to the cart!" , null);
        }catch (ResourceNotFoundException e){
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        } catch (ResourceAlreadyExistException e){
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_ACCEPTABLE, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> editCartTotalItem(EditCartTotalItemRequestDTO requestDTO) throws ResourceNotFoundException {
        try {
            Cart cart = cartRepository.findById(requestDTO.getCartId()).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!userDetails.getId().equals(cart.getUser().getId())){
                throw new ResourceNotFoundException("You are not allowed to edit this cart!");
            }
            cart.setItemUnit(requestDTO.getItemUnit());
            this.cartRepository.save(cart);
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "Cart item unit updated!", null);
        }catch (ResourceNotFoundException e){
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> deleteCart(Long id) throws  ResourceNotFoundException {
        try{
            Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!userDetails.getId().equals(cart.getUser().getId())){
                throw new ResourceNotFoundException("You are not allowed to delete this cart!");
            }
            this.cartRepository.delete(cart);
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK , ZonedDateTime.now(), "Product deleted from cart", null);
        }catch (ResourceNotFoundException e){
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

}
