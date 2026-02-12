package com.smartcart.ecommerce.dtos.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResendVerificationDto {
    private String email;
}
