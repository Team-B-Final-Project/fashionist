package com.anbit.fashionist.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.anbit.fashionist.domain.dao.ProfilePicture;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.EditProfileRequestDTO;
import com.anbit.fashionist.domain.dto.GetProfileResponseDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.ProfilePictureRepository;
import com.anbit.fashionist.repository.UserRepository;
import com.anbit.fashionist.util.FileUploadUtil;

@Service
public class ProfileServiceImpl implements ProfileService {
    // private final AuthService authService;
    private AuthService authService;

    @Autowired
    public ProfileServiceImpl(@Lazy AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    ProfilePictureRepository profilePictureRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${com.anbit.fashionist.domain}")
    String domain;

    @Value("${com.anbit.fashionist.upload-dir}")
    String uploadDirectory;

    @Override
    public ResponseEntity<?> getProfile() {
        User user = authService.getCurrentUser();
        GetProfileResponseDTO responseDTO = GetProfileResponseDTO.builder()
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .profilePictureUrl(user.getProfilePicture().getUrl())
            .email(user.getEmail())
            .username(user.getUsername())
            .sex(user.getSex())
            .dateOfBirth(user.getDateOfBirth())
            .phoneNumber(user.getPhoneNumber())
            .build();
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully retrieved data!", responseDTO);
    }

    @Override
    public ResponseEntity<?> editProfile(EditProfileRequestDTO requestDTO) {
        User user = authService.getCurrentUser();
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());
        user.setDateOfBirth(requestDTO.getDateOfBirth());
        user.setPhoneNumber(requestDTO.getPhoneNumber());
        User editedUser = userRepository.save(user);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully update profile!", editedUser);
    }
    
    @Override
    public ResponseEntity<?> changeProfilePicture(MultipartFile file) throws IOException {
        User user = authService.getCurrentUser();
        ProfilePicture currentProfilePicture = user.getProfilePicture();
        String fileName = StringUtils.cleanPath(user.getUsername() + file.getOriginalFilename().replaceAll(".+[.]", "."));
        String uploadDir = uploadDirectory + "/images/profile_picture/";
        FileUploadUtil.saveFile(uploadDir, fileName, file);
        ProfilePicture profilePicture = ProfilePicture.builder()
            .name(fileName)
            .size(file.getSize())
            .type(file.getContentType())
            .url(domain + "/api/v1/profile/file/profile_picture/" + fileName)
            .build();
        ProfilePicture newProfilePicture = profilePictureRepository.save(profilePicture);
        user.setProfilePicture(newProfilePicture);
        if (currentProfilePicture.getId() != 1) {
            profilePictureRepository.delete(currentProfilePicture);
        }
        userRepository.save(user);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully update profile picture!", null);
    }

    @Override
    public ResponseEntity<byte[]> getProfilePicture(String fileName) throws IOException {
        ClassPathResource file = new ClassPathResource("file_upload/images/profile_picture/" + fileName);
        byte[] bytes = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    @Override
    public ProfilePicture getDefaultProfilePicture() throws ResourceNotFoundException {
        ProfilePicture profilePicture = profilePictureRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("ProfilePicture not found!"));
        return profilePicture;
    }
}
