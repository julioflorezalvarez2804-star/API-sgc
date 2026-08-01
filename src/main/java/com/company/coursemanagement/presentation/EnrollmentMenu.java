package com.company.coursemanagement.presentation;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.domain.exception.BusinessException;
import com.company.coursemanagement.domain.model.EnrollmentStatus;

public class EnrollmentMenu {

    private final EnrollmentService enrollmentService;
    private final InputReader reader;

    public EnrollmentMenu(EnrollmentService enrollmentService, InputReader reader) {
        this.enrollmentService = enrollmentService;
        this.reader = reader;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Menú Enrollment ---");
            System.out.println("1. Create Enrollment");
            System.out.println("2. Find By Id");
            System.out.println("3. List All");
            System.out.println("4. Cancel Enrollment");
            System.out.println("5. Delete Enrollment");
            System.out.println("0. Back");

            int option = reader.readInt("Selecciona una opción: ");

            try {
                switch (option) {
                    case 1 -> create();
                    case 2 -> findById();
                    case 3 -> listAll();
                    case 4 -> cancel();
                    case 5 -> delete();
                    case 0 -> running = false;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (BusinessException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void create() {
        Long studentId = reader.readLong("Id del estudiante: ");
        Long courseId = reader.readLong("Id del curso: ");

        EnrollmentDTO dto = new EnrollmentDTO(null, studentId, courseId, null, EnrollmentStatus.ACTIVE);
        EnrollmentDTO created = enrollmentService.create(dto);
        System.out.println("Inscripción creada: " + created);
    }

    private void findById() {
        Long id = reader.readLong("Id: ");
        EnrollmentDTO dto = enrollmentService.findById(id);
        System.out.println(dto);
    }

    private void listAll() {
        var enrollments = enrollmentService.findAll();
        if (enrollments.isEmpty()) {
            System.out.println("No hay inscripciones registradas.");
            return;
        }
        enrollments.forEach(System.out::println);
    }

    private void cancel() {
        Long id = reader.readLong("Id de la inscripción a cancelar: ");
        EnrollmentDTO cancelled = enrollmentService.cancel(id);
        System.out.println("Inscripción cancelada: " + cancelled);
    }

    private void delete() {
        Long id = reader.readLong("Id de la inscripción a eliminar: ");
        enrollmentService.deleteById(id);
        System.out.println("Inscripción eliminada.");
    }
}
