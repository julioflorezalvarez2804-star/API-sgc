package com.company.coursemanagement.application.dto.response;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.domain.model.EnrollmentStatus;

import java.time.LocalDate;

public record EnrollmentResponseDTO(
        Long id,
        Long studentId,
        Long courseId,
        LocalDate enrollmentDate,
        EnrollmentStatus status
) {
    public static EnrollmentResponseDTO from(EnrollmentDTO dto) {
        return new EnrollmentResponseDTO(
                dto.id(),
                dto.studentId(),
                dto.courseId(),
                dto.enrollmentDate(),
                dto.status()
        );
    }
}