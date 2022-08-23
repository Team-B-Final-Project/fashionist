package com.anbit.fashionist.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anbit.fashionist.domain.dto.EditProfileRequestDTO;
import com.anbit.fashionist.service.ProfileServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "8. Profile Controller")
@RestController
@RequestMapping("/api/v1/profile")
@SecurityRequirement(name = "bearer-key")
public class ProfileController {
    @Autowired
    ProfileServiceImpl profileService;

    /***
     * Get information about the current authenticated user's profile
     * @return
     */
    @Operation(summary = "Get information about the current authenticated user's profile")
    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> getProfile() {
        return profileService.getProfile();
    } 

    /***
     * Edit the current authenticated user's profile information
     * @param requestDTO
     * @return
     */
    @Operation(summary = "Edit the current authenticated user's profile information")
    @PutMapping("/")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> editProfile(@Valid @RequestBody EditProfileRequestDTO requestDTO) {
        return profileService.editProfile(requestDTO);
    }

    /***
     * Change profile picture of the current authenticated user
     * @param file
     * @return
     * @throws IOException
     */
    @Operation(summary = "Change profile picture of the current authenticated user")
    @PatchMapping(value = "/profile_picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> changeProfilePicture(@Valid @RequestParam MultipartFile file) throws IOException{
        return profileService.changeProfilePicture(file);
    }
    
    /***
     * Get profile picture of the current authenticated user
     * @param fileName
     * @return
     * @throws IOException
     */
    @Operation(summary = "Get profile picture of the current authenticated user")
    @GetMapping("/file/profile_picture/{fileName}")
    public ResponseEntity<byte[]> getProfilePicture(@Valid @PathVariable("fileName") String fileName) throws IOException{
        return profileService.getProfilePicture(fileName);
    }
}
