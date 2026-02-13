package com.smartcart.ecommerce.dtos.category;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetCategoryDto {
    private Long id;
    private String name;
    private String description;
    private String imagePath;
}
