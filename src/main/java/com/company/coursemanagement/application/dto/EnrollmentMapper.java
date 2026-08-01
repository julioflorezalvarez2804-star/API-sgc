package com.company.coursemanagement.application.dto;

import com.company.coursemanagement.domain.model.Enrollment;

public class EnrollmentMapper {

    private EnrollmentMapper() {
    }

    public static EnrollmentDTO toDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
                enrollment.getId(),
                enrollment.getStudentId(),
                enrollment.getCourseId(),
                enrollment.getEnrollmentDate(),
                enrollment.getStatus()
        );
    }

    public static Enrollment toModel(EnrollmentDTO dto) {
        return new Enrollment(
                dto.id(),
                dto.studentId(),
                dto.courseId(),
                dto.enrollmentDate(),
                dto.status()
        );
    }
}
