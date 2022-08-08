package com.anbit.fashionist.controller;


import com.anbit.fashionist.domain.dto.CartRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    CartServiceImpl cartService;
    CartRequestDTO cartRequestDTO;


    @PostMapping("/api/v1/cart/add")
    public ResponseEntity<?> add(CartRequestDTO cartRequestDTO) throws ResourceNotFoundException {
        return cartService.addCart(cartRequestDTO);
    }

    @PutMapping("/api/v1/cart/edit")
    public ResponseEntity<?> edit(CartRequestDTO cartRequestDTO) throws ResourceNotFoundException {
        return cartService.editCart(cartRequestDTO);
    }

    @DeleteMapping("/api/v1/cart/delete")
    public ResponseEntity<?> delete(CartRequestDTO cartRequestDTO) throws ResourceNotFoundException {
        return cartService.deleteCart(cartRequestDTO);
    }
}
