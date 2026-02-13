package com.smartcart.ecommerce.dtos.product;

import com.smartcart.ecommerce.enums.Gender;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Gender gender;
    private Integer stockQuantity;
    private boolean active;
    private List<String> imagesPaths;
    private Long categoryId;
    private String categoryName;
}
