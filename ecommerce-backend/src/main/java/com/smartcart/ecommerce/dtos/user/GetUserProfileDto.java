package com.smartcart.ecommerce.dtos.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetUserProfileDto {
    private String name;
    private String email;
    private String profilePhotoPath;
}
