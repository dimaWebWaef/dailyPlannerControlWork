package com.itacademy.dailyplanner.service;

import com.itacademy.dailyplanner.dao.TaskDao;
import com.itacademy.dailyplanner.domian.Task;
import com.itacademy.dailyplanner.exception.DaoException;
import com.itacademy.dailyplanner.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PlannerService {
    private final TaskDao taskDao;

    public PlannerService(TaskDao taskDao) {
        if (taskDao == null) {
            throw new ServiceException("TaskDao не может быть null");
        }
        this.taskDao = taskDao;
    }

    public Task createTask(String description, LocalDate date, Task.Priority priority) {
        try {
            String id = UUID.randomUUID().toString().substring(0, 8);
            Task task = new Task(id, description, date, priority);
            taskDao.save(task);
            return task;
        } catch (DaoException e) {
            throw new ServiceException("Не удалось создать задачу", e);
        }
    }

    public void completeTask(String taskId) {
        try {
            taskDao.findById(taskId).ifPresentOrElse(
                    task -> {
                        task.markCompleted();
                        taskDao.update(task);
                    },
                    () -> { throw new ServiceException("Задача не найдена: " + taskId); }
            );
        } catch (DaoException e) {
            throw new ServiceException("Не удалось отметить задачу выполненной", e);
        }
    }

    public void uncompleteTask(String taskId) {
        try {
            taskDao.findById(taskId).ifPresentOrElse(
                    task -> {
                        task.markPending();
                        taskDao.update(task);
                    },
                    () -> { throw new ServiceException("Задача не найдена: " + taskId); }
            );
        } catch (DaoException e) {
            throw new ServiceException("Не удалось вернуть задачу в невыполненные", e);
        }
    }

    public void deleteTask(String taskId) {
        try {
            if (taskDao.findById(taskId).isEmpty()) {
                throw new ServiceException("Задача не найдена: " + taskId);
            }
            taskDao.delete(taskId);
        } catch (DaoException e) {
            throw new ServiceException("Не удалось удалить задачу", e);
        }
    }

    public List<Task> getTasksForDate(LocalDate date) {
        try {
            return taskDao.findByDate(date);
        } catch (DaoException e) {
            throw new ServiceException("Не удалось получить задачи на дату", e);
        }
    }

    public List<Task> getAllTasks() {
        try {
            return taskDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Не удалось получить список задач", e);
        }
    }

    public String getStatistics() {
        try {
            List<Task> all = taskDao.findAll();
            long total = all.size();
            long done = all.stream().filter(Task::isCompleted).count();
            long pending = total - done;
            return String.format("Всего: %d | Выполнено: %d | Ожидает: %d", total, done, pending);
        } catch (DaoException e) {
            throw new ServiceException("Не удалось получить статистику", e);
        }
    }
}
