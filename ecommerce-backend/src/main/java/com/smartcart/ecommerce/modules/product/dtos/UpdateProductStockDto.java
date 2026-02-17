package com.smartcart.ecommerce.modules.product.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateProductStockDto {
    private Long id;
    private Integer stockQuantity;
}
