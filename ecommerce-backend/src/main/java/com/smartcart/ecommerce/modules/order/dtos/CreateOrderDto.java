package com.smartcart.ecommerce.modules.order.dtos;

import com.smartcart.ecommerce.modules.orderitem.dtos.CreateOrderItemDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateOrderDto {
    private List<CreateOrderItemDto> orderItems;
}
