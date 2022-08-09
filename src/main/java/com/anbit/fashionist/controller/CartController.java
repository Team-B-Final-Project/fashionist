package com.anbit.fashionist.controller;


import com.anbit.fashionist.domain.dto.AddCartRequestDTO;
import com.anbit.fashionist.domain.dto.EditCartTotalItemRequestDTO;
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
    public ResponseEntity<?> add(AddCartRequestDTO requestDTO) throws ResourceNotFoundException {
        return cartService.addCart(requestDTO);
    }

    /***
     * Edit product in cart
     * @param cartRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping("/edit/cart")
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
    public ResponseEntity<?> delete(@PathVariable Long id) throws ResourceNotFoundException {
        return cartService.deleteCart(id);
    }
}
