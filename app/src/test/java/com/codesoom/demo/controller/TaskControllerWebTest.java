package com.codesoom.demo.controller;

import com.codesoom.demo.TaskNotFoundException;
import com.codesoom.demo.application.TaskService;
import com.codesoom.demo.modles.Task;
import java.util.ArrayList;
import java.util.List;
import net.bytebuddy.build.ToStringPlugin.Enhance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//spring 의존해서 테스트

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

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

        given(taskService.updateTask(eq(100L),any(Task.class)))
            .willThrow(new TaskNotFoundException(100L));

        given(taskService.deleteTask(100L))
            .willThrow(new TaskNotFoundException(100L));
    }

    @Test
    void list() throws Exception {
        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk());
            //.andExpect(content().string(containsString("Test Task")));

        verify(taskService).getTasks();
    }

    @Test
    void detailWithvalidId() throws Exception {
        mockMvc.perform(get("/tasks/1"))
            .andExpect(status().isOk());
            //.andExpect(content().string(containsString("Test Task")));
    }

    @Test
    void detailWithInvalidId() throws Exception {
        mockMvc.perform(get("/tasks/100"))
            .andExpect(status().isNotFound());
    }

    @Test
    void createExistedTask() throws Exception {
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"New Task\"}"))
            .andExpect(status().isCreated());

        //해당 Mock Object 의 메소드를 호출했는지 검증
        verify(taskService).createTask(any(Task.class));
    }

    @Test
    void updateExistedTask() throws Exception {
        mockMvc.perform(patch("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Renamed Task\"}")
        )
            .andExpect(status().isOk());
        verify(taskService).updateTask(eq(1L),any(Task.class));
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
