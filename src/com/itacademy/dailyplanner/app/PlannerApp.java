package com.itacademy.dailyplanner.app;


import com.itacademy.dailyplanner.dao.impl.InMemoryTaskDao;
import com.itacademy.dailyplanner.domian.Task;
import com.itacademy.dailyplanner.service.PlannerService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


public class PlannerApp {
    public static void main(String[] args) {

        InMemoryTaskDao dao = new InMemoryTaskDao();
        PlannerService service = new PlannerService(dao);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        addTask(service, scanner);
                        break;
                    case "2":
                        showTasksForDate(service, scanner);
                        break;
                    case "3":
                        showAllTasks(service);
                        break;
                    case "4":
                        completeTask(service, scanner);
                        break;
                    case "5":
                        deleteTask(service, scanner);
                        break;
                    case "6":
                        System.out.println(service.getStatistics());
                        break;
                    case "0":
                        System.out.println("Пока!");
                        return;
                    default:
                        System.out.println("Неверная опция. Попробуйте снова.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты. Используйте yyyy-MM-dd.");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== ЕЖЕДНЕВНИК =====");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Показать задачи на дату");
        System.out.println("3. Показать все задачи");
        System.out.println("4. Отметить задачу выполненной");
        System.out.println("5. Удалить задачу");
        System.out.println("6. Статистика");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void addTask(PlannerService service, Scanner scanner) {
        System.out.print("Описание задачи: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Дата (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine().trim());
        System.out.print("Приоритет (LOW / MEDIUM / HIGH): ");
        Task.Priority priority = Task.Priority.valueOf(scanner.nextLine().trim().toUpperCase());

        Task task = service.createTask(desc, date, priority);
        System.out.println("Задача создана: " + task);
    }

    private static void showTasksForDate(PlannerService service, Scanner scanner) {
        System.out.print("Дата (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine().trim());
        List<Task> tasks = service.getTasksForDate(date);

        if (tasks.isEmpty()) {
            System.out.println("На эту дату задач нет.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    private static void showAllTasks(PlannerService service) {
        List<Task> tasks = service.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Задач пока нет.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    private static void completeTask(PlannerService service, Scanner scanner) {
        System.out.print("ID задачи: ");
        String taskId = scanner.nextLine().trim();
        service.completeTask(taskId);
        System.out.println("Задача отмечена выполненной.");
    }

    private static void deleteTask(PlannerService service, Scanner scanner) {
        System.out.print("ID задачи: ");
        String taskId = scanner.nextLine().trim();
        service.deleteTask(taskId);
        System.out.println("Задача удалена.");
    }
}
