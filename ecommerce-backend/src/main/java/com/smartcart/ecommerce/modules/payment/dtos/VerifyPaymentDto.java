package com.smartcart.ecommerce.modules.payment.dtos;

import lombok.Data;

@Data
public class VerifyPaymentDto {

    private String razorPayOrderId;
    private String razorPayPaymentId;
    private String razorPaySignature;
}
