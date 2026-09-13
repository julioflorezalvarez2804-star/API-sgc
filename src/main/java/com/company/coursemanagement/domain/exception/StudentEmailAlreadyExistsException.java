package com.company.coursemanagement.domain.exception;

public class StudentEmailAlreadyExistsException extends ResourceConflictException {
    public StudentEmailAlreadyExistsException(String message) {
        super(message);
    }
}