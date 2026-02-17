package com.smartcart.ecommerce.modules.payment.dtos;

import com.smartcart.ecommerce.modules.payment.enums.PaymentMethod;
import com.smartcart.ecommerce.modules.payment.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetPaymentDto {
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private Long paymentId;
    private Long orderId;
    private String transactionId;
}
