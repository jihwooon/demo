package com.codesoom.demo.utils;

import com.codesoom.demo.error.InvalidTokenException;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtUtilTest {
    private JwtUtil jwtUnit;
    private static final String SECRET = "12345678901234567890123456789012";

    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";
    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaD3";

    @BeforeEach
    void setUp() {
        jwtUnit = new JwtUtil(SECRET);
    }

    @Test
    void encode() {
        String token = jwtUnit.encode(1L);

        assertThat(token).isEqualTo(VALID_TOKEN);
    }

    @Test
    void decodeWithValidToken() {
        Claims claims = jwtUnit.decode(VALID_TOKEN);

        assertThat(claims.get("userId", Long.class)).isEqualTo(1L);
    }

    @Test
    void decodeWithInValidToken() {
        assertThatThrownBy(() -> jwtUnit.decode(INVALID_TOKEN))
                .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    void decodeWithEmptyToken() {
        assertThatThrownBy(() -> jwtUnit.decode(""))
                .isInstanceOf(InvalidTokenException.class);

        assertThatThrownBy(() -> jwtUnit.decode("    "))
                .isInstanceOf(InvalidTokenException.class);

        assertThatThrownBy(() -> jwtUnit.decode(null))
                .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    void decodeWithBlankToken() {
        assertThatThrownBy(() -> jwtUnit.decode(""))
                .isInstanceOf(InvalidTokenException.class);
    }
}
