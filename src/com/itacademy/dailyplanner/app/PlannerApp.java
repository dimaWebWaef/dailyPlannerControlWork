package com.itacademy.dailyplanner.app;

import com.itacademy.dailyplanner.controller.PlannerController;
import com.itacademy.dailyplanner.dao.impl.FileTaskDao;
import com.itacademy.dailyplanner.domian.Task;
import com.itacademy.dailyplanner.exception.ControllerException;
import com.itacademy.dailyplanner.service.PlannerService;

import java.util.List;
import java.util.Scanner;

public class PlannerApp {
    public static void main(String[] args) {
        String filePath = (args.length > 0) ? args[0] : "tasks.dat";

        FileTaskDao dao = new FileTaskDao(filePath);
        PlannerService service = new PlannerService(dao);
        PlannerController controller = new PlannerController(service);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addTask(controller, scanner);
                    case "2" -> showTasksForDate(controller, scanner);
                    case "3" -> showAllTasks(controller);
                    case "4" -> completeTask(controller, scanner);
                    case "5" -> deleteTask(controller, scanner);
                    case "6" -> System.out.println(controller.getStatistics());
                    case "0" -> {
                        System.out.println("Пока!");
                        return;
                    }
                    default -> System.out.println("Неверная опция. Попробуйте снова.");
                }
            } catch (ControllerException e) {
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

    private static void addTask(PlannerController controller, Scanner scanner) {
        System.out.print("Описание задачи: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Дата (yyyy-MM-dd): ");
        String date = scanner.nextLine().trim();
        System.out.print("Приоритет (LOW / MEDIUM / HIGH): ");
        String priority = scanner.nextLine().trim();

        Task task = controller.addTask(desc, date, priority);
        System.out.println("Задача создана: " + task);
    }

    private static void showTasksForDate(PlannerController controller, Scanner scanner) {
        System.out.print("Дата (yyyy-MM-dd): ");
        String date = scanner.nextLine().trim();
        List<Task> tasks = controller.showTasksForDate(date);

        if (tasks.isEmpty()) {
            System.out.println("На эту дату задач нет.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    private static void showAllTasks(PlannerController controller) {
        List<Task> tasks = controller.showAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Задач пока нет.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    private static void completeTask(PlannerController controller, Scanner scanner) {
        System.out.print("ID задачи: ");
        String taskId = scanner.nextLine().trim();
        controller.completeTask(taskId);
        System.out.println("Задача отмечена выполненной.");
    }

    private static void deleteTask(PlannerController controller, Scanner scanner) {
        System.out.print("ID задачи: ");
        String taskId = scanner.nextLine().trim();
        controller.deleteTask(taskId);
        System.out.println("Задача удалена.");
    }
}
