package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.JwtResponse;
import com.anbit.fashionist.domain.dao.Role;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.config.JwtUtils;
import com.anbit.fashionist.constant.EErrorCode;
import com.anbit.fashionist.constant.ERole;
import com.anbit.fashionist.domain.dto.LoginRequest;

import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.RoleRepository;
import com.anbit.fashionist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
//public abstract class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Value("${com.app.domain}")
    String domain;

    @Value("${server.port}")
    String port;

    @Value("${com.app.name}")
    String projectName;

    @Value("${com.app.team}")
    String projectTeam;

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) throws ResourceNotFoundException {
        try {
            Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
            Boolean isPasswordCorrect = encoder.matches(loginRequest.getPassword(), user.get().getPassword());
            if (Boolean.FALSE.equals(userRepository.existsByUsername(loginRequest.getUsername()))) {
                throw new ResourceNotFoundException("Username or password is wrong!");
            }
            if (Boolean.FALSE.equals(isPasswordCorrect)) {
                throw new ResourceNotFoundException("Username or password is wrong!");
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            JwtUtils jwtUtils = new JwtUtils();
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "Successfully login!", new JwtResponse(jwt, userDetails.getUsername(), roles));
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, ZonedDateTime.now(), "", EErrorCode.MISSING_PARAM.getCode());
        }
    }

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