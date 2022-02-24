package com.codesoom.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void creation() {
        Product product = new Product(1L,"안녕",5000 , "www");

        assertThat(product.getName()).isEqualTo("안녕");
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getPrice()).isEqualTo(5000);
        assertThat(product.getUrl()).isEqualTo("www");
    }
}
