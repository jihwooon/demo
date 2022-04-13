package com.codesoom.demo.controller;

import com.codesoom.demo.application.AuthenticationService;
import com.codesoom.demo.application.UserService;
import com.codesoom.demo.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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
    private AuthenticationService authenticationService;
    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1L)
                .email("jihwooon@gmail.com")
                .name("쥐돌이")
                .password("1234")
                .build();

        given(userService.createUser(any(User.class))).willReturn(user);
    }

    @Test
    @DisplayName("create 메서드는 상태코드 201를 응답한다.")
    void create() throws Exception {
        mockMvc.perform(post("/user")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"jihwooon@gmail.com\", \"name\":\"쥐돌이\", \"password\":\"1234\"}"))
                .andExpect(status().isCreated());

        verify(userService).createUser(any(User.class));
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
