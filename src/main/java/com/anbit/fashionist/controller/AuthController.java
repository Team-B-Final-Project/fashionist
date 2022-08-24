package com.anbit.fashionist.controller;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.anbit.fashionist.domain.dto.*;
import com.anbit.fashionist.helper.PasswordNotMatchException;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.helper.SignInFailException;
import com.anbit.fashionist.helper.WrongOTPException;
import com.anbit.fashionist.service.AuthServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "1. Auth Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthServiceImpl authServiceImpl;

    /***
     * Sign in and get the token for access
     * @param signInRequest
     * @return
     * @throws SignInFailException
     */
    @Operation(summary = "Sign in and get the token for access")
    @PostMapping("/signin")

    public ResponseEntity<?> authenticate(@Valid @RequestBody SignInRequestDTO signInRequest) throws SignInFailException {
        return authServiceImpl.authenticateUser(signInRequest);
    }

    /***
     * Register for user
     * @param signUpRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Register for user")
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException {
        return authServiceImpl.registerUser(signUpRequestDTO);
    }

    /***
     * Forget password
     * @param forgetPasswordRequestDTO
     * @return
     * @throws ResourceNotFoundException
     * @throws MessagingException
     */
    @Operation(summary = "Get OTP to reset password")
    @PostMapping("/forgetpassword")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequestDTO forgetPasswordRequestDTO) throws ResourceNotFoundException, MessagingException {
        return authServiceImpl.forgetPassword(forgetPasswordRequestDTO);
    }

    /***
     * Confirm OTP
     * @param confirmOTPRequestDTO
     * @return
     * @throws WrongOTPException
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Confirm OTP to get the token to reset the password")
    @PostMapping("/confirm_otp")
    public ResponseEntity<?> confirmOTP(@Valid @RequestBody ConfirmOTPRequestDTO confirmOTPRequestDTO) throws WrongOTPException, ResourceNotFoundException {
        return authServiceImpl.confirmOTP(confirmOTPRequestDTO);
    }

    /***
     * Reset password
     * @param token
     * @param resetPasswordRequestDTO
     * @return
     * @throws PasswordNotMatchException
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Reset the password")
    @PatchMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") UUID token, @Valid @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) throws PasswordNotMatchException, ResourceNotFoundException {
        return authServiceImpl.resetPassword(token, resetPasswordRequestDTO);
    }
}
