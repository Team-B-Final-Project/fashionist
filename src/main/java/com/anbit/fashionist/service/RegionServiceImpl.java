package com.anbit.fashionist.service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.repository.DistrictRepository;
import com.anbit.fashionist.repository.ProvinceRepository;
import com.anbit.fashionist.repository.RegencyRepository;
import com.anbit.fashionist.repository.VillageRepository;
import com.anbit.fashionist.constant.EErrorCode;
import com.anbit.fashionist.domain.dao.District;
import com.anbit.fashionist.domain.dao.Province;
import com.anbit.fashionist.domain.dao.Regency;
import com.anbit.fashionist.domain.dao.Village;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;

@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    ProvinceRepository ProvinceRepository;

    @Autowired
    RegencyRepository regencyRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    VillageRepository villageRepository;

    @Override
    public ResponseEntity<?> getProvinces(String name) throws ResourceNotFoundException {
        try {
            List<Province> provinceList;
            if (name == null) {
                provinceList = ProvinceRepository.findAll();
            }else {
                provinceList = ProvinceRepository.findByNameContainingIgnoreCase(name);
            }
            if (provinceList.isEmpty()) {
                throw new ResourceNotFoundException("Province not found!");
            }
            Map<String, Object> metaData = new HashMap<>();
            metaData.put("total_item", provinceList.size());
            return ResponseHandler.generateSuccessResponseWithMeta(HttpStatus.OK, ZonedDateTime.now(), "Successfully retrieved data!", provinceList, metaData);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> getRegencies(String name, Integer provinceId) throws ResourceNotFoundException {
        try {
            List<Regency> regencyList;
            if (name == null && provinceId == null) {
                regencyList = regencyRepository.findAll();
            } else if(name != null && provinceId == null) {
                regencyList = regencyRepository.findByNameContainingIgnoreCase(name);
            } else if(name == null && provinceId != null) {
                regencyList = regencyRepository.findByProvinceId(provinceId);
            } else {
                regencyList = regencyRepository.findByNameContainingIgnoreCaseAndProvinceId(name, provinceId);
            }
            if (regencyList == null) {
                throw new ResourceNotFoundException("Regency not found!");
            }
            Map<String, Object> metaData = new HashMap<>();
            metaData.put("total_item", regencyList.size());
            return ResponseHandler.generateSuccessResponseWithMeta(HttpStatus.OK, ZonedDateTime.now(), "Successfully retrieved data!", regencyList, metaData);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> getDistricts(String name, Integer regId) throws ResourceNotFoundException {
        try {
            List<District> districtList;
            if (name == null && regId == null) {
                districtList = districtRepository.findAll();
            } else if(name != null && regId == null) {
                districtList = districtRepository.findByNameContainingIgnoreCase(name);
            } else if(name == null && regId != null) {
                districtList = districtRepository.findByRegencyId(regId);
            } else {
                districtList = districtRepository.findByNameContainingIgnoreCaseAndRegencyId(name, regId);
            }
            if (districtList == null) {
                throw new ResourceNotFoundException("District not found!");
            }
            Map<String, Object> metaData = new HashMap<>();
            metaData.put("total_item", districtList.size());
            return ResponseHandler.generateSuccessResponseWithMeta(HttpStatus.OK, ZonedDateTime.now(), "Successfully retrieved data!", districtList, metaData);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> getVillages(String name, Integer districtId) throws ResourceNotFoundException {
        try {
            List<Village> villageList;
            if (name == null && districtId == null) {
                villageList = villageRepository.findAll();
            } else if(name != null && districtId == null) {
                villageList = villageRepository.findByNameContainingIgnoreCase(name);
            } else if(name == null && districtId != null) {
                villageList = villageRepository.findByDistrictId(districtId);
            } else {
                villageList = villageRepository.findByNameContainingIgnoreCaseAndDistrictId(name, districtId);
            }
            if (villageList == null) {
                throw new ResourceNotFoundException("District not found!");
            }
            Map<String, Object> metaData = new HashMap<>();
            metaData.put("total_item", villageList.size());
            return ResponseHandler.generateSuccessResponseWithMeta(HttpStatus.OK, ZonedDateTime.now(), "Successfully retrieved data!", villageList, metaData);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }
}
