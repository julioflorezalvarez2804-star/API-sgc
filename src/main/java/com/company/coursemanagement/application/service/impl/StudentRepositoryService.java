package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;
import com.company.coursemanagement.infrastructure.entity.StudentEntity;
import com.company.coursemanagement.infrastructure.repository.jpa.StudentJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentRepositoryService implements StudentRepository {

    private final StudentJpaRepository jpaStudentRepository;

    public StudentRepositoryService(StudentJpaRepository jpaStudentRepository) {
        this.jpaStudentRepository = jpaStudentRepository;
    }

    @Override
    public Student save(Student student) {
        StudentEntity saved = jpaStudentRepository.save(toEntity(student));
        return toDomain(saved);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return jpaStudentRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Student> findAll() {
        return jpaStudentRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Student update(Student student) {
        StudentEntity updated = jpaStudentRepository.save(toEntity(student));
        return toDomain(updated);
    }

    @Override
    public void deleteById(Long id) {
        jpaStudentRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaStudentRepository.existsById(id);
    }

    // 🔽 Conversión de modelo a entidad
    private StudentEntity toEntity(Student student) {
        return new StudentEntity(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getBirthDate()
        );
    }

    // 🔽 Conversión de entidad a modelo
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
