//TODO
// id , email, name, password
package com.codesoom.demo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void creation() {

        User user = new User();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("jihwooon@gmail.com");
        assertThat(user.getName()).isEqualTo("쥐돌이");
        assertThat(user.getPassword()).isEqualTo("1234");

    }

}
