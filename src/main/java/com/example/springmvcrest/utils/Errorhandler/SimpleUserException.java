package com.example.springmvcrest.utils.Errorhandler;

public class SimpleUserException extends RuntimeException{
    public SimpleUserException(String s) {
        super(s);
    }
}
