//TODO
// id , email, name, password
package com.codesoom.demo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void creation() {

        User user = User.builder()
                .id(1L)
                .email("jihwooon@gmail.com")
                .name("쥐돌이")
                .password("1234")
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("jihwooon@gmail.com");
        assertThat(user.getName()).isEqualTo("쥐돌이");
        assertThat(user.getPassword()).isEqualTo("1234");

    }

}
