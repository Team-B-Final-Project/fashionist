package com.anbit.fashionist.service;

import org.springframework.http.ResponseEntity;

import com.anbit.fashionist.domain.dto.CreateAddressRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;

public interface AddressService {
    ResponseEntity<?> createAddress(CreateAddressRequestDTO requestDTO) throws ResourceNotFoundException;

    ResponseEntity<?> getCurrentUserAddresses() throws ResourceNotFoundException;
}
