package com.company.coursemanagement.infrastructure.repository.jpa;

import com.company.coursemanagement.infrastructure.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaRepository extends JpaRepository<CourseEntity, Long> {
}
