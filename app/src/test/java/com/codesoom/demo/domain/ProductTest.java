package com.codesoom.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void creation() {
        Product product = new Product(1L,"쥐돌이",5000 , "www");

        assertThat(product.getName()).isEqualTo("쥐돌이");
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getPrice()).isEqualTo(5000);
        assertThat(product.getUrl()).isEqualTo("www");
    }

    @Test
    void change() {
        Product product = new Product(1L,"쥐돌이","냥이월드", 5000);
        Product source = new Product("쥐순이","순이월드", 5000);

        product.change(source);

        assertThat(product.getName()).isEqualTo("쥐순이");
        assertThat(product.getMaker()).isEqualTo("순이월드");
        assertThat(product.getPrice()).isEqualTo(5000);

    }
}
