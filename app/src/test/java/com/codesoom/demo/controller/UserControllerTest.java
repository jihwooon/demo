package com.codesoom.demo.controller;

import com.codesoom.demo.application.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {


     }

    @Test
    @DisplayName("create 메서드는 상태코드 201를 응답한다.")
    void create() throws Exception {
        mockMvc.perform(post("/user"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("update 메서드 상태코드 200를 응답한다.")
    void update() throws Exception {
        mockMvc.perform(patch("/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("delete 메서드 상태코드 200을 응답한다.")
    void detroy() throws Exception {
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk());
    }
}
