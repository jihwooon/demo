package com.codesoom.demo.modles;

public class Task {
    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //PROJECT-6-ci-cd test
    public String toString() {
        String id = "id";
        String title = "title";
        return String.format("Task-> id: %s, title: %s", id, title);
    }
}
