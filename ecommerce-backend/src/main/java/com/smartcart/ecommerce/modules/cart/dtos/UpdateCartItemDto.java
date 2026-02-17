package com.smartcart.ecommerce.modules.cart.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateCartItemDto {
    private Integer quantity;
}
