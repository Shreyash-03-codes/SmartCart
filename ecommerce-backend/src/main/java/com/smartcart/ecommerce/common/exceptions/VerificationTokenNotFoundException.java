package com.smartcart.ecommerce.common.exceptions;

public class VerificationTokenNotFoundException extends RuntimeException {
    public VerificationTokenNotFoundException(String message) {
        super(message);
    }
}
