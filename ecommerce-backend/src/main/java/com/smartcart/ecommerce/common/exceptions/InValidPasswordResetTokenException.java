package com.smartcart.ecommerce.common.exceptions;

public class InValidPasswordResetTokenException extends RuntimeException {
    public InValidPasswordResetTokenException(String message) {
        super(message);
    }
}
