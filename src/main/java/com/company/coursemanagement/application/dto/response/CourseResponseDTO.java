package com.company.coursemanagement.application.dto.response;

import com.company.coursemanagement.application.dto.CourseDTO;

public record CourseResponseDTO(
        Long id,
        String code,
        String name,
        String description,
        Integer maxCapacity
) {
    public static CourseResponseDTO from(CourseDTO dto) {
        return new CourseResponseDTO(
                dto.id(),
                dto.code(),
                dto.name(),
                dto.description(),
                dto.maxCapacity()
        );
    }
}