package com.smartcart.ecommerce.common.exceptions;


public class InValidRefreshTokenException extends RuntimeException {
    public InValidRefreshTokenException(String message) {
        super(message);
    }
}
