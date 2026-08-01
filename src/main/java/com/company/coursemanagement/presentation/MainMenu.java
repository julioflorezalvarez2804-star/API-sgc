package com.company.coursemanagement.presentation;

public class MainMenu {

    private final StudentMenu studentMenu;
    private final CourseMenu courseMenu;
    private final EnrollmentMenu enrollmentMenu;
    private final InputReader reader;

    public MainMenu(StudentMenu studentMenu, CourseMenu courseMenu,
                    EnrollmentMenu enrollmentMenu, InputReader reader) {
        this.studentMenu = studentMenu;
        this.courseMenu = courseMenu;
        this.enrollmentMenu = enrollmentMenu;
        this.reader = reader;
    }

    public void start() {
        boolean running = true;
        System.out.println("=== Sistema de Gestión de Cursos ===");

        while (running) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Students");
            System.out.println("2. Courses");
            System.out.println("3. Enrollments");
            System.out.println("0. Exit");

            int option = reader.readInt("Selecciona una opción: ");

            switch (option) {
                case 1 -> studentMenu.show();
                case 2 -> courseMenu.show();
                case 3 -> enrollmentMenu.show();
                case 0 -> {
                    running = false;
                    System.out.println("¡Hasta luego!");
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }
}
