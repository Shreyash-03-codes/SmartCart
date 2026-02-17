package com.smartcart.ecommerce.modules.auth.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResendVerificationDto {
    private String email;
}
