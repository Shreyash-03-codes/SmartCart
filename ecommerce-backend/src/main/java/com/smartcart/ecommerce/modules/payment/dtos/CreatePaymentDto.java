package com.smartcart.ecommerce.modules.payment.dtos;

import com.smartcart.ecommerce.modules.payment.enums.PaymentMethod;
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
