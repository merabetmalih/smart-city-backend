package com.example.springmvcrest.store.service.exception;

public class CustomCategoryNotFoundExeption extends RuntimeException {
    public CustomCategoryNotFoundExeption() {
    }

    public CustomCategoryNotFoundExeption(String message) {
        super(message);
    }

    public CustomCategoryNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomCategoryNotFoundExeption(Throwable cause) {
        super(cause);
    }

    public CustomCategoryNotFoundExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}