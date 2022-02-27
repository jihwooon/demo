package com.codesoom.demo.controller;

import com.codesoom.demo.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create() {
        return null;
    }

    @PatchMapping("{id}")
    public User update() {
        return null;
    }

    @DeleteMapping("{id}")
    public void delete() {

    }

}
