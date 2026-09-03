package com.itacademy.dailyplanner.service;


import com.itacademy.dailyplanner.exception.InvalidTaskException;
import com.itacademy.dailyplanner.model.Task;

import java.time.LocalDate;
import java.util.logging.Logger;
import com.itacademy.dailyplanner.util.LoggerConfig;

// валидации задачи перед добавлением изменением
public class TaskValidator {

    private static final Logger logger = LoggerConfig.getLogger(TaskValidator.class.getName());

    public static void validate(Task task) {
        if (task == null) {
            logger.warning("Попытка валидации null-задачи");
            throw new InvalidTaskException("Задача не может быть null");
        }

        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            logger.warning("Пустое название задачи");
            throw new InvalidTaskException("Название задачи не может быть пустым");
        }

        if (task.getDate() == null) {
            logger.warning("Не указана дата задачи");
            throw new InvalidTaskException("Дата задачи обязательна");
        }

        if (task.getDate().isBefore(LocalDate.now())) {
            logger.warning("Дата задачи в прошлом: " + task.getDate());
            throw new InvalidTaskException("Дата задачи не может быть в прошлом");
        }

        if (task.getPriority() == null) {
            logger.warning("Не указан приоритет задачи");
            throw new InvalidTaskException("Приоритет задачи обязателен");
        }

        if (task.getStatus() == null) {
            logger.warning("Не указан статус задачи");
            throw new InvalidTaskException("Статус задачи обязателен");
        }

        logger.info("Задача прошла валидацию: " + task.getTitle());
    }
}
