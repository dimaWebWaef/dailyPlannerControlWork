package com.itacademy.dailyplanner.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate date;
    private Priority priority;
    private TaskStatus status;

    public Task(int id, String title, String description, LocalDate date,
                Priority priority, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    // Переопределяем equals //hash искать задачи в коллекциях
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return "Task #" + id + "\n" +
                "  Название: " + title + "\n" +
                "  Описание: " + description + "\n" +
                "  Дата: " + date.format(formatter) + "\n" +
                "  Приоритет: " + priority.getDisplayName() + "\n" +
                "  Статус: " + status.getDisplayName();
    }
}