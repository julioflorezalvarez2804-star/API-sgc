package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.application.dto.EnrollmentMapper;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.domain.exception.CourseFullException;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.domain.exception.DuplicateEnrollmentException;
import com.company.coursemanagement.domain.exception.EnrollmentNotFoundException;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.model.Enrollment;
import com.company.coursemanagement.domain.model.EnrollmentStatus;
import com.company.coursemanagement.domain.repository.CourseRepository;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import com.company.coursemanagement.domain.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;

public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                  StudentRepository studentRepository,
                                  CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public EnrollmentDTO create(EnrollmentDTO enrollmentDTO) {
        Long studentId = enrollmentDTO.studentId();
        Long courseId = enrollmentDTO.courseId();

        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        boolean alreadyActive = enrollmentRepository.findByStudentId(studentId).stream()
                .anyMatch(e -> e.getCourseId().equals(courseId) && e.getStatus() == EnrollmentStatus.ACTIVE);
        if (alreadyActive) {
            throw new DuplicateEnrollmentException(studentId, courseId);
        }

        long activeCount = enrollmentRepository.findByCourseId(courseId).stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.ACTIVE)
                .count();
        if (course.getMaxCapacity() != null && activeCount >= course.getMaxCapacity()) {
            throw new CourseFullException(courseId);
        }

        Enrollment enrollment = new Enrollment(
                null,
                studentId,
                courseId,
                enrollmentDTO.enrollmentDate() != null ? enrollmentDTO.enrollmentDate() : LocalDate.now(),
                EnrollmentStatus.ACTIVE
        );

        Enrollment saved = enrollmentRepository.save(enrollment);
        return EnrollmentMapper.toDTO(saved);
    }

    @Override
    public EnrollmentDTO findById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        return EnrollmentMapper.toDTO(enrollment);
    }

    @Override
    public List<EnrollmentDTO> findAll() {
        return enrollmentRepository.findAll()
                .stream()
                .map(EnrollmentMapper::toDTO)
                .toList();
    }

    @Override
    public EnrollmentDTO cancel(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        Enrollment updated = enrollmentRepository.update(enrollment);
        return EnrollmentMapper.toDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new EnrollmentNotFoundException(id);
        }
        enrollmentRepository.deleteById(id);
    }
}
