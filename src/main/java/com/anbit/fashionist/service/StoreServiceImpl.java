package com.anbit.fashionist.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.constant.ERole;
import com.anbit.fashionist.domain.dao.Address;
import com.anbit.fashionist.domain.dao.Role;
import com.anbit.fashionist.domain.dao.Store;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.CreateStoreRequestDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.AddressRepository;
import com.anbit.fashionist.repository.RoleRepository;
import com.anbit.fashionist.repository.StoreRepository;
import com.anbit.fashionist.repository.UserRepository;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    AuthServiceImpl authService;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepositor;

    private static final Logger logger = LoggerFactory.getLogger("ResponseHandler");
    
    private static final String loggerLine = "---------------------------------------";

    @Override
    public ResponseEntity<?> createStore(CreateStoreRequestDTO requestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException {
        User user = authService.getCurrentUser();
        if (Boolean.TRUE.equals(storeRepository.existsByUserUsername(user.getUsername()))) {
            throw new ResourceAlreadyExistException("You already have a store!");
        }
        Address address = addressRepository.findById(requestDTO.getAddressId()).orElseThrow(() -> new ResourceNotFoundException("Address not found!"));
        if (!address.getUser().getUsername().equals(user.getUsername())) {
            throw new ResourceNotFoundException("Address not found!");
        }
        Store store = Store.builder()
            .user(user)
            .name(requestDTO.getStoreName())
            .address(address)
            .build();
        storeRepository.save(store);
        Set<Role> roles = user.getRoles();
        Role seller = roleRepositor.findByName(ERole.ROLE_SELLER).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
        roles.add(seller);
        user.setRoles(roles);
        userRepository.save(user);
        logger.info(loggerLine);
        logger.info(store.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.CREATED, "Successfully create new store!", null);
    }
}
