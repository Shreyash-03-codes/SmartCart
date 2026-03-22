package com.smartcart.ecommerce.modules.payment.service.impl;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.common.exceptions.OrderNotPresentException;
import com.smartcart.ecommerce.modules.order.model.Order;
import com.smartcart.ecommerce.modules.order.repository.OrderRepository;
import com.smartcart.ecommerce.modules.payment.dtos.CreatePaymentDto;
import com.smartcart.ecommerce.modules.payment.dtos.GetPaymentDto;
import com.smartcart.ecommerce.modules.payment.dtos.RazorpayOrderDto;
import com.smartcart.ecommerce.modules.payment.dtos.VerifyPaymentDto;
import com.smartcart.ecommerce.modules.payment.enums.PaymentStatus;
import com.smartcart.ecommerce.modules.payment.model.Payment;
import com.smartcart.ecommerce.modules.payment.repository.PaymentRepository;
import com.smartcart.ecommerce.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    @Override
    public RazorpayOrderDto createOrder(CreatePaymentDto createPaymentDto) {
        try{

            Order order=orderRepository.findById(createPaymentDto.getOrderId()).orElseThrow(()->new OrderNotPresentException("Order not found WIth id "+createPaymentDto.getOrderId()));

            RazorpayClient razorpayClient=new RazorpayClient(keyId,keySecret);

            long amountInPaise=order.getAmount().multiply(BigDecimal.valueOf(100)).longValue();

            JSONObject orderRequest=new JSONObject();
            orderRequest.put("amount",amountInPaise);
            orderRequest.put("currency","INR");
            orderRequest.put("receipt","smartcart_order_"+order.getId());
            orderRequest.put("payment_capture",1);

            com.razorpay.Order razorPayOrder=razorpayClient.orders.create(orderRequest);

            Payment payment=Payment
                    .builder()
                    .order(order)
                    .amount(order.getAmount())
                    .paymentMethod(createPaymentDto.getPaymentMethod())
                    .paymentStatus(PaymentStatus.PENDING)
                    .transactionId(razorPayOrder.get("id"))
                    .build();

            paymentRepository.save(payment);

            return RazorpayOrderDto
                    .builder()
                    .razorPayOrderId(razorPayOrder.get("id"))
                    .currency("INR")
                    .amount(order.getAmount())
                    .keyId(keyId)
                    .build();
        }
        catch (RazorpayException e){
            throw new RuntimeException("Failed to create razorpay order "+e.getMessage());
        }
    }

    @Override
    public GetPaymentDto verify(VerifyPaymentDto verifyPaymentDto) {

        try{

            JSONObject attributes=new JSONObject();
            attributes.put("razorpay_order_id",verifyPaymentDto.getRazorPayOrderId());
            attributes.put("razorpay_payment_id",verifyPaymentDto.getRazorPayPaymentId());
            attributes.put("razorpay_signature",verifyPaymentDto.getRazorPaySignature());

            boolean isValid= Utils.verifyPaymentSignature(attributes,keySecret);

            Payment payment=paymentRepository.findByTransactionId(verifyPaymentDto.getRazorPayOrderId()).orElseThrow(()->new RuntimeException("Payment not found with "+verifyPaymentDto.getRazorPayOrderId()));

            if(isValid){

                payment.setPaymentStatus(PaymentStatus.SUCCESS);
                payment.setTransactionId(verifyPaymentDto.getRazorPayPaymentId());

            }
            else{
                payment.setPaymentStatus(PaymentStatus.FAILED);
            }

            paymentRepository.save(payment);

            return mapToPayment(payment);

        }
        catch (RazorpayException e){

            throw new RuntimeException("Payment verification failed. "+e.getMessage());
        }

    }

    @Override
    public GenericResponseDto handleWebHook(String payload, String signature) {
        return null;
    }

    @Override
    public GetPaymentDto getPayment(Long paymentId) {

        Payment payment=paymentRepository.findById(paymentId).orElseThrow(()->new RuntimeException("Payment not found with id "+paymentId));
        return mapToPayment(payment);
    }

    private GetPaymentDto mapToPayment(Payment payment){
        return GetPaymentDto
                .builder()
                .paymentStatus(payment.getPaymentStatus())
                .paymentMethod(payment.getPaymentMethod())
                .paymentId(payment.getId())
                .amount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .orderId(payment.getOrder().getId())
                .build();
    }
}
