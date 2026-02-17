package com.smartcart.ecommerce.modules.category.dtos;

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
