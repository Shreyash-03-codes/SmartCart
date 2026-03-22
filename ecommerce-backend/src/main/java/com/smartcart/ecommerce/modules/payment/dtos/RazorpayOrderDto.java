package com.smartcart.ecommerce.modules.payment.dtos;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RazorpayOrderDto {
    private String razorPayOrderId;
    private BigDecimal amount;
    private String currency;
    private String keyId;
}
