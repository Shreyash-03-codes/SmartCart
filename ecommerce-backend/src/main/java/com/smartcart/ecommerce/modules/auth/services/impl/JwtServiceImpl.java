package com.smartcart.ecommerce.modules.auth.services.impl;

import com.smartcart.ecommerce.modules.auth.services.JwtService;
import com.smartcart.ecommerce.modules.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${access.secret}")
    private String ACCESS_SECRET;

    private final Long ACCESS_EXPIRATION=60*10L;

    @Value("${refresh.secret}")
    private String REFRESH_SECRET;

    private final Long REFRESH_EXPIRATION=60*60*24*30L;

    private SecretKey getAccessSecretKey(){
        return Keys
                .hmacShaKeyFor(ACCESS_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getRefreshSecretKey(){
        return Keys
                .hmacShaKeyFor(REFRESH_SECRET.getBytes(StandardCharsets.UTF_8));
    }


    @Override
    public String generateAccessToken(User user) {

        HashMap<String,Object> claims=new HashMap<>();
        claims.put("name",user.getName());
        claims.put("email",user.getEmail());
        claims.put("roles",user.getRoles().stream().map(r->r.name()).toList());
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ACCESS_EXPIRATION))
                .signWith(getAccessSecretKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(User user) {
        HashMap<String,Object> claims=new HashMap<>();
        claims.put("name",user.getName());
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getId()+"")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+REFRESH_EXPIRATION))
                .signWith(getRefreshSecretKey())
                .compact();

    }


    @Override
    public String extractUsernameFromAccessToken(String accessToken) {
        return extractClaimsFromAccessToken(accessToken).getSubject();
    }

    @Override
    public Long extractIdFromRefreshToken(String refreshToken) {
        return Long.parseLong(
                extractClaimsFromRefreshToken(refreshToken).getSubject()
        );
    }

    @Override
    public boolean isAccessTokenValid(String accessToken, UserDetails userDetails) {
        Claims claims=extractClaimsFromAccessToken(accessToken);
        return claims.getExpiration().after(new Date()) && claims.getSubject().equals(userDetails.getUsername());
    }

    @Override
    public boolean isRefreshTokenValid(String refreshToken, User user) {
        Claims claims=extractClaimsFromRefreshToken(refreshToken);
        return claims.getExpiration().after(new Date()) && claims.getSubject().equals(user.getId()+"");
    }

    private Claims extractClaimsFromAccessToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getAccessSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims extractClaimsFromRefreshToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getRefreshSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
