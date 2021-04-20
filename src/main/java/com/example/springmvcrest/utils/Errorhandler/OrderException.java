package com.example.springmvcrest.utils.Errorhandler;

public class OrderException extends RuntimeException{
    public OrderException(String s) {
        super(s);
    }
}