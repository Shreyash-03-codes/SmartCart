package com.smartcart.ecommerce.modules.auth.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefreshTokenResponseDto {
    private String email;
    private String name;
    private String accessToken;
    private String role;
}
