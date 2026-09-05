package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.application.dto.EnrollmentMapper;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.domain.exception.BusinessException;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
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
        validate(enrollmentDTO);

        Long studentId = enrollmentDTO.studentId();
        Long courseId = enrollmentDTO.courseId();

        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        List<Enrollment> courseEnrollments = enrollmentRepository.findByCourseId(courseId);

        boolean alreadyActive = courseEnrollments.stream()
                .anyMatch(e -> e.getStudentId().equals(studentId)
                        && e.getStatus() == EnrollmentStatus.ACTIVE);
        if (alreadyActive) {
            throw new DuplicateEnrollmentException(studentId, courseId);
        }

        long activeCount = courseEnrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.ACTIVE)
                .count();
        if (course.getMaxCapacity() != null && activeCount >= course.getMaxCapacity()) {
            throw new CourseFullException(courseId);
        }

        Enrollment enrollment = EnrollmentMapper.toModel(enrollmentDTO);
        enrollment.setId(null);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        if (enrollment.getEnrollmentDate() == null) {
            enrollment.setEnrollmentDate(LocalDate.now());
        }

        Enrollment saved = enrollmentRepository.save(enrollment);
        return EnrollmentMapper.toDTO(saved);
    }

    @Override
    public EnrollmentDTO findById(Long id) {
        validateId(id);
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        return EnrollmentMapper.toDTO(enrollment);
    }

    @Override
    public List<EnrollmentDTO> findAll() {
        return enrollmentRepository.findAll().stream()
                .map(EnrollmentMapper::toDTO)
                .toList();
    }

    @Override
    public EnrollmentDTO cancel(Long id) {
        validateId(id);
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));

        if (enrollment.getStatus() == EnrollmentStatus.CANCELLED) {
            throw new BusinessException("La inscripción ya se encuentra cancelada");
        }

        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        Enrollment updated = enrollmentRepository.update(enrollment);
        return EnrollmentMapper.toDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        validateId(id);
        if (!enrollmentRepository.existsById(id)) {
            throw new EnrollmentNotFoundException(id);
        }
        enrollmentRepository.deleteById(id);
    }

    private void validate(EnrollmentDTO dto) {
        if (dto == null) {
            throw new BusinessException("Los datos de la inscripción son obligatorios");
        }
        if (dto.studentId() == null) {
            throw new BusinessException("El id del estudiante es obligatorio");
        }
        if (dto.courseId() == null) {
            throw new BusinessException("El id del curso es obligatorio");
        }
        if (dto.enrollmentDate() != null && dto.enrollmentDate().isAfter(LocalDate.now())) {
            throw new BusinessException("La fecha de inscripción no puede ser futura");
        }
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new BusinessException("El id de la inscripción es obligatorio");
        }
    }
}