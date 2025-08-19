package com.jiawa.train.number.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.jiawa")
public class NumberApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumberApplication.class, args);
    }

}