package com.anbit.fashionist.service;

import javax.mail.MessagingException;
import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.ResetPasswordToken;
import com.anbit.fashionist.domain.dao.Role;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.config.JwtUtils;
import com.anbit.fashionist.constant.EErrorCode;
import com.anbit.fashionist.constant.ERole;
import com.anbit.fashionist.domain.dto.*;
import com.anbit.fashionist.helper.PasswordNotMatchException;
import com.anbit.fashionist.helper.WrongOTPException;
import com.anbit.fashionist.repository.ResetPasswordTokenRepository;
import com.anbit.fashionist.util.EmailSender;
import org.springframework.beans.factory.annotation.Value;

import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.RoleRepository;
import com.anbit.fashionist.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.helper.ResourceAlreadyExistException;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

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

    @Value("${server.port}")
    String port;

    @Value("${com.anbit.fashionist.name}")
    String projectName;

    @Value("${com.anbit.fashionist.team}")
    String projectTeam;

    private final HttpHeaders headers = new HttpHeaders();
    @Override
    public ResponseEntity<?> authenticateUser(SignInRequestDTO loginRequest) throws ResourceNotFoundException {

        try {
            Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
            Boolean isPasswordCorrect = encoder.matches(loginRequest.getPassword(), user.get().getPassword());
            if (Boolean.FALSE.equals(userRepository.existsByUsername(loginRequest.getUsername()))) {
                throw new ResourceNotFoundException("Username or password is wrong!");
            }
            if (Boolean.FALSE.equals(isPasswordCorrect)) {
                throw new ResourceNotFoundException("Username or password is wrong!");
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "Successfully login!", new JwtResponseDTO(jwt, userDetails.getUsername(), roles));
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequestDTO) throws ResourceAlreadyExistException, ResourceNotFoundException{
        try {
            if (userRepository.existsByUsername(signUpRequestDTO.getUsername())) {
                throw new ResourceAlreadyExistException("Username already taken!");
            }
            if (userRepository.existsByEmail(signUpRequestDTO.getEmail())) {
                throw new ResourceAlreadyExistException("Email already exists!");
            }
            if (userRepository.existsByPhoneNumber(signUpRequestDTO.getPhoneNumber())) {
                throw new ResourceAlreadyExistException("Phone already exists!");
            }
            User user = User.builder()
                    .firstName(signUpRequestDTO.getFirstName())
                    .lastName(signUpRequestDTO.getLastName())
                    .username(signUpRequestDTO.getUsername())
                    .email(signUpRequestDTO.getEmail())
                    .phoneNumber(signUpRequestDTO.getPhoneNumber())
                    .password(encoder.encode(signUpRequestDTO.getPassword()))
                    .build();
            Set<Role> roles = new HashSet<>();
            Role customer = roleRepository.findByName(ERole.ROLE_CUSTOMER).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
            roles.add(customer);
            user.setRoles(roles);
            userRepository.save(user);
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "You have been registered successfully!", null);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO) throws ResourceNotFoundException, MessagingException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            if (!userRepository.existsByEmailAddress(forgetPasswordRequestDTO.getEmailAddress())) {
                throw new ResourceNotFoundException("User with email " + forgetPasswordRequestDTO.getEmailAddress() + " does not exist!");
            }
            String emailAddress = forgetPasswordRequestDTO.getEmailAddress();
            int otp = otpService.generateOTP(emailAddress);
            emailSender.sendOtpMessage(emailAddress, "BIOSKOP6 Reset Password Request", String.valueOf(otp));
//            logger.info("--------------------------");
//            logger.info("Forget Password " + emailAddress);
//            logger.info("--------------------------");
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "OTP has been sent to your email!", null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        } catch (MessagingException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());

        }
    }

    @Override
    public ResponseEntity<?> confirmOTP(ConfirmOTPRequestDTO confirmOTPRequestDTO) throws WrongOTPException, ResourceNotFoundException{
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            if (otpService.getOTP(confirmOTPRequestDTO.getEmailAddress()) == 0) {
                throw new ResourceNotFoundException("You have not generated OTP!");
            }else if (otpService.getOTP(confirmOTPRequestDTO.getEmailAddress()) != confirmOTPRequestDTO.getOtp()) {
                throw new WrongOTPException("Wrong OTP!");
            }
            resetPasswordTokenRepository.deleteByEmailAddress(confirmOTPRequestDTO.getEmailAddress());
            ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.save(ResetPasswordToken.builder().emailAddress(confirmOTPRequestDTO.getEmailAddress()).build());
//            logger.info("--------------------------");
//            logger.info("Confirm OTP " + resetPasswordToken);
//            logger.info("--------------------------");
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "OTP has been confirmed!", null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        } catch (WrongOTPException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> resetPassword(UUID token , ResetPasswordRequestDTO resetPasswordRequestDTO) throws PasswordNotMatchException, ResourceNotFoundException {
        headers.set("APP-NAME", projectName + "-API " + projectTeam);
        try {
            if (!resetPasswordRequestDTO.getNewPassword().equals(resetPasswordRequestDTO.getConfirmPassword())) {
                throw new PasswordNotMatchException("Password not match!");
            }
            Optional<ResetPasswordToken> resetPasswordToken = resetPasswordTokenRepository.findByToken(token);
            if (resetPasswordToken.isEmpty()) {
                throw new ResourceNotFoundException("Token is not valid!");
            }
            Optional<User> optionalUser = userRepository.findByEmailAddress(resetPasswordToken.get().getEmailAddress());
            User user = optionalUser.get();
            user.setPassword(encoder.encode(resetPasswordRequestDTO.getNewPassword()));
            userRepository.save(user);
            resetPasswordTokenRepository.deleteByEmailAddress(resetPasswordToken.get().getEmailAddress());
//            logger.info("--------------------------");
//            logger.info("Reset Password " + user);
//            logger.info("--------------------------");
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "Password has been reset successfully!", null);
        } catch (PasswordNotMatchException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }
}
