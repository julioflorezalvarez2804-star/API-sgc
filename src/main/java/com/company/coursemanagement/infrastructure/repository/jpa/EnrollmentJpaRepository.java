package com.company.coursemanagement.infrastructure.repository.jpa;

import com.company.coursemanagement.infrastructure.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentEntity, Long> {

    List<EnrollmentEntity> findByCourseId(Long courseId);

    List<EnrollmentEntity> findByStudentId(Long studentId);
}
