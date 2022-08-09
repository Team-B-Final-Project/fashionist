package com.anbit.fashionist.service;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.constant.EErrorCode;
import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.Address;
import com.anbit.fashionist.domain.dao.Store;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.CreateStoreRequestDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.AddressRepository;
import com.anbit.fashionist.repository.StoreRepository;
import com.anbit.fashionist.repository.UserRepository;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreRepository storeRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> createStore(CreateStoreRequestDTO requestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
            Address address = addressRepository.findById(requestDTO.getAddressId()).orElseThrow(() -> new ResourceNotFoundException("Address not found!"));
            if (address.getUser().getUsername() != userDetails.getUsername()) {
                throw new ResourceNotFoundException("Address not found!");
            }
            Store store = Store.builder()
                .user(user.get())
                .name(requestDTO.getStoreName())
                .address(address)
                .build();
            storeRepository.save(store);
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "Successfully create new store!", null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }
    
}
