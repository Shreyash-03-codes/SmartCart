package com.smartcart.ecommerce.modules.auth.controller;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.auth.dtos.*;
import com.smartcart.ecommerce.modules.auth.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Validated @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto,httpServletResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponseDto> signup(@Validated @RequestBody SignupRequestDto signupRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(signupRequestDto));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<GenericResponseDto> verifyEmail(@RequestParam("email") String email,@RequestParam("token") String token){
        return ResponseEntity.status(HttpStatus.OK).body(authService.verifyEmail(email,token));
    }

    @GetMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refresh(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        return ResponseEntity.status(HttpStatus.OK).body(authService.refresh(httpServletRequest,httpServletResponse));
    }

    @GetMapping("/forget-password")
    public ResponseEntity<GenericResponseDto> forgetPassword(@RequestParam("email") String email){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        authService.forgetPassword(email)
                );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<GenericResponseDto> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        authService.resetPassword(resetPasswordDto)
                );
    }
}
