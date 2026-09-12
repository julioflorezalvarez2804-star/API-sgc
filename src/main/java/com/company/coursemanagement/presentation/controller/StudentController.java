package com.company.coursemanagement.presentation.controller;

import com.company.coursemanagement.application.dto.CreateStudentDTO;
import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.dto.response.StudentResponseDTO;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.domain.exception.BusinessException;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.presentation.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateStudentDTO createStudentDTO) {
        try {
            StudentDTO studentDTO = new StudentDTO(
                    null,
                    createStudentDTO.firstName(),
                    createStudentDTO.lastName(),
                    createStudentDTO.email(),
                    createStudentDTO.birthDate()
            );
            StudentDTO created = studentService.create(studentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(StudentResponseDTO.from(created));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            StudentDTO found = studentService.findById(id);
            return ResponseEntity.ok(StudentResponseDTO.from(found));
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getMessage()));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> findAll() {
        List<StudentResponseDTO> students = studentService.findAll()
                .stream()
                .map(StudentResponseDTO::from)
                .toList();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO updated = studentService.update(id, studentDTO);
            return ResponseEntity.ok(StudentResponseDTO.from(updated));
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getMessage()));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            studentService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getMessage()));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }
}