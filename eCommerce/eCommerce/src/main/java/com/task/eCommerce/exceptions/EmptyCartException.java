package com.task.eCommerce.exceptions;

public class EmptyCartException extends Exception {
    public EmptyCartException(String message) {
        super(message);
    }
}