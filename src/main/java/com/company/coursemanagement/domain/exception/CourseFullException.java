package com.company.coursemanagement.domain.exception;

public class CourseFullException extends BusinessException {

    public CourseFullException(Long courseId) {
        super("Course has reached its maximum capacity: " + courseId);
    }
}
