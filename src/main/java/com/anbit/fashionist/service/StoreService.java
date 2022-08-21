package com.anbit.fashionist.service;

import org.springframework.http.ResponseEntity;

import com.anbit.fashionist.domain.dto.CreateStoreRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;

public interface StoreService {
    ResponseEntity<?> createStore(CreateStoreRequestDTO requestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException;
}
