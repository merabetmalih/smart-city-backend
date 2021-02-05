package com.example.springmvcrest.store.service.exception;

public class MultipleStoreException extends RuntimeException {
    public MultipleStoreException() {
    }

    public MultipleStoreException(String message) {
        super(message);
    }

    public MultipleStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultipleStoreException(Throwable cause) {
        super(cause);
    }

    public MultipleStoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
