package com.task.eCommerce.exceptions;

public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}