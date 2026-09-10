package com.itacademy.dailyplanner.domian;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
    private String id;
    private String description;
    private LocalDate date;
    private Priority priority;
    private boolean completed;

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    // Существующий конструктор — создаёт невыполненную задачу
    public Task(String id, String description, LocalDate date, Priority priority) {
        this(id, description, date, priority, false);
    }

    // Полный конструктор — нужен для восстановления задачи из файла
    public Task(String id, String description, LocalDate date, Priority priority, boolean completed) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID задачи не может быть пустым");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Описание не может быть пустым");
        }
        if (date == null) {
            throw new IllegalArgumentException("Дата не может быть null");
        }
        if (priority == null) {
            throw new IllegalArgumentException("Приоритет не может быть null");
        }

        this.id = id;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.completed = completed;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public Priority getPriority() { return priority; }
    public boolean isCompleted() { return completed; }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Описание не может быть пустым");
        }
        this.description = description;
    }

    public void setPriority(Priority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Приоритет не может быть null");
        }
        this.priority = priority;
    }

    public void markCompleted() { this.completed = true; }
    public void markPending() { this.completed = false; }

    @Override
    public String toString() {
        String status = completed ? "[✓]" : "[ ]";
        return String.format("%s %s | %s | Приоритет: %s | %s",
                status, id, description, priority, date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
