package com.company.coursemanagement.presentation.controller;

import com.company.coursemanagement.application.dto.CreateEnrollmentDTO;
import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.application.dto.response.EnrollmentResponseDTO;
import com.company.coursemanagement.application.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.company.coursemanagement.application.dto.UpdateEnrollmentDTO;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> create(@Valid @RequestBody CreateEnrollmentDTO createEnrollmentDTO) {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO(
                null,
                createEnrollmentDTO.studentId(),
                createEnrollmentDTO.courseId(),
                createEnrollmentDTO.enrollmentDate(),
                null
        );
        EnrollmentDTO created = enrollmentService.create(enrollmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(EnrollmentResponseDTO.from(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> findById(@PathVariable Long id) {
        EnrollmentDTO found = enrollmentService.findById(id);
        return ResponseEntity.ok(EnrollmentResponseDTO.from(found));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDTO>> findAll() {
        List<EnrollmentResponseDTO> enrollments = enrollmentService.findAll()
                .stream()
                .map(EnrollmentResponseDTO::from)
                .toList();
        return ResponseEntity.ok(enrollments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateEnrollmentDTO updateEnrollmentDTO) {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO(
                id,
                updateEnrollmentDTO.studentId(),
                updateEnrollmentDTO.courseId(),
                updateEnrollmentDTO.enrollmentDate(),
                null
        );
        EnrollmentDTO updated = enrollmentService.update(id, enrollmentDTO);
        return ResponseEntity.ok(EnrollmentResponseDTO.from(updated));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<EnrollmentResponseDTO> cancel(@PathVariable Long id) {
        EnrollmentDTO cancelled = enrollmentService.cancel(id);
        return ResponseEntity.ok(EnrollmentResponseDTO.from(cancelled));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enrollmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}