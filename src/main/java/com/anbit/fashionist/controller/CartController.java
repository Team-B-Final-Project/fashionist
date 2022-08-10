package com.anbit.fashionist.controller;


import com.anbit.fashionist.domain.dto.AddCartRequestDTO;
import com.anbit.fashionist.domain.dto.EditCartTotalItemRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.CartServiceImpl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(name = "5. Cart Controller")
@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearer-key")
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
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> add(@Valid AddCartRequestDTO requestDTO) throws ResourceNotFoundException {
        return cartService.addCart(requestDTO);
    }
    
    /***
     * Edit product in cart
     * @param cartRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping("/edit/cart")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> edit(EditCartTotalItemRequestDTO requestDTO) throws ResourceNotFoundException {
        return cartService.editCartTotalItem(requestDTO);
    }
    
    /***
     * Delete product from cart
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/delete/cart/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ResourceNotFoundException {
        return cartService.deleteCart(id);
    }
}
