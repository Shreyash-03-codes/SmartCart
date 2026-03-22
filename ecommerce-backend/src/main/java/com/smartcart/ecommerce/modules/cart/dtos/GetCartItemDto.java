package com.smartcart.ecommerce.modules.cart.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCartItemDto {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private List<String> imagePaths;
    private Integer quantity;
}
