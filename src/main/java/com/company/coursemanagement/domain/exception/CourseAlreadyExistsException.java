package com.company.coursemanagement.domain.exception;

public class CourseAlreadyExistsException extends ResourceConflictException {
    public CourseAlreadyExistsException(String message) {
        super(message);
    }
}