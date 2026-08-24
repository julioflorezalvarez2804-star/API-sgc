package com.company.coursemanagement.infrastructure.repository.jpa;
import java.util.List;
import com.company.coursemanagement.infrastructure.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentEntity, Long> {
    List<EnrollmentEntity> findByCourseId(Long courseId);
    List<EnrollmentEntity> findByStudentId(Long studentId);
}
