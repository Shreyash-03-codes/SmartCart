package com.smartcart.ecommerce.modules.auth.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResetPasswordDto {
    private String token;
    private String email;
    private String newPassword;
}
