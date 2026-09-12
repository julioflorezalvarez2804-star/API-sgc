package com.company.coursemanagement.presentation.controller;

import com.company.coursemanagement.application.dto.CreateEnrollmentDTO;
import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.application.dto.response.EnrollmentResponseDTO;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.domain.exception.BusinessException;
import com.company.coursemanagement.domain.exception.CourseFullException;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.domain.exception.DuplicateEnrollmentException;
import com.company.coursemanagement.domain.exception.EnrollmentNotFoundException;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.presentation.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateEnrollmentDTO createEnrollmentDTO) {
        try {
            EnrollmentDTO enrollmentDTO = new EnrollmentDTO(
                    null,
                    createEnrollmentDTO.studentId(),
                    createEnrollmentDTO.courseId(),
                    createEnrollmentDTO.enrollmentDate(),
                    null
            );
            EnrollmentDTO created = enrollmentService.create(enrollmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(EnrollmentResponseDTO.from(created));
        } catch (StudentNotFoundException | CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getMessage()));
        } catch (DuplicateEnrollmentException | CourseFullException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.of(e.getMessage()));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            EnrollmentDTO found = enrollmentService.findById(id);
            return ResponseEntity.ok(EnrollmentResponseDTO.from(found));
        } catch (EnrollmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getMessage()));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDTO>> findAll() {
        List<EnrollmentResponseDTO> enrollments = enrollmentService.findAll()
                .stream()
                .map(EnrollmentResponseDTO::from)
                .toList();
        return ResponseEntity.ok(enrollments);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Object> cancel(@PathVariable Long id) {
        try {
            EnrollmentDTO cancelled = enrollmentService.cancel(id);
            return ResponseEntity.ok(EnrollmentResponseDTO.from(cancelled));
        } catch (EnrollmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getMessage()));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            enrollmentService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EnrollmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getMessage()));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }
}