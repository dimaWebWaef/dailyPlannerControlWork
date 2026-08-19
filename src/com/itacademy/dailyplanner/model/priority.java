package com.itacademy.dailyplanner.model;

public enum priority {
    IMPORTANT("Важно"),
    NOT_IMPORTANT("Не важно"),
    URGENTLY("Срочно"),
    USUALLY("Обычно");

    private final String  priorities;

    priority (String priorities){
        this.priorities = priorities;
    }

    public String getPriorities() {
        return priorities;
    }
}
