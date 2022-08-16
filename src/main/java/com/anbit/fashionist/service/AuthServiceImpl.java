package com.anbit.fashionist.service;

import com.anbit.fashionist.controller.AuthController;
import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.Role;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.config.JwtUtils;
import com.anbit.fashionist.constant.EErrorCode;
import com.anbit.fashionist.constant.ERole;

import com.anbit.fashionist.domain.dto.JwtResponseDTO;
import com.anbit.fashionist.domain.dto.SignInRequestDTO;

import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.RoleRepository;
import com.anbit.fashionist.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private static final String loggerLine = "---------------------------------------";

    @Override

    public ResponseEntity<?> authenticateUser(SignInRequestDTO loginRequest) throws ResourceNotFoundException {

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
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
            logger.info(loggerLine);
            logger.info("Aunthenticate User " + authentication);
            logger.info(loggerLine);
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "Successfully login!", new JwtResponseDTO(jwt, userDetails.getUsername(), roles));
        } catch (ResourceNotFoundException e) {
            logger.error(loggerLine);
            logger.error(e.getMessage());
            logger.error(loggerLine);
            return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException{
        try {
            if (userRepository.existsByUsername(signUpRequestDTO.getUsername())) {
                throw new ResourceAlreadyExistException("Username already taken!");
            }
            if (userRepository.existsByEmail(signUpRequestDTO.getEmail())) {
                throw new ResourceAlreadyExistException("Email already exists!");
            }
            if (userRepository.existsByPhoneNumber(signUpRequestDTO.getPhoneNumber())) {
                throw new ResourceAlreadyExistException("Phone already exists!");
            }
            User user = User.builder()
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
            logger.info(loggerLine);
            logger.info("Register User " + user);
            logger.info(loggerLine);
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "You have been registered successfully!", null);
        } catch (ResourceAlreadyExistException e) {
            logger.error(loggerLine);
            logger.error(e.getMessage());
            logger.error(loggerLine);
            return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        } catch (ResourceNotFoundException e) {
            logger.error(loggerLine);
            logger.error(e.getMessage());
            logger.error(loggerLine);
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }
}
