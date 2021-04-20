package com.example.springmvcrest.utils.Errorhandler;

import com.example.springmvcrest.utils.GenericErrorResponse;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SimpleUserException.class})
    public ResponseEntity<Object> handleSimpleUserException(SimpleUserException e) {
        return returnErrorMessage(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CartException.class})
    public ResponseEntity<Object> handleCartException(CartException e) {
        return returnErrorMessage(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DateException.class})
    public ResponseEntity<Object> handleCartException(DateException e) {
        return returnErrorMessage(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({OrderException.class})
    public ResponseEntity<Object> handleCartException(OrderException e) {
        return returnErrorMessage(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PoliticsException.class})
    public ResponseEntity<Object> handleCartException(PoliticsException e) {
        return returnErrorMessage(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> returnErrorMessage(String errorMessage,HttpStatus httpStatus){
        return new ResponseEntity<Object>(new GenericErrorResponse(errorMessage), new HttpHeaders(), httpStatus);
    }
}
