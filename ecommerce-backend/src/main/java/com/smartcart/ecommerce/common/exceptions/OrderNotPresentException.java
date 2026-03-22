package com.smartcart.ecommerce.common.exceptions;

public class OrderNotPresentException extends RuntimeException {
    public OrderNotPresentException(String message) {
        super(message);
    }
}
