package com.itacademy.dailyplanner.model;

public enum Status {
    DONE("выполнено"),
    NOT_COMPLETED("не выполнено");

    private final String statuses;

    Status (String statuses){
        this.statuses = statuses;
    }

    public String getStatuses() {
        return statuses;
    }
}
