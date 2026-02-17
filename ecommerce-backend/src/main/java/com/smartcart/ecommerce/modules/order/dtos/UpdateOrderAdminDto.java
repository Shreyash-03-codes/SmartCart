package com.smartcart.ecommerce.modules.order.dtos;

import com.smartcart.ecommerce.modules.order.enums.OrderStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateOrderAdminDto {
    private OrderStatus orderStatus;
}
