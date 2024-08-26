package com.example.react_spring.model;


public class TodoItem {
    private boolean completed;
    private String description;
    private Integer id;

    public TodoItem(Integer id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + " " + (completed ? "[COMPLETED]" : "[ ] ") + description;
    }
}
