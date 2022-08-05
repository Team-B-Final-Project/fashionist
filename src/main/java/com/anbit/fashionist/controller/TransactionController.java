package com.anbit.fashionist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anbit.fashionist.domain.dto.CreateTransactionRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.TransactionServiceImpl;

@RequestMapping("/api/v1")
@RestController
public class TransactionController {
    @Autowired
    TransactionServiceImpl transactionService;

    @GetMapping("/transaction/create")
    public ResponseEntity<?> createTransaction(@RequestBody CreateTransactionRequestDTO requestDTO) throws ResourceNotFoundException {
        return transactionService.createTransaction(requestDTO);
    }
}
