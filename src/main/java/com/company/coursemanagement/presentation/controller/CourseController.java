package com.company.coursemanagement.presentation.controller;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.application.dto.CreateCourseDTO;
import com.company.coursemanagement.application.dto.UpdateCourseDTO;
import com.company.coursemanagement.application.dto.response.CourseResponseDTO;
import com.company.coursemanagement.application.service.CourseService;
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
    public ResponseEntity<CourseResponseDTO> create(@Valid @RequestBody CreateCourseDTO createCourseDTO) {
        CourseDTO courseDTO = new CourseDTO(
                null,
                createCourseDTO.code(),
                createCourseDTO.name(),
                createCourseDTO.description(),
                createCourseDTO.maxCapacity()
        );
        CourseDTO created = courseService.create(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(CourseResponseDTO.from(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> findById(@PathVariable Long id) {
        CourseDTO found = courseService.findById(id);
        return ResponseEntity.ok(CourseResponseDTO.from(found));
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
    public ResponseEntity<CourseResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateCourseDTO updateCourseDTO) {
        CourseDTO courseDTO = new CourseDTO(
                id,
                updateCourseDTO.code(),
                updateCourseDTO.name(),
                updateCourseDTO.description(),
                updateCourseDTO.maxCapacity()
        );
        CourseDTO updated = courseService.update(id, courseDTO);
        return ResponseEntity.ok(CourseResponseDTO.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}