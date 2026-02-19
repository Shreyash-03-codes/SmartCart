package com.smartcart.ecommerce.modules.auth.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponseDto {

    private String email;
    private String name;
    private String accessToken;
    private List<String> role;
}
