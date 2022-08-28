package com.anbit.fashionist.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.repository.DistrictRepository;
import com.anbit.fashionist.repository.ProvinceRepository;
import com.anbit.fashionist.repository.RegencyRepository;
import com.anbit.fashionist.repository.VillageRepository;
import com.anbit.fashionist.domain.dao.District;
import com.anbit.fashionist.domain.dao.Province;
import com.anbit.fashionist.domain.dao.Regency;
import com.anbit.fashionist.domain.dao.Village;
import com.anbit.fashionist.domain.dto.GetRegionResonseDTO;
import com.anbit.fashionist.domain.dto.PostalCodeResponseDTO;
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

    private static final Logger logger = LoggerFactory.getLogger("ResponseHandler");
    
    private static final String loggerLine = "---------------------------------------";

    @Override
    public ResponseEntity<?> getProvinces(String name) throws ResourceNotFoundException {
        List<Province> provinceList;
        if (name == null) {
            provinceList = ProvinceRepository.findAll();
        }else {
            provinceList = ProvinceRepository.findByNameContainingIgnoreCase(name);
        }
        if (provinceList.isEmpty()) {
            throw new ResourceNotFoundException("Province not found!");
        }
        List<GetRegionResonseDTO> responseDTOs = new ArrayList<>();
        provinceList.forEach(province -> {
            GetRegionResonseDTO responseDTO = GetRegionResonseDTO.builder()
                .id(province.getId().longValue())
                .parentId(null)
                .name(province.getName())
                .build();
            responseDTOs.add(responseDTO);
        });
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("total_item", provinceList.size());
        logger.info(loggerLine);
        logger.info("Successfully retrieved data!");
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponseWithMeta(HttpStatus.OK, "Successfully retrieved data!", responseDTOs, metaData);
    }

    @Override
    public ResponseEntity<?> getRegencies(String name, Integer provinceId) throws ResourceNotFoundException {
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
        if (regencyList.isEmpty()) {
            throw new ResourceNotFoundException("Regency not found!");
        }
        List<GetRegionResonseDTO> responseDTOs = new ArrayList<>();
        regencyList.forEach(regency -> {
            GetRegionResonseDTO responseDTO = GetRegionResonseDTO.builder()
                .id(regency.getId().longValue())
                .parentId(regency.getProvince().getId())
                .name(regency.getName())
                .build();
            responseDTOs.add(responseDTO);
        });
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("total_item", regencyList.size());
        logger.info(loggerLine);
        logger.info("Successfully retrieved data!");
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponseWithMeta(HttpStatus.OK, "Successfully retrieved data!", responseDTOs, metaData);
    }

    @Override
    public ResponseEntity<?> getDistricts(String name, Integer regId) throws ResourceNotFoundException {
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
        if (districtList.isEmpty()) {
            throw new ResourceNotFoundException("District not found!");
        }
        List<GetRegionResonseDTO> responseDTOs = new ArrayList<>();
        districtList.forEach(district -> {
            GetRegionResonseDTO responseDTO = GetRegionResonseDTO.builder()
                .id(district.getId().longValue())
                .parentId(district.getRegency().getId())
                .name(district.getName())
                .build();
            responseDTOs.add(responseDTO);
        });
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("total_item", districtList.size());
        logger.info(loggerLine);
        logger.info("Successfully retrieved data!");
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponseWithMeta(HttpStatus.OK, "Successfully retrieved data!", responseDTOs, metaData);
    }

    @Override
    public ResponseEntity<?> getVillages(String name, Integer districtId) throws ResourceNotFoundException {
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
        if (villageList.isEmpty()) {
            throw new ResourceNotFoundException("Village not found!");
        }
        List<GetRegionResonseDTO> responseDTOs = new ArrayList<>();
        villageList.forEach(village -> {
            GetRegionResonseDTO responseDTO = GetRegionResonseDTO.builder()
                .id(village.getId().longValue())
                .parentId(village.getDistrict().getId())
                .name(village.getName())
                .build();
            responseDTOs.add(responseDTO);
        });
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("total_item", villageList.size());
        logger.info(loggerLine);
        logger.info("Successfully retrieved data!");
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponseWithMeta(HttpStatus.OK, "Successfully retrieved data!", responseDTOs, metaData);
    }

    @Override
    public ResponseEntity<?> getPostalCodes(Long villageId) throws ResourceNotFoundException {
        Village village = villageRepository.findById(villageId).orElseThrow(() -> new ResourceNotFoundException("Village not found!"));
        List<PostalCodeResponseDTO> responseDTOs = new ArrayList<>();
        String[] strArray = village.getPostal().split(",");
        List.of(strArray).forEach(str -> {
            PostalCodeResponseDTO responseDTO = PostalCodeResponseDTO.builder()
            .postalCode(str)
            .build();
            responseDTOs.add(responseDTO);
        });
        logger.info(loggerLine);
        logger.info("Successfully retrieved data!");
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully retrieved data!", responseDTOs);
    }
}
