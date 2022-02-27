package com.codesoom.demo.controller;

import com.codesoom.demo.application.UserService;
import com.codesoom.demo.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(User user) {
        return userService.createUser(user);
    }

    @PatchMapping("{id}")
    public User update() {
        return userService.updateUser();
    }

    @DeleteMapping("{id}")
    public void delete() {
        userService.deleteUser();
    }

}
