package com.smartcart.ecommerce.common.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
