package com.company.coursemanagement.presentation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputReader {

    private final Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingresa un número entero.");
            }
        }
    }

    public Long readLong(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingresa un número entero.");
            }
        }
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            String value = readLine(prompt + " (yyyy-MM-dd): ");
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Usa yyyy-MM-dd.");
            }
        }
    }
}
