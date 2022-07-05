package com.example.dddcore.service;

import com.example.dddcore.exception.ValueOneException;
import org.springframework.stereotype.Service;

@Service
public class Main {
    public int hello(int l){
        if(l == 1){
            throw new ValueOneException();
        }

        return 0;
    }

}
