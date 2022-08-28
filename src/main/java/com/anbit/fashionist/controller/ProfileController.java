package com.anbit.fashionist.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anbit.fashionist.domain.dto.EditProfileRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.ProfileServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Profile Controller")
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
     * @throws ResourceAlreadyExistException
     */
    @Operation(summary = "Edit the current authenticated user's profile information")
    @PutMapping("/")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> editProfile(@Valid @RequestBody EditProfileRequestDTO requestDTO) throws ResourceAlreadyExistException {
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
     * Delete the current authenticated user's profile picture
     * @param requestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Delete the current authenticated user's profile picture")
    @DeleteMapping("/profile_picture")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> deleteProfilePicture() throws IOException, ResourceNotFoundException{
        return profileService.deleteProfilePicture();
    }
    
}
