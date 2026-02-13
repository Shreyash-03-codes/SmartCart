package com.smartcart.ecommerce.dtos.product;

import com.smartcart.ecommerce.enums.Gender;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Gender gender;
    private Integer stockQuantity;
    private boolean active;
    private List<MultipartFile> images;
    private Long categoryId;
}
