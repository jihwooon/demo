package com.codesoom.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class HelloControllerTest {

    @Test
    void creation (){
        HelloController helloController = new HelloController();

        assertThat(helloController.sayHello()).isEqualTo("Hello, world");
    }
}
