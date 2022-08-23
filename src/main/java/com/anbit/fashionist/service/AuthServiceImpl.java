package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.ProfilePicture;
import com.anbit.fashionist.domain.dao.Role;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.config.JwtUtils;
import com.anbit.fashionist.constant.ERole;

import com.anbit.fashionist.domain.dto.JwtResponseDTO;
import com.anbit.fashionist.domain.dto.SignInRequestDTO;

import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.helper.SignInFailException;
import com.anbit.fashionist.repository.RoleRepository;
import com.anbit.fashionist.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.domain.dto.SignUpRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ProfileService profileService;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> authenticateUser(SignInRequestDTO loginRequest) throws SignInFailException {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new SignInFailException("Username or password is wrong!"));
        Boolean isPasswordCorrect = encoder.matches(loginRequest.getPassword(), user.getPassword());
        if (Boolean.FALSE.equals(userRepository.existsByUsername(loginRequest.getUsername()))) {
            throw new SignInFailException("Username or password is wrong!");
        }
        if (Boolean.FALSE.equals(isPasswordCorrect)) {
            throw new SignInFailException("Username or password is wrong!");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully login!", new JwtResponseDTO(jwt, userDetails.getUsername(), roles));
    }

    @Override
    public ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException{
        if (userRepository.existsByUsername(signUpRequestDTO.getUsername())) {
            throw new ResourceAlreadyExistException("Username already taken!");
        }
        if (userRepository.existsByEmail(signUpRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistException("Email already exists!");
        }
        if (userRepository.existsByPhoneNumber(signUpRequestDTO.getPhoneNumber())) {
            throw new ResourceAlreadyExistException("Phone already exists!");
        }
        ProfilePicture profilePicture = profileService.getDefaultProfilePicture();
        User user = User.builder()
                .profilePicture(profilePicture)
                .firstName(signUpRequestDTO.getFirstName())
                .lastName(signUpRequestDTO.getLastName())
                .username(signUpRequestDTO.getUsername())
                .email(signUpRequestDTO.getEmail())
                .phoneNumber(signUpRequestDTO.getPhoneNumber())
                .password(encoder.encode(signUpRequestDTO.getPassword()))
                .build();
        Set<Role> roles = new HashSet<>();
        Role customer = roleRepository.findByName(ERole.ROLE_CUSTOMER).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
        roles.add(customer);
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "You have been registered successfully!", null);
    }

    @Override
    public User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getReferenceById(userDetails.getId());
        return user;
    }
}
