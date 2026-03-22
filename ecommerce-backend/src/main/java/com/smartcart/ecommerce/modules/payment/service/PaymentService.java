package com.smartcart.ecommerce.modules.payment.service;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.payment.dtos.CreatePaymentDto;
import com.smartcart.ecommerce.modules.payment.dtos.GetPaymentDto;
import com.smartcart.ecommerce.modules.payment.dtos.RazorpayOrderDto;
import com.smartcart.ecommerce.modules.payment.dtos.VerifyPaymentDto;

public interface PaymentService {
    RazorpayOrderDto createOrder(CreatePaymentDto createPaymentDto);

    GetPaymentDto verify(VerifyPaymentDto verifyPaymentDto);

    GenericResponseDto handleWebHook(String payload, String signature);

    GetPaymentDto getPayment(Long paymentId);
}
