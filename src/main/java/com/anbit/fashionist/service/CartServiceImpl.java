package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.dao.Cart;
import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.CartRequestDTO;
import com.anbit.fashionist.domain.dto.UserRequestDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.CartRepository;
import com.anbit.fashionist.repository.ProductRepository;
import com.anbit.fashionist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
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
    public ResponseEntity<?> addCart(CartRequestDTO cartRequestDTO) throws ResourceNotFoundException {
        try{
            Optional<Product> product = productRepository.findById(cartRequestDTO.getId());
            if(product.isPresent()){
                Product product1 = product.get();
                Cart cart = Cart.builder()
                        .product(product1)
                        .itemUnit(cartRequestDTO.getItemUnit())
                        .totalPrice(cartRequestDTO.getTotalPrice())
                        .build();
                this.cartRepository.save(cart);
                return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(),"Cart added successfully" , cart);
            }
            else{
                throw new ResourceNotFoundException("Product not found");
            }
        }catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> editCart(CartRequestDTO cartRequestDTO) throws ResourceNotFoundException {
        try {
            Cart cart = cartRequestDTO.convertToEntity();
            if(Boolean.FALSE.equals(cartRepository.existsById(cart.getId()))){
                throw new ResourceNotFoundException("Cart not found");
            }
            this.cartRepository.save(cart);
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "cart updated",cart);
        }catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteCart(CartRequestDTO cartRequestDTO, UserRequestDTO userRequestDTO) throws  ResourceNotFoundException {
        try{
            Optional<Cart> cart = cartRepository.findById(cartRequestDTO.getId());
            Optional<User> users = userRepository.findByUserId(userRequestDTO.getId());
                if(Boolean.FALSE.equals(cartRepository.existsById(cart.get().getId()))){
                    throw new ResourceNotFoundException("Product not available");
                }else{
//                    if user is logged in, remove user from cart
                    if(users.isPresent()){
                        Cart cartTobeDeleted = cart.get();
                        cartTobeDeleted.setUser(users.get());
                        this.cartRepository.delete(cartTobeDeleted);
                        return ResponseHandler.generateSuccessResponse(HttpStatus.OK , ZonedDateTime.now(), "Product deleted from cart", null);
                    }else {
                        throw new ResourceNotFoundException("User not found");
                    }
                }

        }catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

}
