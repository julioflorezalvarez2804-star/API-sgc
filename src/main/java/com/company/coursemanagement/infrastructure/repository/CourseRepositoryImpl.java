package com.company.coursemanagement.infrastructure.repository;

import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.repository.CourseRepository;
import com.company.coursemanagement.infrastructure.entity.CourseEntity;
import com.company.coursemanagement.infrastructure.repository.jpa.CourseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepositoryImpl implements CourseRepository {

    private final CourseJpaRepository jpaRepository;

    public CourseRepositoryImpl(CourseJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Course save(Course course) {
        CourseEntity saved = jpaRepository.save(toEntity(course));
        return toDomain(saved);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Course> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Course update(Course course) {
        CourseEntity updated = jpaRepository.save(toEntity(course));
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

    private CourseEntity toEntity(Course course) {
        return new CourseEntity(
                course.getId(),
                course.getCode(),
                course.getName(),
                course.getDescription(),
                course.getMaxCapacity()
        );
    }

    private Course toDomain(CourseEntity entity) {
        return new Course(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getMaxCapacity()
        );
    }
}
