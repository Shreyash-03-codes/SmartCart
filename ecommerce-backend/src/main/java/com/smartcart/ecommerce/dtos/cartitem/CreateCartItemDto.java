package com.smartcart.ecommerce.dtos.cartitem;

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
