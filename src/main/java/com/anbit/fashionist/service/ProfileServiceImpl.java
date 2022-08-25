package com.anbit.fashionist.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

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

    @Autowired
    Cloudinary cloudinaryConfig;

    @Value("${com.anbit.fashionist.domain}")
    String domain;

    @Value("${com.anbit.fashionist.upload-dir}")
    String uploadDirectory;

    private static final Logger logger = LoggerFactory.getLogger("ResponseHandler");
    
    private static final String loggerLine = "---------------------------------------";

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
        logger.info(loggerLine);
        logger.info(responseDTO.toString());
        logger.info(loggerLine);
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
        logger.info(loggerLine);
        logger.info(editedUser.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully update profile!", null);
    }
    
    @Override
    public ResponseEntity<?> changeProfilePicture(MultipartFile file) throws IOException {
        User user = authService.getCurrentUser();
        Map<?,?> uploadResult =  new HashMap<>();
        Long currentProfilePictureId = user.getProfilePicture().getId();
        if (currentProfilePictureId == 1L) {
            uploadResult = cloudinaryConfig.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", "/image/profile_picture/"));
        } else {
            uploadResult = cloudinaryConfig.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "public_id", user.getProfilePicture().getPublicId(), 
                "overwrite", true,
                "folder", "/image/profile_picture/"));
        }
        ProfilePicture currentProfilePicture = user.getProfilePicture();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        ProfilePicture profilePicture = ProfilePicture.builder()
            .name(fileName)
            .publicId(uploadResult.get("public_id").toString())
            .size(file.getSize())
            .type(uploadResult.get("resource_type").toString() + "/" + uploadResult.get("format").toString())
            .url(uploadResult.get("secure_url").toString())
            .build();
        ProfilePicture newProfilePicture = profilePictureRepository.save(profilePicture);
        user.setProfilePicture(newProfilePicture);
        if (currentProfilePicture.getId() != 1) {
            profilePictureRepository.delete(currentProfilePicture);
        }
        userRepository.save(user);
        logger.info(loggerLine);
        logger.info(user.toString());
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully update profile picture!", null);
    }

    @Override
    public ProfilePicture getDefaultProfilePicture() throws ResourceNotFoundException {
        ProfilePicture profilePicture = profilePictureRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("ProfilePicture not found!"));
        return profilePicture;
    }

}
