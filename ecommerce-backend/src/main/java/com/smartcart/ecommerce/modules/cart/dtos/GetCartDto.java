package com.smartcart.ecommerce.modules.cart.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetCartDto {
    private List<GetCartItemDto> cartItems;
    private BigDecimal totalAmount;
}
