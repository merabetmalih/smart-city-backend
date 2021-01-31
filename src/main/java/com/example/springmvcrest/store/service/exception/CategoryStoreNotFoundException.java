package com.example.springmvcrest.store.service.exception;

public class CategoryStoreNotFoundException extends RuntimeException {

    public CategoryStoreNotFoundException() {
    }

    public CategoryStoreNotFoundException(String message) {
        super(message);
    }

    public CategoryStoreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryStoreNotFoundException(Throwable cause) {
        super(cause);
    }

    public CategoryStoreNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}