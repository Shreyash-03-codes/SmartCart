import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { CreatePaymentRequest, PaymentDetails, RazorpayOrderResponse, VerifyPaymentRequest } from '../../../model/payment.models';
import { GenericResponse } from '../../../model/generic-response.model';


@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private readonly apiUrl = 'http://localhost:8080/api/v1/payments';

  constructor(private http: HttpClient) {}

  createOrder(request: CreatePaymentRequest): Observable<RazorpayOrderResponse> {
    return this.http
      .post<GenericResponse<RazorpayOrderResponse>>(`${this.apiUrl}/create-order`, request)
      .pipe(map(response => response.data));  // ✅ unwrap data
  }

  verifyPayment(request: VerifyPaymentRequest): Observable<PaymentDetails> {
    return this.http
      .post<GenericResponse<PaymentDetails>>(`${this.apiUrl}/verify`, request)
      .pipe(map(response => response.data));  // ✅ unwrap data
  }

  getPaymentById(paymentId: number): Observable<PaymentDetails> {
    return this.http
      .get<GenericResponse<PaymentDetails>>(`${this.apiUrl}/${paymentId}`)
      .pipe(map(response => response.data));  // ✅ unwrap data
  }
}