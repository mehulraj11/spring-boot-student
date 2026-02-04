package com.example.student.exception;

public class UserRegistrationException extends RuntimeException{
    public UserRegistrationException(String message){
        super(message);
    }
}
