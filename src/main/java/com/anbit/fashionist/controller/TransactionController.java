package com.anbit.fashionist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.anbit.fashionist.domain.dto.CreateTransactionRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.TransactionServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "6. Transaction Controller")
@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearer-key")
public class TransactionController {
    @Autowired
    TransactionServiceImpl transactionService;

    /***
     * Create a transaction with selected products
     * @param requestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Create a transaction with selected products")
    @PostMapping("/transaction/create")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> createTransaction(@RequestBody CreateTransactionRequestDTO requestDTO) throws ResourceNotFoundException {
        return transactionService.createTransaction(requestDTO);
    }

    /***
     * Get transaction histories per product of current user
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Get transaction histories per product of current user")
    @GetMapping("/transaction/histories")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> transactionHistories() throws ResourceNotFoundException {
        return transactionService.getTransactionHistories();
    }
    
    /***
     * Get details of product we bought
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Get details of product we bought")
    @GetMapping("/transaction/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> transactionHistory(@PathVariable("id") Long id) throws ResourceNotFoundException{
        return transactionService.getTransactionHistory(id);
    }
}
