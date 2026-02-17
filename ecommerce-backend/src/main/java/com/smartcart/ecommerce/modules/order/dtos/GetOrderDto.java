package com.smartcart.ecommerce.modules.order.dtos;

import com.smartcart.ecommerce.modules.orderitem.dtos.GetOrderItemDto;
import com.smartcart.ecommerce.modules.order.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetOrderDto {
    private Long id;
    private BigDecimal amount;
    private OrderStatus orderStatus;
    private List<GetOrderItemDto> orderItems;
}
