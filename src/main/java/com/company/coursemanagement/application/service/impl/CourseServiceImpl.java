package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.application.dto.CourseMapper;
import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseDTO create(CourseDTO courseDTO) {
        Course course = CourseMapper.toModel(courseDTO);
        course.setId(null);
        Course saved = courseRepository.save(course);
        return CourseMapper.toDTO(saved);
    }

    @Override
    public CourseDTO findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return CourseMapper.toDTO(course);
    }

    @Override
    public List<CourseDTO> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(CourseMapper::toDTO)
                .toList();
    }

    @Override
    public CourseDTO update(Long id, CourseDTO courseDTO) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        Course course = CourseMapper.toModel(courseDTO);
        course.setId(id);
        Course updated = courseRepository.update(course);
        return CourseMapper.toDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }
}
