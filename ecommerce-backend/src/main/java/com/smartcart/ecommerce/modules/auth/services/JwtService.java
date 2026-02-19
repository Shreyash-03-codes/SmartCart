package com.smartcart.ecommerce.modules.auth.services;

import com.smartcart.ecommerce.modules.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsernameFromAccessToken(String accessToken);

    boolean isAccessTokenValid(String accessToken, UserDetails userDetails);

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    Long extractIdFromRefreshToken(String refreshToken);

    boolean isRefreshTokenValid(String refreshToken, User user);
}
