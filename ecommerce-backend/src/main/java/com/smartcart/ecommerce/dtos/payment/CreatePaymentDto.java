package com.smartcart.ecommerce.dtos.payment;

import com.smartcart.ecommerce.enums.PaymentMethod;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreatePaymentDto {
    private Long orderId;
    private PaymentMethod paymentMethod;
}
