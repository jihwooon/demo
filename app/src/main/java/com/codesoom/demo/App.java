package com.codesoom.demo;

import java.math.BigInteger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public String getGreeting() {
        BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);
        String integer = String.valueOf(10);

        System.out.println("prime = "+ prime + "/" + prime.getClass());
        System.out.println("integer = "+ integer + "/" + integer.getClass());

        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
