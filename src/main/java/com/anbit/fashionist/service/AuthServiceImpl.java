package com.anbit.fashionist.service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.anbit.fashionist.constant.EErrorCode;
import com.anbit.fashionist.constant.ERole;
import com.anbit.fashionist.domain.dao.Role;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.SignUpRequestDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.repository.RoleRepository;
import com.anbit.fashionist.repository.UserRepository;

public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequestDTO) {
        try {
            if (userRepository.existsByUsername(signUpRequestDTO.getUsername())) {
                throw new ResourceAlreadyExistException("Username already taken!");
            }
            if (userRepository.existsByEmail(signUpRequestDTO.getEmail())) {
                throw new ResourceAlreadyExistException("Email already in use!");
            }
            User user = User.builder()
                    .firstName(signUpRequestDTO.getFirstName())
                    .lastName(signUpRequestDTO.getLastName())
                    .username(signUpRequestDTO.getUsername())
                    .email(signUpRequestDTO.getEmail())
                    .phoneNumber(signUpRequestDTO.getPhoneNumber())
                    .password(encoder.encode(signUpRequestDTO.getPassword()))
                    .build();
            Set<String> strRoles = signUpRequestDTO.getRole();
            Set<Role> roles = new HashSet<>();
            strRoles.forEach(role -> {
                switch(role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
                        roles.add(adminRole);
                    break;
                    case "cust":
                        Role modRole = roleRepository.findByName(ERole.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
                        roles.add(modRole);
                    break;
                    case "user":
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
                        roles.add(userRole);
                    break;
                    default :
                        Role user1Role = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
                        roles.add(user1Role);
                }
            });
            user.setRoles(roles);
            userRepository.save(user);
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "You have been registered successfully!", null);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, ZonedDateTime.now(), "", EErrorCode.MISSING_PARAM.getCode());
        }
    }
    
}
