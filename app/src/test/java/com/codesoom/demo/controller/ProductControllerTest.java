package com.codesoom.demo.controller;

import com.codesoom.demo.ProductNotFoundException;
import com.codesoom.demo.application.ProductService;
import com.codesoom.demo.domain.Product;
import com.codesoom.demo.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@DisplayName("ProductController 클래스")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        Product product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .build();
//      Product updated = new Product(1L,"쥐순이","순이월드", 1000);

        given(productService.getProducts()).willReturn(List.of(product));

        given(productService.getProduct(1L)).willReturn(product);

        given(productService.getProduct(1000L))
                .willThrow(new ProductNotFoundException(1000L));

        given(productService.createProduct(any(ProductData.class)))
                .willReturn(product);

//      given(productService.updateProduct(eq(1L),any(Product.class))).willReturn(updated);

        given(productService.updateProduct(eq(1L), any(ProductData.class)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    ProductData productData = invocation.getArgument(1);
                    return Product.builder()
                            .id(1L)
                            .name(productData.getName())
                            .maker(productData.getMaker())
                            .price(productData.getPrice())
                            .build();
                });

        given(productService.updateProduct(eq(1000L), any(ProductData.class)))
                .willThrow(new ProductNotFoundException(1000L));

        given(productService.deleteProduct(1L)).willReturn(product);
        given(productService.deleteProduct(1000L)).willThrow(new ProductNotFoundException(1000L));
    }

    @Test
    @DisplayName("list 메서드는 status 200을 리턴합니다.")
    void list() throws Exception {
        mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("쥐돌이")));

        verify(productService).getProducts();
    }

    @DisplayName("상태 코드 200을")
    class Context_with_detail {
        @Test
        @DisplayName("반환합니다.")
        void detailWithExsitedProduct() throws Exception {
            mockMvc.perform(get("/products/1")
                            .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("쥐돌이")));

            verify(productService).getProduct(1L);
        }
    }

    @DisplayName("상태 코드 404을")
    class Context_with_Not_Exsited_Product {
        @Test
        @DisplayName("반환합니다.")
        void deatilWithNotExsitedProduct() throws Exception {
            mockMvc.perform(get("/products/1000"))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    @DisplayName("craete 메서드는 상태 코드 204을 응답한다.")
    void createWithExsitedProduct() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"쥐돌이\", \"maker\":\"냥이월드\",\"price\":5000}"))
                .andExpect(status().isCreated());

        verify(productService).createProduct(any(ProductData.class));
    }


    @Test
    @DisplayName("craete 메서드는 상태 코드 400을 응답한다.")
    void createWithInvalidAttributes() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"\", \"maker\":\"\",\"price\":0}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("update 메서드는 상태 코드 200을 응답한다.")
    void updateWithExsitedProduct() throws Exception {
        mockMvc.perform(patch("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"쥐순이\", \"maker\":\"순이월드\",\"price\":1000}"))
                .andExpect(status().isOk());

        verify(productService).updateProduct(eq(1L), any(ProductData.class));
    }

    @Test
    @DisplayName("update 메서드는 상태 코드 404을 반환합니다.")
    void updateWithNotExsitedProduct() throws Exception {
        mockMvc.perform(patch("/products/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"\", \"maker\":\"\",\"price\":1000}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("delete 메서드는 상태 코드 200을 반환합니다.")
    void detroyWithvaildProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());

        verify(productService).deleteProduct(1L);
    }

    @Test
    @DisplayName("delete 메서드는 상태 코드 404을 반환합니다.")
    void detroyWithInvaildProduct() throws Exception {
        mockMvc.perform(delete("/products/1000"))
                .andExpect(status().isNotFound());

        verify(productService).deleteProduct(1000L);
    }
}
