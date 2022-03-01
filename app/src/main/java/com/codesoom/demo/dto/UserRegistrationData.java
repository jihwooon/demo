package com.codesoom.demo.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class UserRegistrationData {
    @NotBlank
    @Size(min=3)
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min=4)
    private String password;
}
