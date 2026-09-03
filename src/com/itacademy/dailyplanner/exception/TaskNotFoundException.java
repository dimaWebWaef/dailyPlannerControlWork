package com.itacademy.dailyplanner.exception;


    // задача не найдена
    public class TaskNotFoundException extends RuntimeException {
        public TaskNotFoundException(String message) {
            super(message);
        }
    }

