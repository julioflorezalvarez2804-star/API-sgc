package com.company.coursemanagement.infrastructure.repository.jpa;

import com.company.coursemanagement.infrastructure.entity.StudentEntity;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface StudentJpaRepository extends JpaRepository<StudentEntity, Long> {
    @Override
    Optional<StudentEntity> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    void delete(StudentEntity entity);




}
