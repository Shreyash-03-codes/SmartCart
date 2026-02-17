package com.smartcart.ecommerce.modules.auth.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignupRequestDto {
    private String name;
    private String email;
    private String password;
    private MultipartFile profilePhoto;
}
