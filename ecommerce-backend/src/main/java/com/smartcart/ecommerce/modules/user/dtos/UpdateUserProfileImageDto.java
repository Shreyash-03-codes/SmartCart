package com.smartcart.ecommerce.modules.user.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserProfileImageDto {
    private MultipartFile image;
}
