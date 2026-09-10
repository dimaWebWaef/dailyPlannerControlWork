package com.itacademy.dailyplanner.controller;

import com.itacademy.dailyplanner.domian.Task;
import com.itacademy.dailyplanner.exception.ControllerException;
import com.itacademy.dailyplanner.exception.ServiceException;
import com.itacademy.dailyplanner.service.PlannerService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PlannerController {
    private final PlannerService service;

    public PlannerController(PlannerService service) {
        if (service == null) {
            throw new ControllerException("PlannerService не может быть null");
        }
        this.service = service;
    }

    public Task addTask(String description, String dateStr, String priorityStr) {
        if (description == null || description.isBlank()) {
            throw new ControllerException("Описание задачи не может быть пустым");
        }
        LocalDate date = parseDate(dateStr);
        Task.Priority priority = parsePriority(priorityStr);
        return service.createTask(description, date, priority);
    }

    public List<Task> showTasksForDate(String dateStr) {
        LocalDate date = parseDate(dateStr);
        return service.getTasksForDate(date);
    }

    public List<Task> showAllTasks() {
        return service.getAllTasks();
    }

    public void completeTask(String taskId) {
        validateId(taskId);
        service.completeTask(taskId);
    }

    public void uncompleteTask(String taskId) {
        validateId(taskId);
        service.uncompleteTask(taskId);
    }

    public void deleteTask(String taskId) {
        validateId(taskId);
        service.deleteTask(taskId);
    }

    public String getStatistics() {
        return service.getStatistics();
    }

    //Вспомогательные методы

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr.trim());
        } catch (DateTimeParseException e) {
            throw new ControllerException("Неверный формат даты. Используйте yyyy-MM-dd.", e);
        }
    }

    private Task.Priority parsePriority(String priorityStr) {
        try {
            return Task.Priority.valueOf(priorityStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ControllerException(
                    "Неверный приоритет. Доступны: LOW, MEDIUM, HIGH.", e);
        }
    }

    private void validateId(String taskId) {
        if (taskId == null || taskId.isBlank()) {
            throw new ControllerException("ID задачи не может быть пустым");
        }
    }
}
