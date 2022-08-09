package com.anbit.fashionist.controller;

import com.anbit.fashionist.domain.dto.SignInRequestDTO;
import com.anbit.fashionist.domain.dto.SignUpRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.AuthServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "1. Auth Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthServiceImpl authServiceImpl;

    /***
     * Sign in and get the token for access
     * @param loginRequest
     * @return
     */
    @Operation(summary = "Sign in and get the token for access")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody SignInRequestDTO signInRequest) throws ResourceNotFoundException
    {
        return authServiceImpl.authenticateUser(signInRequest);
    }

    /***
     * Register for user
     * @param signUpRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     */
    @Operation(summary = "Register for user")
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) throws ResourceAlreadyExistException {
        return authServiceImpl.registerUser(signUpRequestDTO);
    }
}
