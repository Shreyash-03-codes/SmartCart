package com.smartcart.ecommerce.dtos.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignupRequestDto {
    private String name;
    private String email;
    private String password;
}
