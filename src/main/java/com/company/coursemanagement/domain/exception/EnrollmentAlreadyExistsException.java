package com.company.coursemanagement.domain.exception;

public class EnrollmentAlreadyExistsException extends ResourceConflictException {
    public EnrollmentAlreadyExistsException(String message) {
        super(message);
    }
}