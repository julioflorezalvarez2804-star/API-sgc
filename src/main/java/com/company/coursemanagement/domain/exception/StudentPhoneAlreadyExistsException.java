package com.company.coursemanagement.domain.exception;

public class StudentPhoneAlreadyExistsException extends ResourceConflictException {
    public StudentPhoneAlreadyExistsException(String message) {
        super(message);
    }
}