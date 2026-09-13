package com.company.coursemanagement.domain.exception;

public class StudentAlreadyExistsException extends ResourceConflictException {
    public StudentAlreadyExistsException(String message) {
        super(message);
    }
}