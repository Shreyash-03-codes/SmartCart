package com.smartcart.ecommerce.modules.auth.handlers;

import com.smartcart.ecommerce.modules.auth.services.JwtService;
import com.smartcart.ecommerce.modules.user.enums.Provider;
import com.smartcart.ecommerce.modules.user.enums.Roles;
import com.smartcart.ecommerce.modules.user.model.User;
import com.smartcart.ecommerce.modules.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Set;

@RequiredArgsConstructor
@Component

public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        DefaultOAuth2User auth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String email = auth2User.getAttribute("email");
        String name = auth2User.getAttribute("name");

        User savedUser = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User userToSave = User
                            .builder()
                            .email(email)
                            .name(name)
                            .roles(Set.of(Roles.USER))
                            .provider(Provider.OAUTH2)
                            .enabled(true)
                            .build();
                    return userRepository.save(userToSave);
                });

        String accessToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);

        String redirectUrl = "http://localhost:4200/auth/login?token=" + accessToken
                + "&email=" + email
                + "&name=" +
                 "&role=user";

        response.sendRedirect(redirectUrl);
    }
}