package com.smartcart.ecommerce.common.exceptions;

public class ProductDoesNotExistsException extends RuntimeException {
    public ProductDoesNotExistsException(String message) {
        super(message);
    }
}
