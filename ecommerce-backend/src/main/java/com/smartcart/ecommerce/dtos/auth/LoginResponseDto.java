package com.smartcart.ecommerce.dtos.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponseDto {

    private String email;
    private String name;
    private String accessToken;
    private String role;
}
