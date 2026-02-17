package com.smartcart.ecommerce.modules.orderitem.dtos;

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
