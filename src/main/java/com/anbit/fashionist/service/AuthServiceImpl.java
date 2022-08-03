package com.anbit.fashionist.service;

import com.anbit.fashionist.controller.AuthController;
import com.anbit.fashionist.domain.dao.JwtResponse;
import com.anbit.fashionist.config.JwtUtils;
import com.anbit.fashionist.domain.dto.LoginRequest;
import com.anbit.fashionist.entity.User;
import com.anbit.fashionist.entity.UserDetailsImpl;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
//public abstract class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

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

    private final HttpHeaders headers = new HttpHeaders();
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) throws ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
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
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully retrieved user data!", ZonedDateTime.now(), new JwtResponse(jwt, userDetails.getUsername(), roles));
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
            return null;
        } catch (ResourceAlreadyExistException e) {
            return null;
        }
    }
}
