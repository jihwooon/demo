// TODO: 2022/03/01
// 1. 가입(create) -> POST /users => 가입 정보(DTO) -> email이 unique key!
// 2. 정보 갱신(update) -> PATCH /users/{id} => 정보 갱신(DTO) => 이름만!
// 3. 탈퇴(delete) -> DELETE /users/{id}
package com.codesoom.demo.controller;

import com.codesoom.demo.application.UserService;
import com.codesoom.demo.domain.User;
import com.codesoom.demo.dto.UserRegistrationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    @BeforeEach
    void setUp() {

        given(userService.registerUser(any(UserRegistrationData.class))).will(
                invocation -> {
                UserRegistrationData registrationData = invocation.getArgument(0);
                return User.builder()
                            .id(13L)
                            .email(registrationData.getEmail())
                            .name(registrationData.getName())
                            .password(registrationData.getPassword())
                            .build();
                });
    }

    @Test
    @DisplayName("create 메서드는 isCreated을 응답한다.")
    void registerUserWithValidAttributes() throws Exception {
        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"쥐돌이\", \"email\":\"test@example.com\", \"password\":\"test\"}")
            )
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"id\":13")
                ))
                .andExpect(content().string(
                        containsString("\"email\":\"test@example.com\"")
                ));

        verify(userService).registerUser(any(UserRegistrationData.class));
    }

    @Test
    @DisplayName("create 메서드는 isBadRequest을 응답한다.")
    void registerUserWithInvalidAttributes() throws Exception {
        mockMvc.perform(
                    post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{}")
            )
                .andExpect(status().isBadRequest());
    }

}
