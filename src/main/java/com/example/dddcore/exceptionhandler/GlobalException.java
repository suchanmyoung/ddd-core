package com.example.dddcore.exceptionhandler;

import com.example.dddcore.exception.ValueOneException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ValueOneException.class)
    public String exception(ValueOneException exception){
        return String.valueOf(exception);
    }
}
