package com.smartcart.ecommerce.modules.payment.dtos;

import com.smartcart.ecommerce.modules.payment.enums.PaymentStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdatePaymentDto {
    private PaymentStatus paymentStatus;
    private String transactionId;
}
