package com.anbit.fashionist.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.anbit.fashionist.controller.AddressController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.constant.EErrorCode;
import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.Address;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.CreateAddressRequestDTO;
import com.anbit.fashionist.domain.dto.GetCurrentUserAddressesResponseDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.AddressRepository;
import com.anbit.fashionist.repository.UserRepository;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
    private static final String loggerLine = "---------------------------------------";

    @Override
    public ResponseEntity<?> createAddress(CreateAddressRequestDTO requestDTO) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
            Address address = Address.builder()
                .user(user)
                .phoneNumber(requestDTO.getPhoneNumber())
                .name(requestDTO.getName())
                .province(requestDTO.getProvince())
                .city(requestDTO.getCity())
                .district(requestDTO.getDistrict())
                .fullAddress(requestDTO.getFullAddress())
                .build();
            addressRepository.save(address);
            logger.info(loggerLine);
//            logger.info("Create Address " + address);
            logger.info(loggerLine);
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "Successfully create new address!", null);
        } catch (ResourceNotFoundException e) {
            logger.error(loggerLine);
            logger.error(e.getMessage());
            logger.error(loggerLine);
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> getCurrentUserAddresses() throws ResourceNotFoundException {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.getReferenceById(userDetails.getId());
            List<Address> addresses = addressRepository.findByUser(user);
            if (addresses.isEmpty()) {
                throw new ResourceNotFoundException("You have no address yet!");
            }
            List<GetCurrentUserAddressesResponseDTO> responseDTOs = new ArrayList<>();
            addresses.forEach(address -> {
                GetCurrentUserAddressesResponseDTO responseDTO = GetCurrentUserAddressesResponseDTO.builder()
                    .id(address.getId())
                    .name(address.getName())
                    .phoneNumber(address.getPhoneNumber())
                    .province(address.getProvince())
                    .city(address.getCity())
                    .district(address.getDistrict())
                    .fullAddress(address.getFullAddress())
                    .build();
                    responseDTOs.add(responseDTO);
                });
            logger.info(loggerLine);
            logger.info("Current User Address " + user);
            logger.info(loggerLine);
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "Successfully retrieved data!", responseDTOs);
        } catch (ResourceNotFoundException e) {
            logger.error(loggerLine);
            logger.error(e.getMessage());
            logger.error(loggerLine);
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }
}
