package com.codesoom.demo;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("Task not found: " + id);
    }
}
