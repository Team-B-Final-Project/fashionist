package com.anbit.fashionist.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.anbit.fashionist.constant.EErrorCode;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceAlreadyExistException;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.helper.SignInFailException;
import com.anbit.fashionist.helper.WrongOTPException;

@ControllerAdvice
public class ErrorExceptionHandlingController extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorExceptionHandlingController.class);

    private static final String loggerLine = "---------------------------------------";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var error = EErrorCode.MALFORMED_JSON;
        return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), error.getCode(), error.getDescription());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var error = EErrorCode.INVALIND_REQUEST;
        List<String> messages = new ArrayList<>();
        ex.getFieldErrors().forEach(err -> {
            messages.add(err.getField() + " " + err.getDefaultMessage());
        });
        logger.error(loggerLine);
        logger.error(error.getDescription());
        logger.error(loggerLine);
        return ResponseHandler.generateValidationErrorResponse(HttpStatus.BAD_REQUEST, messages, error.getCode(), error.getDescription());
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        var error = EErrorCode.NOT_FOUND;
        logger.error(loggerLine);
        logger.error(error.getDescription());
        logger.error(loggerLine);
        return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), error.getCode(), error.getDescription());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e) {
        var error = EErrorCode.FILE_NOT_EXIST;
        logger.error(loggerLine);
        logger.error(error.getDescription());
        logger.error(loggerLine);
        return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), error.getCode(), error.getDescription());
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<?> handleResourceAlreadyExistException(ResourceAlreadyExistException e) {
        var error = EErrorCode.RESOURCE_EXIST;
        logger.error(loggerLine);
        logger.error(error.getDescription());
        logger.error(loggerLine);
        return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), error.getCode(), error.getDescription());
    }

    @ExceptionHandler(SignInFailException.class)
    public ResponseEntity<?> handleSignInFailException(SignInFailException e) {
        var error = EErrorCode.INVALID_CRED;
        logger.error(loggerLine);
        logger.error(error.getDescription());
        logger.error(loggerLine);
        return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), error.getCode(), error.getDescription());
    }

    @ExceptionHandler(WrongOTPException.class)
    public ResponseEntity<?> handleWrongOTPException(WrongOTPException e) {
        var error = EErrorCode.WRONG_OTP;
        logger.error(loggerLine);
        logger.error(error.getDescription());
        logger.error(loggerLine);
        return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), error.getCode(), error.getDescription());
    }
}
