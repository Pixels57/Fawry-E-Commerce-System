package com.task.eCommerce.exceptions;

public class InsufficientQuantityException extends Exception {
    public InsufficientQuantityException(String message) {
        super(message);
    }
}