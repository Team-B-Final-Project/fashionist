package com.anbit.fashionist.service;


import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.http.ResponseEntity;

import com.anbit.fashionist.domain.dto.*;
import com.anbit.fashionist.helper.PasswordNotMatchException;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.helper.SignInFailException;
import com.anbit.fashionist.helper.WrongOTPException;

public interface AuthService {

    ResponseEntity<?> authenticateUser(SignInRequestDTO signInRequest) throws SignInFailException;

    ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException;

    ResponseEntity<?> forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO) throws ResourceNotFoundException, MessagingException;

    ResponseEntity<?> confirmOTP(ConfirmOTPRequestDTO confirmOTPRequestDTO) throws WrongOTPException, ResourceNotFoundException;

    ResponseEntity<?> resetPassword(UUID token, ResetPasswordRequestDTO resetPasswordRequestDTO) throws PasswordNotMatchException, ResourceNotFoundException;

}