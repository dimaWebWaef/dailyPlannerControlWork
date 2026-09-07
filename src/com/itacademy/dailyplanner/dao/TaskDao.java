package com.itacademy.dailyplanner.dao;

import com.itacademy.dailyplanner.domian.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Интерфейс dao
public interface TaskDao {
    void save(Task task);
    void update(Task task);
    void delete(String taskId);
    Optional<Task> findById(String taskId);
    List<Task> findByDate(LocalDate date);
    List<Task> findAll();
}
