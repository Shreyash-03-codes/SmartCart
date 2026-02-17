package com.smartcart.ecommerce.modules.user.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserProfileDto {
    private String name;
    private String profilePhotoPath;
}
