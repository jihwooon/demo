//TODO
//1. Read Collection - GET/tasks => 완료
//2. Read Item - GET /tasks/{id} => 진행
//3. Create - POST /tasks
//4. Update - PUT/PATCH /tasks/{id}
//5. Delete - DELETE /tasks/{id}
package com.codesoom.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.codesoom.demo.TaskNotFoundException;
import com.codesoom.demo.application.TaskService;
import com.codesoom.demo.domain.Task;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TaskController 클래스")
class TaskControllerTest {

    //1. Real objecty
    //2. Moc object => 타입에 필요함
    //3. Spy Proxy => 진짜 오브젝트가 필요함
    private TaskService taskService;
    private TaskController controller;

    @BeforeEach
    void setUp() {
        taskService = mock(TaskService.class);
        controller = new TaskController(taskService);

        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setTitle("Test1");
        tasks.add(task);

        given(taskService.getTasks()).willReturn(tasks);
        given(taskService.getTask(1L)).willReturn(task);
        given(taskService.getTask(100L))
                .willThrow(new TaskNotFoundException(100L));
        given(taskService.updateTask(eq(100L), any(Task.class)))
                .willThrow(new TaskNotFoundException(100L));
        given(taskService.deleteTask(100L))
                .willThrow(new TaskNotFoundException(100L));


    }

    @Test
    void listWithoutTasks() {
        given(taskService.getTasks()).willReturn(new ArrayList<>());

        assertThat(controller.list()).isEmpty();

        verify(taskService).getTasks();
    }

    @Test
    void listWithsomeTasks() {
        assertThat(controller.list()).isNotEmpty();

        verify(taskService).getTasks();
    }

    @Test
    void createNewTask() {
        Task task = new Task();
        task.setTitle("Test1");

        controller.create(task);

        verify(taskService).createTask(task);
    }

    @Test
    void detailWithExistedID() {
        Task task = controller.detail(1L);
        assertThat(task).isNotNull();
    }

    @Test
    void detailWithNotExistedID() {
        assertThatThrownBy(() -> controller.detail(100L)).isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void updateExistedTask() {
        Task task = new Task();
        task.setTitle("Rename task");

        controller.update(1L, task);

        verify(taskService).updateTask(1L, task);
    }

    @Test
    void updateNotExistedTask() {
        Task task = new Task();
        task.setTitle("Rename task");

        assertThatThrownBy(() -> controller.update(100L, task)).isInstanceOf(
                TaskNotFoundException.class);
    }

    @Test
    void deleteExistedTask() {
        controller.delete(1L);
        verify(taskService).deleteTask(1L);
    }

    @Test
    void deleteNotExistedTask() {
        assertThatThrownBy(() -> controller.delete(100L)).isInstanceOf(TaskNotFoundException.class);
    }

}
