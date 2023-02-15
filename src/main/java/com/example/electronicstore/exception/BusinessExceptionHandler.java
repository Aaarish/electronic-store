package com.example.electronicstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<List<ErrorModel>> handleResourceNotFoundException(ResourceNotFoundException e){
        return new ResponseEntity<>(e.getErrorModels(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorModel>> handleException(Exception e){
        return new ResponseEntity<>(Arrays.asList(new ErrorModel(ErrorType.UNKNOWN_ERROR, e.getMessage())), HttpStatus.NOT_FOUND);
    }
}
