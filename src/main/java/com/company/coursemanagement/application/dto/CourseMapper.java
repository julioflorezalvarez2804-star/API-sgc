package com.company.coursemanagement.application.dto;

import com.company.coursemanagement.domain.model.Course;

public class CourseMapper {

    private CourseMapper() {
    }

    public static CourseDTO toDTO(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getCode(),
                course.getName(),
                course.getDescription(),
                course.getMaxCapacity()
        );
    }

    public static Course toModel(CourseDTO dto) {
        return new Course(
                dto.id(),
                dto.code(),
                dto.name(),
                dto.description(),
                dto.maxCapacity()
        );
    }
}
