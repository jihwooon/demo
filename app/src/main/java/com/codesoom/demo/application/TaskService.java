package com.codesoom.demo.application;

import com.codesoom.demo.TaskNotFoundException;
import com.codesoom.demo.modles.Task;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private List<Task> tasks = new ArrayList<>();
    private Long newId = 0L;

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTask(Long id) {
        return findTask(id);
    }

    public Task createTask(Task source) {
        Task task = new Task();
        task.setId(generteId());
        task.setTitle(source.getTitle());
        tasks.add(task);
        return task;
    }

    public Task updateTask(Long id, Task source) {
        Task task = findTask(id);
        task.setTitle(source.getTitle());

        return task;
    }

    public Task deleteTask(Long id) {
        Task task = getTask(id);
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
