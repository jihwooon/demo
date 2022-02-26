package com.codesoom.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Product 클래스")
class ProductTest {

    @Test
    void creation() {
        Product product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .price(5000)
                .imageUrl("www")
                .build();

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("쥐돌이");
        assertThat(product.getPrice()).isEqualTo(5000);
        assertThat(product.getImageUrl()).isEqualTo("www");
    }

    @Test
    void change() {
        Product product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .price(5000)
                .imageUrl("www")
                .build();

        Product source = Product.builder()
                .name("쥐순이")
                .maker("순이월드")
                .price(5000)
                .build();

        product.change("쥐순이","순이월드", 5000);

        assertThat(product.getName()).isEqualTo("쥐순이");
        assertThat(product.getMaker()).isEqualTo("순이월드");
        assertThat(product.getPrice()).isEqualTo(5000);

    }
}
