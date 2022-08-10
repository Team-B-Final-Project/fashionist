package com.anbit.fashionist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anbit.fashionist.domain.dto.CreateAddressRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.AddressServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "2. Address Controller")
@RestController
@RequestMapping("/api/v1/address")
@SecurityRequirement(name = "bearer-key")
public class AddressController {
    @Autowired
    AddressServiceImpl addressService;

    /***
     * Add new address for customer or seller
     * @param requestDTO
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER') or hasAuthority('ROLE_SELLER')")
    @Operation(summary = "Add new address for customer or seller")
    @PostMapping("/add")
    public ResponseEntity<?> addAddress(@RequestBody CreateAddressRequestDTO requestDTO){
        return addressService.createAddress(requestDTO); 
    }

    /***
     * Get list of address of current user
     * @return
     * @throws ResourceNotFoundException
     */
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER') or hasAuthority('ROLE_SELLER')")
    @Operation(summary = "Get list of address of current user")
    @GetMapping("/")
    public ResponseEntity<?> getCurrentUserAddresses() throws ResourceNotFoundException {
        return addressService.getCurrentUserAddresses();
    }
}
