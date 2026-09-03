package com.itacademy.dailyplanner.exception;

//задача с ID уже есть
public class DuplicateTaskException extends RuntimeException {
    public DuplicateTaskException(String message) {
        super(message);
    }
}
