package com.company.coursemanagement.infrastructure.repository;

import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.repository.CourseRepository;
import com.company.coursemanagement.shared.IdGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCourseRepository implements CourseRepository {

    private final Map<Long, Course> storage = new ConcurrentHashMap<>();
    private final IdGenerator idGenerator = new IdGenerator();

    @Override
    public Course save(Course course) {
        Long id = idGenerator.next();
        course.setId(id);
        storage.put(id, course);
        return course;
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Course> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Course update(Course course) {
        storage.put(course.getId(), course);
        return course;
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
