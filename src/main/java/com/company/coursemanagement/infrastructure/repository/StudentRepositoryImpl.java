package com.company.coursemanagement.infrastructure.repository;

import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;
import com.company.coursemanagement.infrastructure.entity.StudentEntity;
import com.company.coursemanagement.infrastructure.repository.jpa.StudentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final StudentJpaRepository jpaRepository;

    public StudentRepositoryImpl(StudentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Student save(Student student) {
        StudentEntity saved = jpaRepository.save(toEntity(student));
        return toDomain(saved);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Student> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Student update(Student student) {
        StudentEntity updated = jpaRepository.save(toEntity(student));
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

    private StudentEntity toEntity(Student student) {
        return new StudentEntity(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getBirthDate()
        );
    }

    private Student toDomain(StudentEntity entity) {
        return new Student(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getBirthDate()
        );
    }
}
