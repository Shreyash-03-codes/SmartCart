package com.smartcart.ecommerce.common.exceptions;

public class CategoryNotPresentException extends RuntimeException {
    public CategoryNotPresentException(String message) {
        super(message);
    }
}
