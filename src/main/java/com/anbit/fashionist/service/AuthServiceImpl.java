package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.ProfilePicture;
import com.anbit.fashionist.domain.dao.Role;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.config.JwtUtils;
import com.anbit.fashionist.constant.ERole;
import com.anbit.fashionist.constant.ESex;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.helper.SignInFailException;
import com.anbit.fashionist.repository.RoleRepository;
import com.anbit.fashionist.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.controller.AuthController;
import com.anbit.fashionist.domain.dao.ResetPasswordToken;
import com.anbit.fashionist.domain.dto.*;
import com.anbit.fashionist.helper.PasswordNotMatchException;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.WrongOTPException;
import com.anbit.fashionist.repository.ResetPasswordTokenRepository;
import com.anbit.fashionist.util.EmailSender;


@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ProfileService profileService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    OTPServiceImpl otpService;

    @Autowired
    EmailSender emailSender;

    @Value("${com.anbit.fashionist.domain}")
    String domain;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private static final String loggerLine = "---------------------------------------";

    @Override
    public ResponseEntity<?> authenticateUser(SignInRequestDTO loginRequest) throws SignInFailException {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new SignInFailException("Username or password is wrong!"));
        Boolean isPasswordCorrect = encoder.matches(loginRequest.getPassword(), user.getPassword());
        if (Boolean.FALSE.equals(userRepository.existsByUsername(loginRequest.getUsername()))) {
            throw new SignInFailException("Username or password is wrong!");
        }
        if (Boolean.FALSE.equals(isPasswordCorrect)) {
            throw new SignInFailException("Username or password is wrong!");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        logger.info(loggerLine);
        logger.info("Aunthenticate User " + authentication);
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully login!", new JwtResponseDTO(jwt, userDetails.getUsername(), roles));
    }

    @Override
    public ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException{
        if (userRepository.existsByUsername(signUpRequestDTO.getUsername())) {
            throw new ResourceAlreadyExistException("Username already taken!");
        }
        if (userRepository.existsByEmail(signUpRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistException("Email already exists!");
        }
        if (userRepository.existsByPhoneNumber(signUpRequestDTO.getPhoneNumber())) {
            throw new ResourceAlreadyExistException("Phone already exists!");
        }
        ProfilePicture profilePicture = profileService.getDefaultProfilePicture();
        User user = User.builder()
                .profilePicture(profilePicture)
                .firstName(signUpRequestDTO.getFirstName())
                .lastName(signUpRequestDTO.getLastName())
                .sex(ESex.valueOf(signUpRequestDTO.getSex().toUpperCase()).getName())
                .username(signUpRequestDTO.getUsername())
                .email(signUpRequestDTO.getEmail())
                .phoneNumber(signUpRequestDTO.getPhoneNumber())
                .password(encoder.encode(signUpRequestDTO.getPassword()))
                .build();
        Set<Role> roles = new HashSet<>();
        com.anbit.fashionist.domain.dao.Role customer = roleRepository.findByName(ERole.ROLE_CUSTOMER).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
        roles.add(customer);
        user.setRoles(roles);
        userRepository.save(user);
        logger.info(loggerLine);
        logger.info("Register User " + user);
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "You have been registered successfully!", null);
    }

    @Override
    public ResponseEntity<?> forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO) throws ResourceNotFoundException, MessagingException {
        if (!userRepository.existsByEmail(forgetPasswordRequestDTO.getEmailAddress())) {
            throw new ResourceNotFoundException("User with email " + forgetPasswordRequestDTO.getEmailAddress() + " does not exist!");
        }
        String emailAddress = forgetPasswordRequestDTO.getEmailAddress();
        int otp = otpService.generateOTP(emailAddress);
        emailSender.sendOtpMessage(emailAddress, "FASHIONIST Reset Password Request", String.valueOf(otp));
        logger.info(loggerLine);
        logger.info("Forget Password " + emailAddress);
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "OTP has been sent to your email!", null);
    }

    @Override
    public ResponseEntity<?> confirmOTP(ConfirmOTPRequestDTO confirmOTPRequestDTO) throws WrongOTPException, ResourceNotFoundException{
        if (otpService.getOTP(confirmOTPRequestDTO.getEmailAddress()) == 0) {
            throw new ResourceNotFoundException("You have not generated OTP!");
        }else if (otpService.getOTP(confirmOTPRequestDTO.getEmailAddress()) != confirmOTPRequestDTO.getOtp()) {
            throw new WrongOTPException("Wrong OTP!");
        }
        resetPasswordTokenRepository.deleteByEmailAddress(confirmOTPRequestDTO.getEmailAddress());
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.save(ResetPasswordToken.builder().emailAddress(confirmOTPRequestDTO.getEmailAddress()).build());
        logger.info(loggerLine);
        logger.info("Confirm OTP " + resetPasswordToken);
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "OTP has been confirmed!", null);
    }

    @Override
    public ResponseEntity<?> resetPassword(UUID token , ResetPasswordRequestDTO resetPasswordRequestDTO) throws PasswordNotMatchException, ResourceNotFoundException {
        if (!resetPasswordRequestDTO.getNewPassword().equals(resetPasswordRequestDTO.getConfirmPassword())) {
            throw new PasswordNotMatchException("Password not match!");
        }
        Optional<ResetPasswordToken> resetPasswordToken = resetPasswordTokenRepository.findByToken(token);
        if (resetPasswordToken.isEmpty()) {
            throw new ResourceNotFoundException("Token is not valid!");
        }
        Optional<User> optionalUser = userRepository.findByEmail(resetPasswordToken.get().getEmailAddress());
        User user = optionalUser.get();
        user.setPassword(encoder.encode(resetPasswordRequestDTO.getNewPassword()));
        userRepository.save(user);
        resetPasswordTokenRepository.deleteByEmailAddress(resetPasswordToken.get().getEmailAddress());
        logger.info(loggerLine);
        logger.info("Reset Password " + user);
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Password has been reset successfully!", null);
    }

    @Override
    public User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getReferenceById(userDetails.getId());
        return user;
    }
}
