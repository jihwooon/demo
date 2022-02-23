package com.codesoom.demo.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codesoom.demo.TaskNotFoundException;
import com.codesoom.demo.domain.Task;
import com.codesoom.demo.domain.TaskRepository;
import com.codesoom.demo.infra.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskServiceTest {
    private TaskService taskService;
    private static final String TASK_TITLE = "test";
    private static final String UPDATE_POSTFIX = "!!!";


    @BeforeEach
    void setUp() {
        TaskRepository taskRepository = new InMemoryTaskRepository();
        taskService = new TaskService(taskRepository);

        // fixtures
        Task task = new Task();
        task.setTitle("test");

        taskService.createTask(task);
    }

    @Test
    void getTasks() {
        Task task = taskService.getTasks().get(0);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);
    }

    @Test
    void getTaskWithValidId() {
        Task task = taskService.getTask(1L);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);
    }

    @Test
    void getTaskWithInValidId() {
        assertThatThrownBy(() -> taskService.getTask(100L))
            .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void createTask() {
        Task task = new Task();
        task.setTitle(TASK_TITLE);
        taskService.createTask(task);

        assertThat(taskService.getTasks()).hasSize(2);
    }

    @Test
    void updateWithExistedID() {
        Task source = new Task();
        source.setTitle(TASK_TITLE + UPDATE_POSTFIX);
        taskService.updateTask(1L, source);

        Task task = taskService.getTask(1L);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE + UPDATE_POSTFIX);

    }

    @Test
    void updateWithNotExistedID() {
        Task source = new Task();
        source.setTitle(TASK_TITLE + UPDATE_POSTFIX);

        assertThatThrownBy(() -> taskService.updateTask(100L, source))
            .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void deleteTaskWithExsitedID() {
        int oldSize = taskService.getTasks().size();

        taskService.deleteTask(1L);

        int newSize = taskService.getTasks().size();

        assertThat(oldSize - newSize).isEqualTo(1L);
    }

    @Test
    void deleteTaskWithNotExsitedID() {
        assertThatThrownBy(() -> taskService.deleteTask(100L))
            .isInstanceOf(TaskNotFoundException.class);
    }
}
