package com.company.coursemanagement.domain.exception;

public class StudentEmailAlreadyExistsException extends RuntimeException {
    public StudentEmailAlreadyExistsException(String message) {
        super(message);
    }
}
