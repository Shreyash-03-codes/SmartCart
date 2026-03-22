import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { PaymentMethod, PaymentStatus, RazorpayOrderResponse, VerifyPaymentRequest } from '../../../model/payment.models';
import { PaymentService } from '../../../core/services/payment/payment.service';
import { JwtService } from '../../../core/services/jwt/jwt.service';


declare var Razorpay: any;

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  // Pass these from parent component or route
  // e.g. <app-checkout [orderId]="1" [orderTotal]="500" />
  @Input() orderId: number = 8;
  @Input() orderTotal: number = 500;
  @Input() customerName: string = '';
  @Input() customerEmail: string = '';
  @Input() customerPhone: string = '';

  // Expose enum to template
  PaymentMethod = PaymentMethod;

  selectedPaymentMethod: PaymentMethod = PaymentMethod.UPI;

  // UI states
  isLoading: boolean = false;
  isSuccess: boolean = false;
  isFailed: boolean = false;
  errorMessage: string = '';
  successTransactionId: string = '';
  paymentId: number | null = null;

  constructor(
    private paymentService: PaymentService,
    private router: Router,
    private jwtService: JwtService
  ) {}

  ngOnInit(): void {

    console.log('token:', localStorage.getItem('token'))
console.log('username:', localStorage.getItem('username'))
console.log('userRole:', localStorage.getItem('userRole'))
    const jwt = this.jwtService.getJwt();
    if (jwt) {
      this.customerName = jwt.username;
    }
  }

  onPayNow(): void {
    this.resetState();
    this.isLoading = true;

    // Step 1: Call backend → create Razorpay order
    this.paymentService.createOrder({
      orderId: this.orderId,
      paymentMethod: this.selectedPaymentMethod
    }).subscribe({
      next: (response) => {
  this.isLoading = false;

  // ADD THIS
  console.log('Full response from backend:', response);
  console.log('keyId:', response.keyId);
  console.log('razorPayOrderId:', response.razorPayOrderId);
  console.log('amount:', response.amount);
  console.log('currency:', response.currency);

  this.openRazorpayPopup(response);
},
      error: (err) => {
        this.isLoading = false;
        this.isFailed = true;
        this.errorMessage = 'Could not initiate payment. Please try again.';
        console.error('Create order failed:', err);
      }
    });
  }

  private openRazorpayPopup(orderResponse: RazorpayOrderResponse): void {
    const options = {
      key: orderResponse.keyId,
      amount: orderResponse.amount * 100,       // paise
      currency: orderResponse.currency,
      name: 'SmartCart',
      description: 'Payment for Order #' + this.orderId,
      order_id: orderResponse.razorPayOrderId,  // exact field from your backend

      // Step 3: Called by Razorpay after user pays successfully
      handler: (razorpayResponse: any) => {
         console.log('razorpay_order_id:', razorpayResponse.razorpay_order_id);
  console.log('razorpay_payment_id:', razorpayResponse.razorpay_payment_id);
  console.log('razorpay_signature:', razorpayResponse.razorpay_signature);
        this.verifyPayment(razorpayResponse);
      },

      prefill: {
        name: this.customerName,
        email: this.customerEmail,
        contact: this.customerPhone
      },

      theme: {
        color: '#0d6efd'   // Bootstrap primary blue
      },

      modal: {
        // Called when user closes popup without paying
        ondismiss: () => {
          this.isFailed = true;
          this.errorMessage = 'Payment cancelled. Please try again.';
        }
      }
    };

    const rzp = new Razorpay(options);

    // Called when payment fails inside Razorpay popup
    rzp.on('payment.failed', (response: any) => {
      this.isFailed = true;
      this.errorMessage = 'Payment failed: ' + response.error.description;
    });

    rzp.open();
  }

  private verifyPayment(razorpayResponse: any): void {
    this.isLoading = true;

    // Build VerifyPaymentRequest matching your backend VerifyPaymentDto exactly
    const verifyRequest: VerifyPaymentRequest = {
      razorPayOrderId: razorpayResponse.razorpay_order_id,
      razorPayPaymentId: razorpayResponse.razorpay_payment_id,
      razorPaySignature: razorpayResponse.razorpay_signature
    };

    // Step 4: Send to backend for signature verification
    this.paymentService.verifyPayment(verifyRequest).subscribe({
      next: (payment) => {
        this.isLoading = false;

        if (payment.paymentStatus === PaymentStatus.SUCCESS) {
          this.isSuccess = true;
          this.successTransactionId = payment.transactionId;
          this.paymentId = payment.paymentId;
          // Redirect to order success page after 3 seconds
          setTimeout(() => {
            this.router.navigate(['/order-success'], {
              queryParams: { paymentId: payment.paymentId }
            });
          }, 3000);
        } else {
          this.isFailed = true;
          this.errorMessage = 'Payment verification failed. Please contact support.';
        }
      },
      error: (err) => {
        this.isLoading = false;
        this.isFailed = true;
        this.errorMessage = 'Verification error. Please contact support.';
        console.error('Verify payment failed:', err);
      }
    });
  }

  onRetry(): void {
    this.resetState();
  }

  private resetState(): void {
    this.isLoading = false;
    this.isSuccess = false;
    this.isFailed = false;
    this.errorMessage = '';
    this.successTransactionId = '';
  }
}