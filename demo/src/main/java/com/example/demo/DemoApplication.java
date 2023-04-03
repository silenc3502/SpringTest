package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        final ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        final Object testController = context.getBean("testController");

        System.out.println("testController: " + testController);
    }

}
