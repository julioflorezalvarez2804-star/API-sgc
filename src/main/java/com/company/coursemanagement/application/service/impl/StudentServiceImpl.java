package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.dto.StudentMapper;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.domain.exception.BusinessException;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDTO create(StudentDTO studentDTO) {
        validate(studentDTO);
        Student student = StudentMapper.toModel(studentDTO);
        student.setId(null); // id is assigned by the repository
        Student saved = studentRepository.save(student);
        return StudentMapper.toDTO(saved);
    }

    @Override
    public StudentDTO findById(Long id) {
        validateId(id);
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
        validateId(id);
        validate(studentDTO);
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
        validateId(id);
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    private void validate(StudentDTO studentDTO) {
        if (studentDTO == null) {
            throw new BusinessException("Los datos del estudiante son obligatorios");
        }
        if (studentDTO.firstName() == null || studentDTO.firstName().isBlank()) {
            throw new BusinessException("El nombre del estudiante es obligatorio");
        }
        if (studentDTO.lastName() == null || studentDTO.lastName().isBlank()) {
            throw new BusinessException("El apellido del estudiante es obligatorio");
        }
        if (studentDTO.email() == null || studentDTO.email().isBlank()) {
            throw new BusinessException("El correo del estudiante es obligatorio");
        }
        if (!EMAIL_PATTERN.matcher(studentDTO.email()).matches()) {
            throw new BusinessException("El correo del estudiante no tiene un formato válido");
        }
        if (studentDTO.birthDate() == null) {
            throw new BusinessException("La fecha de nacimiento es obligatoria");
        }
        if (studentDTO.birthDate().isAfter(LocalDate.now())) {
            throw new BusinessException("La fecha de nacimiento no puede ser futura");
        }
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new BusinessException("El id del estudiante es obligatorio");
        }
    }
}