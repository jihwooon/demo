package com.codesoom.demo.error;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("Task not found: " + id);
    }
}
