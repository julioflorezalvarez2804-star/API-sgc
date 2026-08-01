package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    StudentDTO create(StudentDTO studentDTO);

    StudentDTO findById(Long id);

    List<StudentDTO> findAll();

    StudentDTO update(Long id, StudentDTO studentDTO);

    void deleteById(Long id);
}
