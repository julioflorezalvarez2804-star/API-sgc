package com.company.coursemanagement.presentation.controller;

import com.company.coursemanagement.application.dto.CreateStudentDTO;
import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.dto.UpdateStudentDTO;
import com.company.coursemanagement.application.dto.response.StudentResponseDTO;
import com.company.coursemanagement.application.service.StudentService;
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
    public ResponseEntity<StudentResponseDTO> create(@Valid @RequestBody CreateStudentDTO createStudentDTO) {
        StudentDTO studentDTO = new StudentDTO(
                null,
                createStudentDTO.firstName(),
                createStudentDTO.lastName(),
                createStudentDTO.email(),
                createStudentDTO.birthDate()
        );
        StudentDTO created = studentService.create(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(StudentResponseDTO.from(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> findById(@PathVariable Long id) {
        StudentDTO found = studentService.findById(id);
        return ResponseEntity.ok(StudentResponseDTO.from(found));
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
    public ResponseEntity<StudentResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateStudentDTO updateStudentDTO) {
        StudentDTO studentDTO = new StudentDTO(
                id,
                updateStudentDTO.firstName(),
                updateStudentDTO.lastName(),
                updateStudentDTO.email(),
                updateStudentDTO.birthDate()
        );
        StudentDTO updated = studentService.update(id, studentDTO);
        return ResponseEntity.ok(StudentResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}