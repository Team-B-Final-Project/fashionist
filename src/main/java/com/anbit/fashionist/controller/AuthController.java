package com.anbit.fashionist.controller;

import com.anbit.fashionist.domain.dto.LoginRequest;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class AuthController {

    @Autowired
AuthServiceImpl authServiceImpl;
/***
 * Endpoint to sign in and get the token for accessing the CRUD system
 * @param loginRequest
 * @return
 */
@PostMapping("/sigin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) throws ResourceNotFoundException
    {
        return authServiceImpl.authenticateUser(loginRequest);
    }
}
