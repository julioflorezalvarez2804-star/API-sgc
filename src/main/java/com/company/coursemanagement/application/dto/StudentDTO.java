package com.company.coursemanagement.application.dto;

import java.time.LocalDate;

public record StudentDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate
) {
}
