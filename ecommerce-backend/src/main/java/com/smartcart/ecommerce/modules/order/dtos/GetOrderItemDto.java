package com.smartcart.ecommerce.modules.order.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class GetOrderItemDto {

    private Long productId;

    private String productName;

    private List<String> images;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal amount;
}
