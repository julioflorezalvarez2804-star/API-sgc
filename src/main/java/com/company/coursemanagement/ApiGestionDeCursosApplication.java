package com.company.coursemanagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGestionDeCursosApplication implements CommandLineRunner {

    public static void main(String[] args) {
        // 1. Inicia el framework Spring Boot
        SpringApplication.run(ApiGestionDeCursosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
