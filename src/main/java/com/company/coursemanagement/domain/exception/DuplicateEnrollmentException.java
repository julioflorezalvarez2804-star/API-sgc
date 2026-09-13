package com.company.coursemanagement.domain.exception;

public class DuplicateEnrollmentException extends ResourceConflictException {
    public DuplicateEnrollmentException(Long studentId, Long courseId) {
        super("Student " + studentId + " is already actively enrolled in course " + courseId);
    }
}
