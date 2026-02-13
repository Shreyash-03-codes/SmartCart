package com.smartcart.ecommerce.dtos.orders;

import com.smartcart.ecommerce.dtos.orderitem.CreateOrderItemDto;
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
