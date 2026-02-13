package com.smartcart.ecommerce.dtos.orders;

import com.smartcart.ecommerce.dtos.orderitem.GetOrderItemDto;
import com.smartcart.ecommerce.enums.OrderStatus;
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
