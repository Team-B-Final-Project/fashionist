package com.anbit.fashionist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.RegionServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "7. Region Controller")
@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    RegionServiceImpl regionService;
    
    /***
     * 
     * @param name
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Get data of provinces")
    @GetMapping("/provinces")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getProvinces(@RequestParam(value  = "name", required = false) String name) throws ResourceNotFoundException {
        return regionService.getProvinces(name); 
    }

    /***
     * Get data of regencies
     * @param name
     * @param provinceId
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Get data of regencies")
    @GetMapping("/regencies")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getRegencies(
        @RequestParam(value  = "name", required = false) String name, 
        @RequestParam(value  = "provinceId", required = true) Integer provinceId) throws ResourceNotFoundException {
        return regionService.getRegencies(name, provinceId); 
    }

    /***
     * Get data of districts
     * @param name
     * @param regId
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Get data of districts")
    @GetMapping("/districts")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getDistricts(
        @RequestParam(value  = "name", required = false) String name, 
        @RequestParam(value  = "regencyId", required = true) Integer regId) throws ResourceNotFoundException {
        return regionService.getDistricts(name, regId); 
    }

    /***
     * Get data of villages
     * @param name
     * @param regId
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Get data of villages")
    @GetMapping("/villages")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getVillages(
        @RequestParam(value  = "name", required = false) String name, 
        @RequestParam(value  = "districtId", required = true) Integer districtId) throws ResourceNotFoundException {
        return regionService.getVillages(name, districtId); 
    }
}
