package com.smartcart.ecommerce.common.exceptions;

public class CategoryExistsAlreadyException extends RuntimeException {
    public CategoryExistsAlreadyException(String message) {
        super(message);
    }
}
