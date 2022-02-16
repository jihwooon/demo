package com.codesoom.demo.controller;

import com.codesoom.demo.TaskNotFoundException;
import com.codesoom.demo.modles.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private List<Task> tasks = new ArrayList<>();
    private Long newId = 0L;

    @GetMapping
    public List<Task> list() {
        return tasks;
    }

    @GetMapping("{id}")
    public Task detail(@PathVariable Long id) {
        Task task = findTask(id);
        return task;
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        task.setId(generteid());
        tasks.add(task);

        return task;
    }

    @PatchMapping("{id}")
    public Task update(@PathVariable Long id,
                       @RequestBody Task source
    ) {

        Task task = findTask(id);
        task.setTitle(source.getTitle());

        return task;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Task> delete(@PathVariable Long id) {
        Task task = findTask(id);
        tasks.remove(task);

        return ResponseEntity.noContent().build();
    }

    private Task findTask(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    private synchronized Long generteid() {
        newId += 1;
        return newId;
    }
}
