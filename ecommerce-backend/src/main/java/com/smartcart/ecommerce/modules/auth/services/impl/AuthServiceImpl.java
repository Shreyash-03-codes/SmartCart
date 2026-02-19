package com.smartcart.ecommerce.modules.auth.services.impl;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.common.exceptions.*;
import com.smartcart.ecommerce.common.services.EmailService;
import com.smartcart.ecommerce.modules.auth.dtos.*;
import com.smartcart.ecommerce.modules.auth.models.PasswordResetToken;
import com.smartcart.ecommerce.modules.auth.repositories.PasswordResetTokenRepository;
import com.smartcart.ecommerce.modules.auth.repositories.VerificationTokenRepository;
import com.smartcart.ecommerce.modules.auth.models.VerificationToken;
import com.smartcart.ecommerce.modules.auth.services.AuthService;
import com.smartcart.ecommerce.modules.auth.services.JwtService;
import com.smartcart.ecommerce.modules.user.enums.Provider;
import com.smartcart.ecommerce.modules.user.enums.Roles;
import com.smartcart.ecommerce.modules.user.model.User;
import com.smartcart.ecommerce.modules.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final VerificationTokenRepository verificationTokenRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword());
        Authentication authentication=authenticationManager.authenticate(authenticationToken);
        User user=(User)authentication.getPrincipal();

        if(!user.isEnabled()){
            throw new UserNotVerifiedException("The User with email "+user.getEmail()+" is not verified");
        }

        String accessToken=jwtService.generateAccessToken(user);
        String refreshToken=jwtService.generateRefreshToken(user);

        LoginResponseDto loginResponseDto=LoginResponseDto
                .builder()
                .accessToken(accessToken)
                .email(user.getEmail())
                .name(user.getName())
                .role(
                        user.getRoles()
                                .stream()
                                .map(r->r.name())
                                .toList()
                )
                .build();

        Cookie cookie=new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*7);
        httpServletResponse.addCookie(cookie);

        return loginResponseDto;
    }

    @Override
    public GenericResponseDto signup(SignupRequestDto signupRequestDto) {
        if(userRepository.findByEmail(signupRequestDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User with email "+signupRequestDto.getEmail()+" Already exists");
        }

        User userToSave=User
                .builder()
                .email(signupRequestDto.getEmail())
                .name(signupRequestDto.getName())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .enabled(false)
                .roles(Set.of(Roles.USER))
                .provider(Provider.USERNAME_AND_PASSWORD)
                .build();
        User savedUser=userRepository.save(userToSave);

        String token = UUID.randomUUID().toString();
        String link = "http://localhost:4200/auth/verify-email?email=" + savedUser.getEmail() + "&token=" + token;
        String text = "Welcome to SmartCart!\n\n"
                + "Click the link below to verify your email:\n"
                + link + "\n\n"
                + "If you did not create an account, please ignore this email.";

        VerificationToken verificationToken=VerificationToken
                .builder()
                .token(token)
                .expiryDatetime(LocalDateTime.now().plusHours(24))
                .email(savedUser.getEmail())
                .build();

        VerificationToken savedToken=verificationTokenRepository.save(verificationToken);


        emailService.sendEmail(savedUser.getEmail(), "Verify your email", text);

        GenericResponseDto genericResponseDto=GenericResponseDto
                .builder()
                .message("User Registered successfully, Please verify we have sent the email.")
                .build();

        return genericResponseDto;
    }

    @Override
    public GenericResponseDto verifyEmail(String email, String token) {

        VerificationToken verificationToken=verificationTokenRepository.findByEmail(email).orElseThrow(()->new VerificationTokenNotFoundException("verification token is not found for email "+email));
        if(!token.equals(verificationToken.getToken())){
            throw new InValidVerificationTokenException("verification token is Invalid for email "+email);
        }
        if(verificationToken.getExpiryDatetime().isBefore(LocalDateTime.now())){
            throw new InValidVerificationTokenException("Verification token is expired");
        }
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserEmailNotFoundException("user email not found with "+email));
        user.enableUser();
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        return GenericResponseDto
                .builder()
                .message("User verified with email "+email)
                .build();
    }


    @Override
    public RefreshTokenResponseDto refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String refreshToken=getRefreshToken(httpServletRequest);
        if(refreshToken == null || refreshToken.isBlank()){
            throw new InValidRefreshTokenException("The token is null or empty");
        }
        Long id=jwtService.extractIdFromRefreshToken(refreshToken);
        User user=userRepository.findById(id).orElseThrow(()->new UserIdNotFoundException("User with id "+id+" not found"));
        if(!jwtService.isRefreshTokenValid(refreshToken,user)){
            throw new InValidRefreshTokenException("The token is Invalid");
        }
        String accessToken=jwtService.generateAccessToken(user);
        String newRefreshToken=jwtService.generateRefreshToken(user);

        RefreshTokenResponseDto refreshTokenResponseDto=RefreshTokenResponseDto
                .builder()
                .accessToken(accessToken)
                .email(user.getEmail())
                .name(user.getName())
                .role(
                        user.getRoles()
                                .stream()
                                .map(r->r.name())
                                .toList()
                )
                .build();

        Cookie cookie=new Cookie("refreshToken",newRefreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7*24*60*60);
        httpServletResponse.addCookie(cookie);

        return refreshTokenResponseDto;
    }


    @Override
    public GenericResponseDto forgetPassword(String email) {

        User user=userRepository.findByEmail(email).orElseThrow(()->new UserEmailNotFoundException("user with email "+email+" not found"));
        String token=UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken=PasswordResetToken
                .builder()
                .email(email)
                .tokenExpiry(LocalDateTime.now().plusHours(24))
                .token(token)
                .build();

        passwordResetTokenRepository.save(passwordResetToken);

        String link = "http://localhost:4200/auth/reset-password?token=" + token + "&email=" + email;
        String text = "Hi " + user.getName() + ",\n\n"
                + "Click the link below to reset your password:\n"
                + link + "\n\n"
                + "If you did not request a password reset, please ignore this email.";

        emailService.sendEmail(email, "Reset Your Password", text);

        return GenericResponseDto.builder()
                .message("Password reset link sent to your email")
                .build();
    }

    @Override
    public GenericResponseDto resetPassword( ResetPasswordDto resetPasswordDto) {

        PasswordResetToken passwordResetToken=passwordResetTokenRepository.findByEmail(resetPasswordDto.getEmail()).orElseThrow(()->new UserEmailNotFoundException("User with email "+resetPasswordDto.getEmail()+" not found"));

        if(!passwordResetToken.getToken().equals(resetPasswordDto.getToken())){
            throw new InValidPasswordResetTokenException("Invalid password reset token");
        }

        if(passwordResetToken.getTokenExpiry().isBefore(LocalDateTime.now())){
            throw new InValidPasswordResetTokenException("Token is expired");
        }

        User user=userRepository.findByEmail(resetPasswordDto.getEmail()).orElseThrow(()->new UserEmailNotFoundException("user with email "+resetPasswordDto.getEmail()+" not found"));
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        userRepository.save(user);
        passwordResetTokenRepository.delete(passwordResetToken);
        return GenericResponseDto
                .builder()
                .message("Password reset successfully.")
                .build();
    }


    private String getRefreshToken(HttpServletRequest httpServletRequest){
        Cookie[]cookies=httpServletRequest.getCookies();
        return Arrays.stream(cookies)
                .filter(c->c.getName().equals("refreshToken"))
                .findFirst()
                .get()
                .getValue();
    }
}
