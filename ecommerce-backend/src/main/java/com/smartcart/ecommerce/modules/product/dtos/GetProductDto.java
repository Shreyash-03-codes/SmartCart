package com.smartcart.ecommerce.modules.product.dtos;

import com.smartcart.ecommerce.modules.product.enums.Gender;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Gender gender;
    private Integer stockQuantity;
    private boolean active;
    private List<String> images;
    private Long categoryId;
    private String categoryName;
}
