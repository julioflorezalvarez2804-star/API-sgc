package com.company.coursemanagement.infrastructure.repository;

import com.company.coursemanagement.domain.model.Enrollment;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import com.company.coursemanagement.shared.IdGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryEnrollmentRepository implements EnrollmentRepository {

    private final Map<Long, Enrollment> storage = new ConcurrentHashMap<>();
    private final IdGenerator idGenerator = new IdGenerator();

    @Override
    public Enrollment save(Enrollment enrollment) {
        Long id = idGenerator.next();
        enrollment.setId(id);
        storage.put(id, enrollment);
        return enrollment;
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Enrollment> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public List<Enrollment> findByCourseId(Long courseId) {
        return storage.values().stream()
                .filter(e -> e.getCourseId().equals(courseId))
                .toList();
    }

    @Override
    public List<Enrollment> findByStudentId(Long studentId) {
        return storage.values().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .toList();
    }

    @Override
    public Enrollment update(Enrollment enrollment) {
        storage.put(enrollment.getId(), enrollment);
        return enrollment;
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
