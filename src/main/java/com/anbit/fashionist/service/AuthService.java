package com.anbit.fashionist.service;


import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.SignInRequestDTO;

import com.anbit.fashionist.domain.dto.SignUpRequestDTO;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.helper.SignInFailException;

import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> authenticateUser(SignInRequestDTO signInRequest) throws SignInFailException;

    ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException;

    User getCurrentUser();
}