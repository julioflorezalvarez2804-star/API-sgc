package com.company.coursemanagement.domain.exception;

public class StudentPhoneAlreadyExistsException extends RuntimeException {
    public StudentPhoneAlreadyExistsException(String message) {
        super(message);
    }
}
