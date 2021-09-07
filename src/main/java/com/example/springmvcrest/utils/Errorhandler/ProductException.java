package com.example.springmvcrest.utils.Errorhandler;

public class ProductException extends RuntimeException{
    public ProductException(String s) {
        super(s);
    }
}