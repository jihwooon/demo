package com.codesoom.demo.modles;

import static org.assertj.core.api.Assertions.assertThat;import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void creation() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("제목");

        assertThat(task.getId()).isEqualTo(1L);
        assertThat(task.getTitle()).isEqualTo("제목");

        assertThat(task.getId()).isNotEqualTo(100L);
        assertThat(task.getTitle()).isNotEqualTo("제목1");
    }
}
