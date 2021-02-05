package com.example.springmvcrest.store.controller;

import com.example.springmvcrest.store.service.exception.CustomCategoryNotFoundExeption;
import com.example.springmvcrest.store.service.exception.DefaultCategoryNotFoundException;
import com.example.springmvcrest.store.service.exception.MultipleStoreException;
import com.example.springmvcrest.store.service.exception.StoreNotFoundException;
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

    @ExceptionHandler({DefaultCategoryNotFoundException.class})
    public ResponseEntity<Object> handleCategoryStoreNotFoundException(Exception exception, WebRequest request){
        return returnErrorMessage("CategoryStoreNotFound",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ImageStoreNotFoundException.class})
    public ResponseEntity<Object> handleImageStoreNotFoundException(Exception exception, WebRequest request){
        return returnErrorMessage("ImageStoreNotFound",HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({StoreNotFoundException.class})
    public ResponseEntity<Object> handleStoreNotFoundException(Exception exception, WebRequest request){
        return returnErrorMessage("StoreNotFound",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MultipleStoreException.class})
    public ResponseEntity<Object> handleMultipleStoreException(Exception exception, WebRequest request){
        return returnErrorMessage("MultipleStore",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomCategoryNotFoundExeption.class})
    public ResponseEntity<Object> handleCustomCategoryNotFoundExeption(Exception exception, WebRequest request){
        return returnErrorMessage("CustomCategoryNotFound",HttpStatus.NOT_FOUND);
    }

     private ResponseEntity<Object> returnErrorMessage(String errorMessage,HttpStatus httpStatus){
        return new ResponseEntity<Object>(new GenericErrorResponse(errorMessage), new HttpHeaders(), httpStatus);
    }
}