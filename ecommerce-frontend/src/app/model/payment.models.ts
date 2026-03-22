// Matches your CreatePaymentDto
export interface CreatePaymentRequest {
  orderId: number;
  paymentMethod: PaymentMethod;
}

// Matches your RazorpayOrderDto
// Note: razorPayOrderId (capital P) — matches your backend field
export interface RazorpayOrderResponse {
  razorPayOrderId: string;
  amount: number;
  currency: string;
  keyId: string;
}

// Matches your VerifyPaymentDto
// Note: razorPayOrderId, razorPayPaymentId, razorPaySignature — matches your backend
export interface VerifyPaymentRequest {
  razorPayOrderId: string;
  razorPayPaymentId: string;
  razorPaySignature: string;
}

// Matches your GetPaymentDto
export interface PaymentDetails {
  paymentId: number;
  orderId: number;
  amount: number;
  paymentMethod: string;
  paymentStatus: PaymentStatus;
  transactionId: string;
}

// Matches your PaymentMethod enum
export enum PaymentMethod {
  UPI = 'UPI',
  CARD = 'CARD',
  NET_BANKING = 'NET_BANKING',
  CASH = 'CASH'
}

// Matches your PaymentStatus enum
export enum PaymentStatus {
  SUCCESS = 'SUCCESS',
  FAILED = 'FAILED',
  PENDING = 'PENDING'
}