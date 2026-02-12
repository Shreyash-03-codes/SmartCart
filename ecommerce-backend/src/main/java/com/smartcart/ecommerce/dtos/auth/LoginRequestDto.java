package com.smartcart.ecommerce.dtos.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequestDto {
    private String email;
    private String password;
}
