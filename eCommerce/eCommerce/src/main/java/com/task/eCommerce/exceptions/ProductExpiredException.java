package com.task.eCommerce.exceptions;

public class ProductExpiredException extends Exception {
    public ProductExpiredException(String message) {
        super(message);
    }
}