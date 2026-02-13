package com.smartcart.ecommerce.dtos.category;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateCategoryDto {
    private String name;
    private String description;
    private MultipartFile image;
}
