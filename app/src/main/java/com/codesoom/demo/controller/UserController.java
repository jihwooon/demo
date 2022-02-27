package com.codesoom.demo.controller;

import com.codesoom.demo.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create() {
        return null;
    }

}
