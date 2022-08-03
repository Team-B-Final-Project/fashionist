package com.anbit.fashionist.controller;

import com.anbit.fashionist.domain.dto.LoginRequest;
import com.anbit.fashionist.domain.dto.SignUpRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthServiceImpl authServiceImpl;

    /***
     * Endpoint to sign in and get the token for accessing the CRUD system
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) throws ResourceNotFoundException
    {
        return authServiceImpl.authenticateUser(loginRequest);
    }

    /***
     * Endpoint for register user 
     * @param signUpRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     */
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) throws ResourceAlreadyExistException {
        return authServiceImpl.registerUser(signUpRequestDTO);
    }
}
