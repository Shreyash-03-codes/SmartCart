package com.smartcart.ecommerce.modules.cart.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateCartItemDto {
    private Long productId;
    private Integer quantity;
}
