package com.company.coursemanagement.presentation;

import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.domain.exception.BusinessException;

import java.time.LocalDate;

public class StudentMenu {

    private final StudentService studentService;
    private final InputReader reader;

    public StudentMenu(StudentService studentService, InputReader reader) {
        this.studentService = studentService;
        this.reader = reader;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Menú Student ---");
            System.out.println("1. Create");
            System.out.println("2. Find By Id");
            System.out.println("3. List All");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");

            int option = reader.readInt("Selecciona una opción: ");

            try {
                switch (option) {
                    case 1 -> create();
                    case 2 -> findById();
                    case 3 -> listAll();
                    case 4 -> update();
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
        String firstName = reader.readLine("Nombre: ");
        String lastName = reader.readLine("Apellido: ");
        String email = reader.readLine("Email: ");
        LocalDate birthDate = reader.readDate("Fecha de nacimiento");

        StudentDTO dto = new StudentDTO(null, firstName, lastName, email, birthDate);
        StudentDTO created = studentService.create(dto);
        System.out.println("Estudiante creado: " + created);
    }

    private void findById() {
        Long id = reader.readLong("Id: ");
        StudentDTO dto = studentService.findById(id);
        System.out.println(dto);
    }

    private void listAll() {
        var students = studentService.findAll();
        if (students.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        students.forEach(System.out::println);
    }

    private void update() {
        Long id = reader.readLong("Id del estudiante a actualizar: ");
        String firstName = reader.readLine("Nuevo nombre: ");
        String lastName = reader.readLine("Nuevo apellido: ");
        String email = reader.readLine("Nuevo email: ");
        LocalDate birthDate = reader.readDate("Nueva fecha de nacimiento");

        StudentDTO dto = new StudentDTO(id, firstName, lastName, email, birthDate);
        StudentDTO updated = studentService.update(id, dto);
        System.out.println("Estudiante actualizado: " + updated);
    }

    private void delete() {
        Long id = reader.readLong("Id del estudiante a eliminar: ");
        studentService.deleteById(id);
        System.out.println("Estudiante eliminado.");
    }
}
