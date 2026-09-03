

import com.itacademy.dailyplanner.exception.InvalidTaskException;
import com.itacademy.dailyplanner.exception.TaskNotFoundException;
import com.itacademy.dailyplanner.model.Priority;
import com.itacademy.dailyplanner.model.Task;
import com.itacademy.dailyplanner.model.TaskStatus;
import com.itacademy.dailyplanner.service.TaskService;
import com.itacademy.dailyplanner.util.LoggerConfig;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

// Точка входа в приложение
// Простой консольный интерфейс — как делали на курсах
public class Main {

    private static final Logger logger = LoggerConfig.getLogger(Main.class.getName());
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskService taskService = new TaskService();
    private static int nextId = 1;

    public static void main(String[] args) {
        logger.info("=== Ежедневник запущен ===");

        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addTaskMenu();
                    break;
                case "2":
                    showAllTasks();
                    break;
                case "3":
                    removeTaskMenu();
                    break;
                case "4":
                    markDoneMenu();
                    break;
                case "5":
                    showTasksByDate();
                    break;
                case "0":
                    running = false;
                    System.out.println("До свидания!");
                    break;
                default:
                    System.out.println("Неверная команда, попробуйте ещё раз");
            }
        }

        logger.info("=== Ежедневник закрыт ===");
    }

    private static void printMenu() {
        System.out.println("\n========== ЕЖЕДНЕВНИК ==========");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Показать все задачи");
        System.out.println("3. Удалить задачу");
        System.out.println("4. Отметить задачу выполненной");
        System.out.println("5. Показать задачи на дату");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void addTaskMenu() {
        try {
            System.out.print("Название задачи: ");
            String title = scanner.nextLine().trim();

            System.out.print("Описание: ");
            String description = scanner.nextLine().trim();

            System.out.print("Дата (гггг.мм.дд): ");
            LocalDate date = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Приоритет (LOW / MEDIUM / HIGH): ");
            Priority priority = Priority.valueOf(scanner.nextLine().trim().toUpperCase());

            // Новая задача по умолчанию получает статус NEW
            Task task = new Task(nextId++, title, description, date, priority, TaskStatus.NEW);
            taskService.addTask(task);

            System.out.println("Задача добавлена!");
        } catch (InvalidTaskException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
            logger.warning("Ошибка валидации: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неверный формат ввода. Проверьте данные.");
            logger.warning("Ошибка ввода: " + e.getMessage());
        }
    }

    private static void showAllTasks() {
        List<Task> tasks = taskService.getTasksSortedByDate();
        if (tasks.isEmpty()) {
            System.out.println("Задач нет");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task);
            System.out.println("----------------------------");
        }
    }

    private static void removeTaskMenu() {
        try {
            System.out.print("ID задачи для удаления: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            taskService.removeTask(id);
            System.out.println("Задача удалена!");
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом");
        }
    }

    private static void markDoneMenu() {
        try {
            System.out.print("ID выполненной задачи: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            taskService.markAsDone(id);
            System.out.println("Задача отмечена как выполненная!");
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом");
        }
    }

    private static void showTasksByDate() {
        try {
            System.out.print("Дата (гггг-мм-дд): ");
            LocalDate date = LocalDate.parse(scanner.nextLine().trim());
            List<Task> tasks = taskService.getTasksByDate(date);
            if (tasks.isEmpty()) {
                System.out.println("На эту дату задач нет");
                return;
            }
            for (Task task : tasks) {
                System.out.println(task);
                System.out.println("----------------------------");
            }
        } catch (Exception e) {
            System.out.println("Неверный формат даты");
        }
    }
}
