package com.smartcart.ecommerce.dtos.cart;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateCartItemDto {
    private Integer quantity;
}
