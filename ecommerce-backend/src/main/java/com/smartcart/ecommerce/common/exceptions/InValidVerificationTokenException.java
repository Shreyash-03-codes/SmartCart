package com.smartcart.ecommerce.common.exceptions;

public class InValidVerificationTokenException extends RuntimeException {
    public InValidVerificationTokenException(String message) {
        super(message);
    }
}
