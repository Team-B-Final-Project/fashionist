package com.anbit.fashionist.controller;


import com.anbit.fashionist.domain.dto.CartRequestDTO;
import com.anbit.fashionist.domain.dto.UserRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CartController {
    @Autowired
    CartServiceImpl cartService;

    /***
     * Add product to cart
     * @param cartRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PostMapping("/add/cart")
    public ResponseEntity<?> add(CartRequestDTO cartRequestDTO) throws ResourceNotFoundException {
        return cartService.addCart(cartRequestDTO);
    }

    /***
     * Edit product in cart
     * @param cartRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping("/edit/cart")
    public ResponseEntity<?> edit(CartRequestDTO cartRequestDTO) throws ResourceNotFoundException {
        return cartService.editCart(cartRequestDTO);
    }

    /***
     * Delete product from cart
     * @param cartRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/delete/cart")
    public ResponseEntity<?> delete(CartRequestDTO cartRequestDTO, UserRequestDTO userRequestDTO) throws ResourceNotFoundException {
        return cartService.deleteCart(cartRequestDTO, userRequestDTO);
    }
}
