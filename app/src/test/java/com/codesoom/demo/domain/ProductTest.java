package com.codesoom.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void creation() {
        Product product = new Product();

        assertThat(product.getName()).isNull();
    }

}
