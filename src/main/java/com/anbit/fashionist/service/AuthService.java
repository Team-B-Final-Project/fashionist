package com.anbit.fashionist.service;

import org.springframework.http.ResponseEntity;

import com.anbit.fashionist.domain.dto.SignUpRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;

public interface AuthService {
    ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequestDTO) throws ResourceAlreadyExistException;
}
