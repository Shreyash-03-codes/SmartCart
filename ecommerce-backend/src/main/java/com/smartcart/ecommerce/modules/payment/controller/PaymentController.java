package com.smartcart.ecommerce.modules.payment.controller;


import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.payment.dtos.CreatePaymentDto;
import com.smartcart.ecommerce.modules.payment.dtos.GetPaymentDto;
import com.smartcart.ecommerce.modules.payment.dtos.RazorpayOrderDto;
import com.smartcart.ecommerce.modules.payment.dtos.VerifyPaymentDto;
import com.smartcart.ecommerce.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<RazorpayOrderDto> createOrder(@RequestBody CreatePaymentDto createPaymentDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createOrder(createPaymentDto));
    }

    @PostMapping("/verify")
    public ResponseEntity<GetPaymentDto> verify(@RequestBody VerifyPaymentDto verifyPaymentDto){
        return ResponseEntity.ok(paymentService.verify(verifyPaymentDto));
    }

    @PostMapping("/webhook")
    public ResponseEntity<GenericResponseDto> webhook(@RequestBody String payload, @RequestHeader("X-Razorpay-Signature") String signature){
        return ResponseEntity.ok(paymentService.handleWebHook(payload,signature));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<GetPaymentDto> getPayment(@PathVariable Long paymentId){
        return ResponseEntity.ok(paymentService.getPayment(paymentId));
    }
}
