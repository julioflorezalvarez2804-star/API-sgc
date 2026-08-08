package com.company.coursemanagement.application.service;

import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

public class StudentServices implements StudentRepository {

    private StudentRepository StudentRepository = null;

    public  StudentServices(StudentRepository studentRepository) {
        this.StudentRepository = StudentRepository;
    }


    @Override
    public Student save(Student student) {
        return null;
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        return List.of();
    }

    @Override
    public Student update(Student student) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
