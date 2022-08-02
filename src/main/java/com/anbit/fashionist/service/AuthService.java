package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.dto.LoginRequest;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest signinRequest) throws ResourceNotFoundException;
}
