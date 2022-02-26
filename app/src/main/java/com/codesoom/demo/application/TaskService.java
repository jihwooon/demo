package com.codesoom.demo.application;

import com.codesoom.demo.TaskNotFoundException;
import com.codesoom.demo.domain.Task;
import com.codesoom.demo.domain.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepsitory) {
        this.taskRepository = taskRepsitory;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(()-> new TaskNotFoundException(id));
    }

    public Task createTask(Task source) {
        return taskRepository.save(source);
    }

    public Task updateTask(Long id, Task source) {
        Task task = taskRepository.findById(id)
            .orElseThrow(()-> new TaskNotFoundException(id));
        task.setTitle(source.getTitle());

        return task;
    }

    public Task deleteTask(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(()-> new TaskNotFoundException(id));
        taskRepository.delete(task);

        return task;
    }


}
