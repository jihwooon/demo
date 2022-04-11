package com.codesoom.error;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String token) {
        super("Invalid token : "+ token);
    }
}
