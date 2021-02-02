package com.example.springmvcrest.store.controller;

import com.example.springmvcrest.store.service.exception.CategoryStoreNotFoundException;
import com.example.springmvcrest.user.provider.service.exception.ProviderNotFoundException;
import com.example.springmvcrest.utils.GenericErrorResponse;
import com.example.springmvcrest.utils.ImageStoreNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ProviderNotFoundException.class})
    public ResponseEntity<Object> handleProviderNotFoundException(Exception exception, WebRequest request){
        return returnErrorMessage("ProviderNotFound",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CategoryStoreNotFoundException.class})
    public ResponseEntity<Object> handleCategoryStoreNotFoundException(Exception exception, WebRequest request){
        return returnErrorMessage("CategoryStoreNotFound",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ImageStoreNotFoundException.class})
    public ResponseEntity<Object> handleImageStoreNotFoundException(Exception exception, WebRequest request){
        return returnErrorMessage("ImageStoreNotFound",HttpStatus.NOT_FOUND);
    }

     private ResponseEntity<Object> returnErrorMessage(String errorMessage,HttpStatus httpStatus){
        return new ResponseEntity<Object>(new GenericErrorResponse(errorMessage), new HttpHeaders(), httpStatus);
    }
}