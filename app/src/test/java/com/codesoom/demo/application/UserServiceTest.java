package com.codesoom.demo.application;

import com.codesoom.demo.UserEmailDuplicationException;
import com.codesoom.demo.domain.User;
import com.codesoom.demo.domain.UserRepository;
import com.codesoom.demo.dto.UserRegistrationData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 클래스")
class UserServiceTest {
    private static String EXISTED_EMAIL_ADDRESS = "already@example.com";
    private UserService userService;
    private final UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);

        given(userRepository.existsByEmail(EXISTED_EMAIL_ADDRESS))
                .willThrow(new UserEmailDuplicationException(EXISTED_EMAIL_ADDRESS));

        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return User.builder()
                    .id(1L)
                    .name(source.getName())
                    .email(source.getEmail())
                    .password(source.getPassword())
                    .build();
        });
    }

    @Test
    void registerUser() {
        UserRegistrationData registrationData = UserRegistrationData.builder()
                .email("test@example.com")
                .name("tester")
                .password("test")
                .build();

        User user = userService.registerUser(registrationData);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("tester");
        assertThat(user.getEmail()).isEqualTo("test@example.com");

        verify(userRepository).save(any(User.class));

    }

    @Test
    void registerUserWithDuplicatedEmail() {
        UserRegistrationData registrationData = UserRegistrationData.builder()
                .email(EXISTED_EMAIL_ADDRESS)
                .name("tester")
                .password("test")
                .build();

        assertThatThrownBy(() -> userService.registerUser(registrationData))
                .isInstanceOf(UserEmailDuplicationException.class);

        verify(userRepository).existsByEmail(EXISTED_EMAIL_ADDRESS);
    }
}
