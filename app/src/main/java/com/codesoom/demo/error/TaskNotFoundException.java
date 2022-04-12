package com.codesoom.demo.error;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(Long id) {
        super("Task not found: " + id);
    }
}
