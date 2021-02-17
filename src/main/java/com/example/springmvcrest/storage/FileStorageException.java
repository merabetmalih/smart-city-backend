package com.example.springmvcrest.storage;

public class FileStorageException extends RuntimeException{
    public FileStorageException(String message) {
        super(message);
    }
}