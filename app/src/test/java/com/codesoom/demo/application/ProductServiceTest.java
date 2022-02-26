//TODO
//1. getProducts
//2. getProduct
//3. createProduct
//4. updateProduct
//5. deleteProduct
package com.codesoom.demo.application;

import com.codesoom.demo.ProductNotFoundException;
import com.codesoom.demo.domain.Product;
import com.codesoom.demo.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    // 호출 순 (우선 순위)
    // 지역변수 <- 전역 변수 <- static
    private ProductRepository productRepository = mock(ProductRepository.class);
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);

        Product product = new Product(1L, "쥐돌이", "냥이월드", 50000);

        //ArrayList<T> = new ArrayList(); -> List.of();
        given(productRepository.findAll()).willReturn(List.of(product));

        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(productRepository.findById(1000L)).willReturn(Optional.empty());

        given(productRepository.findById(1000L)).willThrow(new ProductNotFoundException(1000L));

        given(productRepository.save(any(Product.class))).will(invocation -> {
            Product source = invocation.getArgument(0);
            return new Product(
                    2L,
                    source.getName(),
                    source.getMaker(),
                    source.getPrice()
            );
        });
    }

    @Test
    void getProductsWithExsitedProduct() {

        given(productRepository.findAll()).willReturn(List.of());

        assertThat(productService.getProducts()).isEmpty();;

    }

    @Test
    void getProductsWithNotExsitedProduct() {
        List<Product> products = productService.getProducts();

        assertThat(products).isNotEmpty();

        Product product = products.get(0);

        assertThat(product.getName()).isEqualTo("쥐돌이");
    }

    @Test
    void getProductWithExsitedId() {

        Product product = productService.getProduct(1L);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("쥐돌이");
    }

    @Test
    void getProductWithNotExsitedId() {

        assertThatThrownBy(() -> productService.getProduct(1000L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(1000L);
    }

    @Test
    void createProduct() {
        Product source = new Product("쥐돌이", "냥이월드", 5000);

        Product product = productService.createProduct(source);

        verify(productRepository).save(any(Product.class));

        assertThat(product.getId()).isEqualTo(2L);
        assertThat(product.getName()).isEqualTo("쥐돌이");

    }

    @Test
    void updateWithExistedProduct() {
        Product source = new Product("쥐순이", "순이월드", 5000);

        Product product = productService.updateProduct(1L, source);

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("쥐순이");

    }

    @Test
    void updateWithNotExistedProduct() {
        Product source = new Product("쥐순이", "순이월드", 5000);

        assertThatThrownBy(() -> productService.updateProduct(1000L, source))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void deleteProductWithExistedProduct() {
        productService.deleteProduct(1L);

        verify(productRepository).delete(any(Product.class));

    }

    @Test
    void deleteProductWithNotExistedProduct() {
        assertThatThrownBy(() -> productService.deleteProduct(1000L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(1000L);

    }
}
