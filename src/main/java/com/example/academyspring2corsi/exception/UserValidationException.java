package com.example.academyspring2corsi.exception;

public class UserValidationException extends RuntimeException{
    public UserValidationException(String message) {
        super(message);
    }

}
