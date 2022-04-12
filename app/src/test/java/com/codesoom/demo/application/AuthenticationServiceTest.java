package com.codesoom.demo.application;

import com.codesoom.demo.domain.User;
import com.codesoom.demo.domain.UserRepository;
import com.codesoom.demo.error.InvalidTokenException;
import com.codesoom.demo.error.LoginFailException;
import com.codesoom.demo.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AuthenticationServiceTest {

    private static final String SECRET = "12345678901234567890123456789012";
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";
    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaD3";

    private AuthenticationService authenticationService;

    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {

        JwtUtil jwtUtil = new JwtUtil(SECRET);
        authenticationService = new AuthenticationService(userRepository, jwtUtil);

        User user = User.builder()
                .password("test")
                .email("tester@example.com")
                .build();
        given(userRepository.findByEmail("tester@example.com")).willReturn(Optional.of(user));
    }

    @Test
    void login() {
        String accessToken = authenticationService.login("tester@example.com", "test");

        assertThat(accessToken).isEqualTo(VALID_TOKEN);
    }

    @Test
    void parseTokenWithValidToken() {
        Long userId = authenticationService.parseToken(VALID_TOKEN);

        assertThat(userId).isEqualTo(1L);
    }

    @Test
    void parseTokenWithInValidToken() {
        assertThatThrownBy(() -> authenticationService.parseToken(INVALID_TOKEN))
                .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    void parseTokenWithBlankToken() {
        assertThatThrownBy(() -> authenticationService.parseToken(null))
                .isInstanceOf(InvalidTokenException.class);

        assertThatThrownBy(() -> authenticationService.parseToken(""))
                .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    void loginWithRightEmail() {
        String accessToken = authenticationService.login(
                "tester@example.com", "test");

        assertThat(accessToken).isEqualTo(VALID_TOKEN);

        verify(userRepository).findByEmail("tester@example.com");
    }

    @Test
    void loginWithWrongEmail() {
        assertThatThrownBy(()-> authenticationService.login("badguy@example.com", "test"))
                .isInstanceOf(LoginFailException.class);

        verify(userRepository).findByEmail("badguy@example.com");
    }

    @Test
    void loginWithWrongPassword() {
        assertThatThrownBy(()-> authenticationService.login("tester@example.com", "xxxx"))
                .isInstanceOf(LoginFailException.class);

    }

}
