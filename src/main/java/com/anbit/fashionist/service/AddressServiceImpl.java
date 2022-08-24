package com.anbit.fashionist.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.domain.dao.Address;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dao.Village;
import com.anbit.fashionist.domain.dto.CreateAddressRequestDTO;
import com.anbit.fashionist.domain.dto.AddressResponseDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.AddressRepository;
import com.anbit.fashionist.repository.UserRepository;
import com.anbit.fashionist.repository.VillageRepository;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AuthServiceImpl authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    VillageRepository villageRepository;

    private static final Logger logger = LoggerFactory.getLogger("ResponseHandler");
    
    private static final String loggerLine = "---------------------------------------";

    @Override
    public ResponseEntity<?> createAddress(CreateAddressRequestDTO requestDTO) throws ResourceNotFoundException {
        User user = authService.getCurrentUser();
        Village village = villageRepository.findById(requestDTO.getVillageId()).orElseThrow(() -> new ResourceNotFoundException("Village not found!"));

        Address address = Address.builder()
            .user(user)
            .phoneNumber(requestDTO.getPhoneNumber())
            .name(requestDTO.getName())
            .village(village)
            .postalCode(requestDTO.getPostalCode())
            .fullAddress(requestDTO.getFullAddress())
            .build();
        addressRepository.save(address);
        logger.info(loggerLine);
        logger.info("Successfully create new address!");
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully create new address!", null);
    }

    @Override
    public ResponseEntity<?> getCurrentUserAddresses() throws ResourceNotFoundException {
        User user = authService.getCurrentUser();
        List<Address> addresses = addressRepository.findByUser(user);
        if (addresses.isEmpty()) {
            throw new ResourceNotFoundException("You have no address yet!");
        }
        List<AddressResponseDTO> responseDTOs = new ArrayList<>();
        addresses.forEach(address -> {
            AddressResponseDTO responseDTO = AddressResponseDTO.builder()
                .id(address.getId())
                .name(address.getName())
                .phoneNumber(address.getPhoneNumber())
                .fullAddress(address.getFullAddress())
                .postalCode(address.getPostalCode())
                .build();
                responseDTO.setRegion(
                    address.getVillage().getDistrict().getRegency().getProvince(), 
                    address.getVillage().getDistrict().getRegency(), 
                    address.getVillage().getDistrict(), 
                    address.getVillage()
                );
                responseDTOs.add(responseDTO);
            });
        logger.info(loggerLine);
        logger.info(responseDTOs.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully retrieved data!", responseDTOs);
    }
}
