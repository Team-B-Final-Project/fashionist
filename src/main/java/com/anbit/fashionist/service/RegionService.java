package com.anbit.fashionist.service;

import org.springframework.http.ResponseEntity;

import com.anbit.fashionist.helper.ResourceNotFoundException;

public interface RegionService {
    ResponseEntity<?> getProvinces(String name) throws ResourceNotFoundException;
    
    ResponseEntity<?> getRegencies(String name, Integer provinceId) throws ResourceNotFoundException;

    ResponseEntity<?> getDistricts(String name, Integer regId) throws ResourceNotFoundException;
    
    ResponseEntity<?> getVillages(String name, Integer districtId) throws ResourceNotFoundException;
}
