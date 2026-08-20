-- Script de referencia. NO es necesario ejecutarlo a mano:
-- con spring.jpa.hibernate.ddl-auto=update, Hibernate crea/actualiza estas
-- tablas automáticamente al levantar la aplicación.
-- Úsalo solo si quieres crear la base manualmente en MySQL Workbench / consola.

CREATE DATABASE IF NOT EXISTS sistemagestioncursos
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sistemagestioncursos;

CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    birth_date DATE
);

CREATE TABLE IF NOT EXISTS courses (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    code         VARCHAR(20)  NOT NULL UNIQUE,
    name         VARCHAR(150) NOT NULL,
    description  VARCHAR(500),
    max_capacity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS enrollments (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id      BIGINT NOT NULL,
    course_id       BIGINT NOT NULL,
    enrollment_date DATE NOT NULL,
    status          VARCHAR(20) NOT NULL, -- ACTIVE, CANCELLED, COMPLETED

    CONSTRAINT fk_enrollment_student FOREIGN KEY (student_id) REFERENCES students(id),
    CONSTRAINT fk_enrollment_course  FOREIGN KEY (course_id)  REFERENCES courses(id),
    CONSTRAINT uk_student_course UNIQUE (student_id, course_id)
);
