package com.company.coursemanagement.presentation.controller;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.application.dto.CreateCourseDTO;
import com.company.coursemanagement.application.dto.response.CourseResponseDTO;
import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.domain.exception.BusinessException;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.presentation.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateCourseDTO createCourseDTO) {
        try {
            CourseDTO courseDTO = new CourseDTO(
                    null,
                    createCourseDTO.code(),
                    createCourseDTO.name(),
                    createCourseDTO.description(),
                    createCourseDTO.maxCapacity()
            );
            CourseDTO created = courseService.create(courseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(CourseResponseDTO.from(created));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            CourseDTO found = courseService.findById(id);
            return ResponseEntity.ok(CourseResponseDTO.from(found));
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getMessage()));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> findAll() {
        List<CourseResponseDTO> courses = courseService.findAll()
                .stream()
                .map(CourseResponseDTO::from)
                .toList();
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        try {
            CourseDTO updated = courseService.update(id, courseDTO);
            return ResponseEntity.ok(CourseResponseDTO.from(updated));
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getMessage()));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            courseService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getMessage()));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
        }
    }
}