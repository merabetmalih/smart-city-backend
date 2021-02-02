package com.example.springmvcrest.utils;

public class ImageStoreNotFoundException extends RuntimeException{
    public ImageStoreNotFoundException() {
    }

    public ImageStoreNotFoundException(String message) {
        super(message);
    }

    public ImageStoreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageStoreNotFoundException(Throwable cause) {
        super(cause);
    }

    public ImageStoreNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
