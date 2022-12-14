package com.anbit.fashionist.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anbit.fashionist.domain.dto.CreateStoreRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.StoreServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "4. Store Controller")
@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearer-key")
public class StoreController {
    @Autowired
    StoreServiceImpl storeService;

    /***
     * Open store for user who doesn't have any store yet
     * @param requestDTO
     * @return
     * @throws ResourceNotFoundException
     * @throws ResourceAlreadyExistException
     */
    @Operation(summary = "Open store for user who doesn't have any store yet")
    @PostMapping("/store/create")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> openStore(@Valid @RequestBody CreateStoreRequestDTO requestDTO) throws ResourceNotFoundException, ResourceAlreadyExistException {
        return storeService.createStore(requestDTO);
    }
}
