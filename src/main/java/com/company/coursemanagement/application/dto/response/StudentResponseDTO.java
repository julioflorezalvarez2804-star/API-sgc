package com.company.coursemanagement.application.dto.response;

import com.company.coursemanagement.application.dto.StudentDTO;

import java.time.LocalDate;

public record StudentResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate
) {
    public static StudentResponseDTO from(StudentDTO dto) {
        return new StudentResponseDTO(
                dto.id(),
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.birthDate()
        );
    }
}