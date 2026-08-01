package com.company.coursemanagement.application.dto;

import com.company.coursemanagement.domain.model.EnrollmentStatus;

import java.time.LocalDate;

public record EnrollmentDTO(
        Long id,
        Long studentId,
        Long courseId,
        LocalDate enrollmentDate,
        EnrollmentStatus status
) {
}
