package com.codesoom.demo.controller;

import com.codesoom.demo.application.TaskService;
import com.codesoom.demo.domain.Task;
import com.codesoom.demo.error.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@DisplayName("TaskController 클래스")
class TaskControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskController taskController;

    @MockBean
    private TaskService taskService;


    @BeforeEach
    void setUp() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setTitle("Task task");
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
    @DisplayName("list 메서드 상태 코드 200을 반환하다.")
    void list() throws Exception {
        mockMvc.perform(get("/tasks")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Task task")));

        verify(taskService).getTasks();
    }

    @Test
    @DisplayName("detail 메서드는 상태 코드 200을 반환한다.")
    void detailWithvalidId() throws Exception {
        mockMvc.perform(get("/tasks/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
//            .andExpect(content().string(containsString("Test Task")));
        verify(taskService).getTask(1L);
    }

    @Test
    @DisplayName("detail 메서드는 상태 코드 404을 반환한다.")
    void detailWithInvalidId() throws Exception {
        mockMvc.perform(get("/tasks/100"))
                .andExpect(status().isNotFound());
        verify(taskService).getTask(100L);
    }

    @Test
    void createExistedTask() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Task\"}")
                )
                .andExpect(status().isCreated());

        verify(taskService).createTask(any(Task.class));
    }

    @Test
    void updateExistedTask() throws Exception {
        mockMvc.perform(patch("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Renamed Task\"}")
                )
                .andExpect(status().isOk());
        verify(taskService).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void updateNotExistedTask() throws Exception {
        mockMvc.perform(patch("/tasks/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Renamed Task\"}")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteExistedTask() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk());

        verify(taskService).deleteTask(1L);
    }

    @Test
    void deleteNotExistedTask() throws Exception {
        mockMvc.perform(delete("/tasks/100"))
                .andExpect(status().isNotFound());

        verify(taskService).deleteTask(100L);
    }

}
