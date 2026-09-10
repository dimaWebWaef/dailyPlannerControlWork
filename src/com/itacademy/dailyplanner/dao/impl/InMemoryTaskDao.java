package com.itacademy.dailyplanner.dao.impl;

import com.itacademy.dailyplanner.dao.TaskDao;
import com.itacademy.dailyplanner.domian.Task;
import com.itacademy.dailyplanner.exception.DaoException;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryTaskDao implements TaskDao {
    private final Map<String, Task> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Task task) {
        storage.put(task.getId(), task);
    }

    @Override
    public void update(Task task) {
        if (!storage.containsKey(task.getId())) {
            throw new DaoException("Задача не найдена: " + task.getId());
        }
        storage.put(task.getId(), task);
    }

    @Override
    public void delete(String taskId) {
        storage.remove(taskId);
    }

    @Override
    public Optional<Task> findById(String taskId) {
        return Optional.ofNullable(storage.get(taskId));
    }

    @Override
    public List<Task> findByDate(LocalDate date) {
        return storage.values().stream()
                .filter(t -> t.getDate().equals(date))
                .sorted(Comparator.comparing(Task::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAll() {
        return storage.values().stream()
                .sorted(Comparator.comparing(Task::getDate)
                        .thenComparing(t -> t.getPriority().ordinal(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
