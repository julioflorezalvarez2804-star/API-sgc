package com.company.coursemanagement.infrastructure.repository;

import com.company.coursemanagement.domain.model.Enrollment;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import com.company.coursemanagement.infrastructure.entity.CourseEntity;
import com.company.coursemanagement.infrastructure.entity.EnrollmentEntity;
import com.company.coursemanagement.infrastructure.entity.StudentEntity;
import com.company.coursemanagement.infrastructure.repository.jpa.CourseJpaRepository;
import com.company.coursemanagement.infrastructure.repository.jpa.EnrollmentJpaRepository;
import com.company.coursemanagement.infrastructure.repository.jpa.StudentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    private final EnrollmentJpaRepository jpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final CourseJpaRepository courseJpaRepository;

    public EnrollmentRepositoryImpl(EnrollmentJpaRepository jpaRepository,
                                     StudentJpaRepository studentJpaRepository,
                                     CourseJpaRepository courseJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.studentJpaRepository = studentJpaRepository;
        this.courseJpaRepository = courseJpaRepository;
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        EnrollmentEntity saved = jpaRepository.save(toEntity(enrollment));
        return toDomain(saved);
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Enrollment> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Enrollment> findByCourseId(Long courseId) {
        return jpaRepository.findByCourseId(courseId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Enrollment> findByStudentId(Long studentId) {
        return jpaRepository.findByStudentId(studentId).stream().map(this::toDomain).toList();
    }

    @Override
    public Enrollment update(Enrollment enrollment) {
        EnrollmentEntity updated = jpaRepository.save(toEntity(enrollment));
        return toDomain(updated);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    private EnrollmentEntity toEntity(Enrollment enrollment) {
        StudentEntity student = studentJpaRepository.getReferenceById(enrollment.getStudentId());
        CourseEntity course = courseJpaRepository.getReferenceById(enrollment.getCourseId());

        return new EnrollmentEntity(
                enrollment.getId(),
                student,
                course,
                enrollment.getEnrollmentDate(),
                enrollment.getStatus()
        );
    }

    private Enrollment toDomain(EnrollmentEntity entity) {
        return new Enrollment(
                entity.getId(),
                entity.getStudent().getId(),
                entity.getCourse().getId(),
                entity.getEnrollmentDate(),
                entity.getStatus()
        );
    }
}
