package com.example.springmvcrest.store.service.exception;

public class DefaultCategoryNotFoundException extends RuntimeException {

    public DefaultCategoryNotFoundException() {
    }

    public DefaultCategoryNotFoundException(String message) {
        super(message);
    }

    public DefaultCategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DefaultCategoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public DefaultCategoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}