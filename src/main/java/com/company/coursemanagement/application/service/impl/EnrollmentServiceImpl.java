package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.domain.model.Enrollment;
import com.company.coursemanagement.infrastructure.entity.CourseEntity;
import com.company.coursemanagement.infrastructure.entity.EnrollmentEntity;
import com.company.coursemanagement.infrastructure.entity.StudentEntity;
import com.company.coursemanagement.infrastructure.repository.jpa.EnrollmentJpaRepository;
import com.company.coursemanagement.infrastructure.repository.jpa.CourseJpaRepository;
import com.company.coursemanagement.infrastructure.repository.jpa.StudentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentServiceImpl {

    @Autowired
    private EnrollmentJpaRepository jpaEnrollmentRepository;
    @Autowired
    private StudentJpaRepository studentJpaRepository;
    @Autowired
    private CourseJpaRepository courseJpaRepository;

    public Enrollment save(Enrollment enrollment) {
        EnrollmentEntity saved = jpaEnrollmentRepository.save(toEntity(enrollment));
        return toDomain(saved);
    }

    public Optional<Enrollment> findById(Long id) {
        return jpaEnrollmentRepository.findById(id).map(this::toDomain);
    }

    public List<Enrollment> findAll() {
        return jpaEnrollmentRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    public void deleteById(Long id) {
        jpaEnrollmentRepository.deleteById(id);
    }

    private EnrollmentEntity toEntity(Enrollment enrollment) {
        StudentEntity student = studentJpaRepository.getReferenceById(enrollment.getStudentId());
        CourseEntity course = courseJpaRepository.getReferenceById(enrollment.getCourseId());
        return new EnrollmentEntity(enrollment.getId(), student, course, enrollment.getEnrollmentDate(),
                enrollment.getStatus());
    }


    private Enrollment toDomain(EnrollmentEntity entity) {
        return new Enrollment(entity.getId(), entity.getStudent().getId(), entity.getCourse().getId(),
                entity.getEnrollmentDate(), entity.getStatus());


    }
}