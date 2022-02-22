package com.codesoom.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class HelloControllerTest {

    @Test
    void sayHello (){
        HelloController helloController = new HelloController();
        String result = helloController.sayHello();
        assertThat(result).isEqualTo("Hello, world");

    }
}
