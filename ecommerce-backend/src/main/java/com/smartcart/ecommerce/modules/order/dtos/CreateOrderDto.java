package com.smartcart.ecommerce.modules.order.dtos;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
public class CreateOrderDto {

    private Long paymentId;
}
