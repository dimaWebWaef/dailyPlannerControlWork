package com.itacademy.dailyplanner.repository;

import com.itacademy.dailyplanner.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

 Task save(Task task);

 List<Task> findAll();

 Optional<Task> findById(int id);

 void  delete(int id);
}
