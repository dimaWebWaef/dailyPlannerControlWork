package com.itacademy.dailyplanner.service;


import com.itacademy.dailyplanner.dao.TaskDao;
import com.itacademy.dailyplanner.domian.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// Сервис
public class PlannerService {
    private final TaskDao taskDao;


    public PlannerService(TaskDao taskDao) {
        if (taskDao == null) {
            throw new IllegalArgumentException("TaskDao не может быть null");
        }
        this.taskDao = taskDao;
    }

    // Создать новую задачу
    public Task createTask(String description, LocalDate date, Task.Priority priority) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        Task task = new Task(id, description, date, priority);
        taskDao.save(task);
        return task;
    }

    // Отметить задачу выполненной
    public void completeTask(String taskId) {
        taskDao.findById(taskId).ifPresentOrElse(
                task -> {
                    task.markCompleted();
                    taskDao.update(task);
                },
                () -> { throw new IllegalArgumentException("Задача не найдена: " + taskId); }
        );
    }

    // Вернуть в невыполненные
    public void uncompleteTask(String taskId) {
        taskDao.findById(taskId).ifPresentOrElse(
                task -> {
                    task.markPending();
                    taskDao.update(task);
                },
                () -> { throw new IllegalArgumentException("Задача не найдена: " + taskId); }
        );
    }

    // Удалить задачу
    public void deleteTask(String taskId) {
        if (taskDao.findById(taskId).isEmpty()) {
            throw new IllegalArgumentException("Задача не найдена: " + taskId);
        }
        taskDao.delete(taskId);
    }

    // Получить задачи на дату
    public List<Task> getTasksForDate(LocalDate date) {
        return taskDao.findByDate(date);
    }

    // Получить все задачи
    public List<Task> getAllTasks() {
        return taskDao.findAll();
    }

    // Статистика
    public String getStatistics() {
        List<Task> all = taskDao.findAll();
        long total = all.size();
        long done = all.stream().filter(Task::isCompleted).count();
        long pending = total - done;
        return String.format("Всего: %d | Выполнено: %d | Ожидает: %d", total, done, pending);
    }
}
