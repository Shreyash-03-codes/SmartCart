package com.smartcart.ecommerce.dtos.payment;

import com.smartcart.ecommerce.enums.PaymentStatus;
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
