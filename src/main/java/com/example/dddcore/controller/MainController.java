package com.example.dddcore.controller;

import com.example.dddcore.exception.ValueOneException;
import com.example.dddcore.service.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    Main main;

    @GetMapping(value = "hello")
    public int hello() {
        int hello = main.hello(1);
        return hello;
    }

    @ExceptionHandler(ValueOneException.class)
    public String exception(ValueOneException exception){
        return String.valueOf(exception);
    }

}
