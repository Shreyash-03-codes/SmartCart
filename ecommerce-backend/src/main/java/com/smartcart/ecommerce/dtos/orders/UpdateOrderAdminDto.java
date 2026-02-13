package com.smartcart.ecommerce.dtos.orders;

import com.smartcart.ecommerce.enums.OrderStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateOrderAdminDto {
    private OrderStatus orderStatus;
}
