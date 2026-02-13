package com.smartcart.ecommerce.dtos.cart;

import com.smartcart.ecommerce.dtos.cartitem.GetCartItemDto;
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
