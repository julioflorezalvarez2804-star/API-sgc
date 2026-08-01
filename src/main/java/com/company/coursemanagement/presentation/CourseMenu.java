package com.company.coursemanagement.presentation;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.domain.exception.BusinessException;

public class CourseMenu {

    private final CourseService courseService;
    private final InputReader reader;

    public CourseMenu(CourseService courseService, InputReader reader) {
        this.courseService = courseService;
        this.reader = reader;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Menú Course ---");
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
        String code = reader.readLine("Código: ");
        String name = reader.readLine("Nombre: ");
        String description = reader.readLine("Descripción: ");
        int maxCapacity = reader.readInt("Capacidad máxima: ");

        CourseDTO dto = new CourseDTO(null, code, name, description, maxCapacity);
        CourseDTO created = courseService.create(dto);
        System.out.println("Curso creado: " + created);
    }

    private void findById() {
        Long id = reader.readLong("Id: ");
        CourseDTO dto = courseService.findById(id);
        System.out.println(dto);
    }

    private void listAll() {
        var courses = courseService.findAll();
        if (courses.isEmpty()) {
            System.out.println("No hay cursos registrados.");
            return;
        }
        courses.forEach(System.out::println);
    }

    private void update() {
        Long id = reader.readLong("Id del curso a actualizar: ");
        String code = reader.readLine("Nuevo código: ");
        String name = reader.readLine("Nuevo nombre: ");
        String description = reader.readLine("Nueva descripción: ");
        int maxCapacity = reader.readInt("Nueva capacidad máxima: ");

        CourseDTO dto = new CourseDTO(id, code, name, description, maxCapacity);
        CourseDTO updated = courseService.update(id, dto);
        System.out.println("Curso actualizado: " + updated);
    }

    private void delete() {
        Long id = reader.readLong("Id del curso a eliminar: ");
        courseService.deleteById(id);
        System.out.println("Curso eliminado.");
    }
}
