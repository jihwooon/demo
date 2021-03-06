package com.codesoom.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("HelloController 클래스")
class HelloControllerTest {

    @Test
    void sayHello() {
        HelloController helloController = new HelloController();
        String result = helloController.sayHello();
        assertThat(result).isEqualTo("Hello, world");

    }
}
