package com.codesoom.demo.controller;

import com.codesoom.demo.error.InvalidTokenException;
import com.codesoom.demo.error.LoginFailException;
import com.codesoom.demo.error.ProductNotFoundException;
import com.codesoom.demo.error.TaskNotFoundException;
import com.codesoom.demo.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ControllerErrorAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskNotFoundException.class)
    public ErrorResponse taskHandleNotFound() {
        return new ErrorResponse("Task Not Found");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse productHandleNotFound() {
        return new ErrorResponse("Product Not Found");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidTokenException.class)
    public ErrorResponse handleInvalidAccessTokenException() {
        return new ErrorResponse("Invalid access token");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginFailException.class)
    public ErrorResponse loginFailBadRequest() {
        return new ErrorResponse("Login Fail email");
    }

}
