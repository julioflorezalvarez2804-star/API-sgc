package com.company.coursemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.company.coursemanagement")

public class ApiGestionDeCursosApplication

{ public static void main(String[] args) { SpringApplication.run(ApiGestionDeCursosApplication.class, args); } }