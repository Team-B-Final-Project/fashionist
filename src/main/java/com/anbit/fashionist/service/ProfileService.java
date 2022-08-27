package com.anbit.fashionist.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.anbit.fashionist.domain.dao.ProfilePicture;
import com.anbit.fashionist.domain.dto.EditProfileRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;

public interface ProfileService {
    ResponseEntity<?> getProfile();

    ResponseEntity<?> editProfile(EditProfileRequestDTO requestDTO);
    
    ResponseEntity<?> changeProfilePicture(MultipartFile file) throws IOException;
    
    ResponseEntity<?> deleteProfilePicture() throws IOException, ResourceNotFoundException;

    ProfilePicture getDefaultProfilePicture() throws ResourceNotFoundException;
}
