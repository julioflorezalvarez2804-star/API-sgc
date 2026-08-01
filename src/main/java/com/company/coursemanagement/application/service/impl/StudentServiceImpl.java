package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.dto.StudentMapper;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;

import java.util.List;

/**
 * Depends only on the StudentRepository abstraction (Dependency Inversion Principle),
 * never on a concrete infrastructure implementation.
 */
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDTO create(StudentDTO studentDTO) {
        Student student = StudentMapper.toModel(studentDTO);
        student.setId(null); // id is assigned by the repository
        Student saved = studentRepository.save(student);
        return StudentMapper.toDTO(saved);
    }

    @Override
    public StudentDTO findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return StudentMapper.toDTO(student);
    }

    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toDTO)
                .toList();
    }

    @Override
    public StudentDTO update(Long id, StudentDTO studentDTO) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        Student student = StudentMapper.toModel(studentDTO);
        student.setId(id);
        Student updated = studentRepository.update(student);
        return StudentMapper.toDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }
}
