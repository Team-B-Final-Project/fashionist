package com.anbit.fashionist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.anbit.fashionist.domain.dto.CreateTransactionRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.TransactionServiceImpl;

@RequestMapping("/api/v1")
@RestController
public class TransactionController {
    @Autowired
    TransactionServiceImpl transactionService;

    /***
     * Create a transaction of current user 
     * @param requestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PostMapping("/transaction/create")
    public ResponseEntity<?> createTransaction(@RequestBody CreateTransactionRequestDTO requestDTO) throws ResourceNotFoundException {
        return transactionService.createTransaction(requestDTO);
    }

    /***
     * Get transaction histories per product of current user
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/transaction/histories")
    public ResponseEntity<?> transactionHistories() throws ResourceNotFoundException {
        return transactionService.getTransactionHistories();
    }
    
    /***
     * Get details of product we bought
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @PostMapping("/transaction/{uuid}")
    public ResponseEntity<?> transactionHistory(@PathVariable("uuid") Long id) throws ResourceNotFoundException{
        return transactionService.getTransactionHistory(id);
    }
}
