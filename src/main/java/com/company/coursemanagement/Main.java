package com.company.coursemanagement;

import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.application.service.impl.CourseServiceImpl;
import com.company.coursemanagement.application.service.impl.EnrollmentServiceImpl;
import com.company.coursemanagement.application.service.impl.StudentServiceImpl;
import com.company.coursemanagement.domain.repository.CourseRepository;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import com.company.coursemanagement.domain.repository.StudentRepository;
import com.company.coursemanagement.infrastructure.repository.InMemoryCourseRepository;
import com.company.coursemanagement.infrastructure.repository.InMemoryEnrollmentRepository;
import com.company.coursemanagement.infrastructure.repository.InMemoryStudentRepository;
import com.company.coursemanagement.presentation.CourseMenu;
import com.company.coursemanagement.presentation.EnrollmentMenu;
import com.company.coursemanagement.presentation.InputReader;
import com.company.coursemanagement.presentation.MainMenu;
import com.company.coursemanagement.presentation.StudentMenu;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        StudentRepository studentRepository = new InMemoryStudentRepository();
        CourseRepository courseRepository = new InMemoryCourseRepository();
        EnrollmentRepository enrollmentRepository = new InMemoryEnrollmentRepository();


        StudentService studentService = new StudentServiceImpl(studentRepository);
        CourseService courseService = new CourseServiceImpl(courseRepository);
        EnrollmentService enrollmentService = new EnrollmentServiceImpl(
                enrollmentRepository, studentRepository, courseRepository);


        try (Scanner scanner = new Scanner(System.in)) {
            InputReader reader = new InputReader(scanner);

            StudentMenu studentMenu = new StudentMenu(studentService, reader);
            CourseMenu courseMenu = new CourseMenu(courseService, reader);
            EnrollmentMenu enrollmentMenu = new EnrollmentMenu(enrollmentService, reader);

            MainMenu mainMenu = new MainMenu(studentMenu, courseMenu, enrollmentMenu, reader);
            mainMenu.start();
        }
    }
}
