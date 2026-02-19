package com.smartcart.ecommerce.common.advices;

import com.smartcart.ecommerce.common.dtos.error.ErrorResponse;
import com.smartcart.ecommerce.common.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<ErrorResponse> userNotVerifiedException(UserNotVerifiedException userNotVerifiedException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse
                        .builder()
                        .status(HttpStatus.UNAUTHORIZED.name())
                        .description(userNotVerifiedException.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentialsException(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse
                                .builder()
                                .status(HttpStatus.UNAUTHORIZED.name())
                                .description("Invalid Username or Password")
                                .build()
                );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> authenticationException(AuthenticationException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse
                                .builder()
                                .status(HttpStatus.UNAUTHORIZED.name())
                                .description(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(InValidRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> inValidRefreshTokenException(InValidRefreshTokenException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse
                                .builder()
                                .status(HttpStatus.BAD_REQUEST.name())
                                .description(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> userIdNotFoundException(UserIdNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse
                                .builder()
                                .status(HttpStatus.BAD_REQUEST.name())
                                .description(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> userAlreadyExistsException(UserAlreadyExistsException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse
                                .builder()
                                .status(HttpStatus.BAD_REQUEST.name())
                                .description(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(VerificationTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> verificationTokenNotFoundException(VerificationTokenNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse
                                .builder()
                                .status(HttpStatus.BAD_REQUEST.name())
                                .description(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(InValidVerificationTokenException.class)
    public ResponseEntity<ErrorResponse> inValidVerificationTokenException(InValidVerificationTokenException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse
                                .builder()
                                .status(HttpStatus.BAD_REQUEST.name())
                                .description(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> userEmailNotFoundException(UserEmailNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse
                                .builder()
                                .status(HttpStatus.BAD_REQUEST.name())
                                .description(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(InValidPasswordResetTokenException.class)
    public ResponseEntity<ErrorResponse> inValidPasswordResetTokenException(InValidPasswordResetTokenException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse
                                .builder()
                                .status(HttpStatus.BAD_REQUEST.name())
                                .description(ex.getMessage())
                                .build()
                );
    }

}
