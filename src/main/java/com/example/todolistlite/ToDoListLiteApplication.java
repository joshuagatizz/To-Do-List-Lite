package com.example.todolistlite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@EnableConfigurationProperties
@SpringBootApplication
public class ToDoListLiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoListLiteApplication.class, args);
    }

}
