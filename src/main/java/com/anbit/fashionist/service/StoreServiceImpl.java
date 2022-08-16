package com.anbit.fashionist.service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

import com.anbit.fashionist.controller.StoreController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.constant.EErrorCode;
import com.anbit.fashionist.constant.ERole;
import com.anbit.fashionist.domain.common.UserDetailsImpl;
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
    StoreRepository storeRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepositor;

    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
    private static final String loggerLine = "---------------------------------------";

    @Override
    public ResponseEntity<?> createStore(CreateStoreRequestDTO requestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
            if (Boolean.TRUE.equals(storeRepository.existsByUserUsername(userDetails.getUsername()))) {
                throw new ResourceAlreadyExistException("You already have a store!");
            }
            Address address = addressRepository.findById(requestDTO.getAddressId()).orElseThrow(() -> new ResourceNotFoundException("Address not found!"));
            if (!address.getUser().getUsername().equals(userDetails.getUsername())) {
                throw new ResourceNotFoundException("Address not found!");
            }
            Store store = Store.builder()
                .user(user.get())
                .name(requestDTO.getStoreName())
                .address(address)
                .build();
            storeRepository.save(store);
            Set<Role> roles = user.get().getRoles();
            Role seller = roleRepositor.findByName(ERole.ROLE_SELLER).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
            roles.add(seller);
            user.get().setRoles(roles);
            userRepository.save(user.get());
            logger.info(loggerLine);
            logger.info("Create Store " + store);
            logger.info(loggerLine);
            return ResponseHandler.generateSuccessResponse(HttpStatus.CREATED, ZonedDateTime.now(), "Successfully create new store!", null);
        } catch (ResourceNotFoundException e) {
            logger.error(loggerLine);
            logger.error(e.getMessage());
            logger.error(loggerLine);
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        } catch(ResourceAlreadyExistException e){
            logger.error(loggerLine);
            logger.error(e.getMessage());
            logger.error(loggerLine);
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_ACCEPTABLE, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }
}
