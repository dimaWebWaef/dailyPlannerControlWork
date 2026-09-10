package com.itacademy.dailyplanner.dao.impl;

import com.itacademy.dailyplanner.dao.TaskDao;
import com.itacademy.dailyplanner.domian.Task;
import com.itacademy.dailyplanner.exception.DaoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class FileTaskDao implements TaskDao {
    private final Path filePath;
    private final Map<String, Task> cache = new LinkedHashMap<>();

    public FileTaskDao(String filePath) {
        this.filePath = Path.of(filePath);
        loadFromFile();
    }

    // --- Чтение / запись файла ---

    private void loadFromFile() {
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent() != null
                        ? filePath.getParent() : Path.of("."));
                return;
            }
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.isBlank()) continue;
                    Task task = parseLine(line);
                    cache.put(task.getId(), task);
                }
            }
        } catch (IOException e) {
            throw new DaoException("Ошибка чтения файла: " + filePath, e);
        }
    }

    private void flushToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Task task : cache.values()) {
                writer.write(formatLine(task));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DaoException("Ошибка записи файла: " + filePath, e);
        }
    }



    private String formatLine(Task task) {
        return String.join("|",
                task.getId(),
                task.getDate().toString(),
                task.getPriority().name(),
                String.valueOf(task.isCompleted()),
                task.getDescription()
        );
    }

    private Task parseLine(String line) {
        String[] parts = line.split("\\|", 5);
        if (parts.length < 5) {
            throw new DaoException("Некорректная строка в файле: " + line);
        }
        try {
            String id = parts[0];
            LocalDate date = LocalDate.parse(parts[1]);
            Task.Priority priority = Task.Priority.valueOf(parts[2]);
            boolean completed = Boolean.parseBoolean(parts[3]);
            String description = parts[4];
            return new Task(id, description, date, priority, completed);
        } catch (DateTimeParseException e) {
            throw new DaoException("Некорректная дата в файле: " + parts[1], e);
        } catch (IllegalArgumentException e) {
            throw new DaoException("Некорректный приоритет в файле: " + parts[2], e);
        }
    }



    @Override
    public void save(Task task) {
        cache.put(task.getId(), task);
        flushToFile();
    }

    @Override
    public void update(Task task) {
        if (!cache.containsKey(task.getId())) {
            throw new DaoException("Задача не найдена: " + task.getId());
        }
        cache.put(task.getId(), task);
        flushToFile();
    }

    @Override
    public void delete(String taskId) {
        cache.remove(taskId);
        flushToFile();
    }

    @Override
    public Optional<Task> findById(String taskId) {
        return Optional.ofNullable(cache.get(taskId));
    }

    @Override
    public List<Task> findByDate(LocalDate date) {
        return cache.values().stream()
                .filter(t -> t.getDate().equals(date))
                .sorted(Comparator.comparing(Task::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAll() {
        return cache.values().stream()
                .sorted(Comparator.comparing(Task::getDate)
                        .thenComparing(t -> t.getPriority().ordinal(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
