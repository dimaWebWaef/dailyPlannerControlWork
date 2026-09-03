package com.itacademy.dailyplanner.service;

import com.itacademy.dailyplanner.exception.DuplicateTaskException;
import com.itacademy.dailyplanner.exception.TaskNotFoundException;
import com.itacademy.dailyplanner.model.Task;
import com.itacademy.dailyplanner.model.TaskStatus;
import com.itacademy.dailyplanner.util.LoggerConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

// Основной сервис для работы с задачами
//  коллекции Hash  хранения по ID, ArList для списков
public class TaskService {

    private static final Logger logger = LoggerConfig.getLogger(TaskService.class.getName());


    //  быстрого поиска по ID
    private final Map<Integer, Task> tasks;


    public TaskService() {
        this.tasks = new HashMap<>();
        logger.info("TaskService создан, хранилище задач инициализировано");
    }

    // Добавить задачу
    public void addTask(Task task) {
        TaskValidator.validate(task);

        if (tasks.containsKey(task.getId())) {
            logger.warning("Попытка добавить дубликат, ID: " + task.getId());
            throw new DuplicateTaskException(
                    "Задача с ID " + task.getId() + " уже существует"
            );
        }

        tasks.put(task.getId(), task);
        logger.info("Задача добавлена: ID=" + task.getId() + ", название=\"" + task.getTitle() + "\"");
    }

    // Удалить задачу по ID
    public void removeTask(int id) {
        if (!tasks.containsKey(id)) {
            logger.warning("Задача не найдена при удалении, ID: " + id);
            throw new TaskNotFoundException("Задача с ID " + id + " не найдена");
        }


        Task removed = tasks.remove(id);
        logger.info("Задача удалена: ID=" + id + ", \"" + removed.getTitle() + "\"");
    }

    // Получить задачу по ID
    public Task getTask(int id) {
        if (!tasks.containsKey(id)) {
            logger.warning("Задача не найдена, ID: " + id);
            throw new TaskNotFoundException("Задача с ID " + id + " не найдена");
        }
        return tasks.get(id);
    }

    // Получить все задачи (возвращает неизменяемый список)
    public List<Task> getAllTasks() {
        List<Task> list = new ArrayList<>(tasks.values());
        logger.info("Получен список всех задач, количество: " + list.size());
        return Collections.unmodifiableList(list);
    }

    // Получить задачи на конкретную дату
    public List<Task> getTasksByDate(LocalDate date) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getDate().equals(date)) {
                result.add(task);
            }
        }
        logger.info("Найдено задач на " + date + ": " + result.size());
        return result;
    }

    // Отметить задачу выполненной
    public void markAsDone(int id) {
        Task task = getTask(id);
        task.setStatus(TaskStatus.DONE);
        logger.info("Задача отмечена выполненной: ID=" + id);
    }

    // Сортировка задач по дате
    public List<Task> getTasksSortedByDate() {
        List<Task> list = new ArrayList<>(tasks.values());
        list.sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getDate().compareTo(t2.getDate());
            }
        });
        logger.info("Задачи отсортированы по дате");
        return list;
    }

    // Количество задач
    public int getTaskCount() {
        return tasks.size();
    }
}
