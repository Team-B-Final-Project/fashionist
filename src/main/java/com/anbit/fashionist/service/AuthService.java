package com.anbit.fashionist.service;


import com.anbit.fashionist.domain.dto.*;

import com.anbit.fashionist.helper.PasswordNotMatchException;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.helper.WrongOTPException;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.util.UUID;

public interface AuthService {

    ResponseEntity<?> authenticateUser(SignInRequestDTO signInRequest) throws ResourceNotFoundException;

    ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException;

    ResponseEntity<?> forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO) throws ResourceNotFoundException, MessagingException;

    ResponseEntity<?> confirmOTP(ConfirmOTPRequestDTO confirmOTPRequestDTO) throws WrongOTPException, ResourceNotFoundException;

    ResponseEntity<?> resetPassword(UUID token, ResetPasswordRequestDTO resetPasswordRequestDTO) throws PasswordNotMatchException, ResourceNotFoundException;

}