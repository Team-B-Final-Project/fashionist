package com.anbit.fashionist.controller;


import com.anbit.fashionist.domain.dto.AddCartRequestDTO;
import com.anbit.fashionist.domain.dto.EditCartTotalItemRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.CartServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
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

    /***xception
     * @throws ResourceAlreadyExistException
     */
    @Operation(summary = "Add a product to your cart")
    @PostMapping("/add/cart")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> add(@Valid @RequestBody AddCartRequestDTO requestDTO) throws ResourceNotFoundException, ResourceAlreadyExistException {
        return cartService.addCart(requestDTO);
    }
    
    /***
     * Edit product in cart
     * @param cartRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Edit total item unit of a product in cart")
    @PatchMapping("/edit/cart")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> edit(@Valid @RequestBody EditCartTotalItemRequestDTO requestDTO) throws ResourceNotFoundException {
        return cartService.editCartTotalItem(requestDTO);
    }
    
    /***
     * Delete product from cart
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "delete product from cart")
    @DeleteMapping("/delete/cart/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) throws ResourceNotFoundException {
        return cartService.deleteCart(id);
    }

    /***
     * Get all cart
     * @return
     * @throws ResourceNotFoundException
     */

    @Operation(summary = "Get all cart")
    @GetMapping("/get/cart")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        return cartService.getCart();
    }
}
