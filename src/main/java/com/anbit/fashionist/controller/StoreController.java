package com.anbit.fashionist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anbit.fashionist.domain.dto.CreateStoreRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.StoreServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class StoreController {
    @Autowired
    StoreServiceImpl storeService;

    @PostMapping("/store/create")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> openStore(CreateStoreRequestDTO requestDTO) throws ResourceNotFoundException, ResourceAlreadyExistException {
        return storeService.createStore(requestDTO);
    }
}
