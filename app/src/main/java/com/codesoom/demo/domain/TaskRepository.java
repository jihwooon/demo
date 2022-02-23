package com.codesoom.demo.domain;

import com.codesoom.demo.TaskNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {

    private List<Task> tasks = new ArrayList<>();
    private Long newId = 0L;

    public List<Task> findAll() {
        return tasks;
    }

    public Task find(Long id) {
        return findTask(id);
    }

    public Task save(Task source) {
        Task task = new Task();
        task.setId(generteId());
        task.setTitle(source.getTitle());
        tasks.add(task);
        return task;
    }

    public Task remove(Task task) {
        tasks.remove(task);
        return task;
    }

    private synchronized Long generteId() {
        newId += 1;
        return newId;
    }

    private Task findTask(Long id) {
        return tasks.stream()
            .filter(task -> task.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
