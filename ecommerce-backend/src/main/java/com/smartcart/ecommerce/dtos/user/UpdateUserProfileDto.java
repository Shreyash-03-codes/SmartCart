package com.smartcart.ecommerce.dtos.user;

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
