package com.example.springmvcrest.utils.Errorhandler;

public class CartException extends RuntimeException{
    public CartException(String s) {
        super(s);
    }
}
