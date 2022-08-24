package com.anbit.fashionist.service;

import org.springframework.http.ResponseEntity;

import com.anbit.fashionist.domain.dto.CreateTransactionRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;

public interface TransactionService {
    ResponseEntity<?> createTransaction(CreateTransactionRequestDTO requestDTO) throws ResourceNotFoundException;

    ResponseEntity<?> getTransactionHistories() throws ResourceNotFoundException;
    
    ResponseEntity<?> getTransactionHistory(Long id) throws ResourceNotFoundException;
    
    ResponseEntity<?> makePayment(Long transactionId) throws ResourceNotFoundException;

    ResponseEntity<?> sendProduct(Long transactionId, String receipt) throws ResourceNotFoundException;

    ResponseEntity<?> productDelivered(Long transactionId, String receipt) throws ResourceNotFoundException;
}
