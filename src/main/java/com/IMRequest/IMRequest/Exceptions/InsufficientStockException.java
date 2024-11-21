package com.IMRequest.IMRequest.Exceptions;

public class InsufficientStockException extends RuntimeException {

    // Constructor that accepts a message
    public InsufficientStockException(String message) {
        super(message);
    }

    // Constructor that accepts both a message and a cause
    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
