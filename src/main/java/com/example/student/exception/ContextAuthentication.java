package com.example.student.exception;

public class ContextAuthentication extends RuntimeException{
    public ContextAuthentication(String message){
        super(message);
    }
}
