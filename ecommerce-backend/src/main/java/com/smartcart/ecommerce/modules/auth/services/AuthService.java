package com.smartcart.ecommerce.modules.auth.services;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.auth.dtos.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse);

    GenericResponseDto signup(SignupRequestDto signupRequestDto);

    GenericResponseDto verifyEmail(String email, String token);

    RefreshTokenResponseDto refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    GenericResponseDto forgetPassword(String email);

    GenericResponseDto resetPassword( ResetPasswordDto resetPasswordDto);
}
