package com.anbit.fashionist.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.anbit.fashionist.domain.dto.CreateTransactionRequestDTO;
import com.anbit.fashionist.domain.dto.SendProductRequsetDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.TransactionServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transaction Controller")
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
    public ResponseEntity<?> createTransaction(@Valid @RequestBody CreateTransactionRequestDTO requestDTO) throws ResourceNotFoundException {
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
    public ResponseEntity<?> transactionHistory(@Valid @PathVariable("id") Long id) throws ResourceNotFoundException{
        return transactionService.getTransactionHistory(id);
    }

    /***
     * Pay a transaction
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Pay a transaction")
    @GetMapping("/transaction/pay")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> transactionPay(@Valid @RequestParam("id") Long transactionId) throws ResourceNotFoundException{
        return transactionService.makePayment(transactionId);
    }

    /***
     * Send product and input the receipt
     * @param transactionId
     * @param requsetDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Send product and input the receipt")
    @PostMapping("/transaction/send_product")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<?> sendProduct(@Valid @RequestParam("id") Long transactionId, SendProductRequsetDTO requsetDTO) throws ResourceNotFoundException{
        return transactionService.sendProduct(transactionId, requsetDTO);
    }

    /***
     * Confirm product has been received and transaction is successfull
     * @param transactionId
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Confirm product has been received and transaction is successfull")
    @GetMapping("/transaction/confirm")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> confirmProduct(@Valid @RequestParam("id") Long transactionId) throws ResourceNotFoundException{
        return transactionService.productDelivered(transactionId);
    }
}
