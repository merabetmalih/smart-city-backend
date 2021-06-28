package com.example.springmvcrest.utils.Errorhandler;

public class FlashDealException extends RuntimeException{
    public FlashDealException(String s) {
        super(s);
    }
}