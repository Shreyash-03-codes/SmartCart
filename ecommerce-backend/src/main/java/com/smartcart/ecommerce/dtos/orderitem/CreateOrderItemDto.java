package com.smartcart.ecommerce.dtos.orderitem;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderItemDto {
    private Long productId;
    private Integer quantity;
}
