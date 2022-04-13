package com.codesoom.demo.controller;

import com.codesoom.demo.application.AuthenticationService;
import com.codesoom.demo.application.ProductService;
import com.codesoom.demo.domain.Product;
import com.codesoom.demo.dto.ProductData;
import com.codesoom.demo.error.InvalidTokenException;
import com.codesoom.demo.error.ProductNotFoundException;
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
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";

    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaD3";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        Product product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .build();

        given(productService.getProducts()).willReturn(List.of(product));

        given(productService.getProduct(1L)).willReturn(product);

        given(productService.getProduct(1000L))
                .willThrow(new ProductNotFoundException(1000L));

        given(productService.createProduct(any(ProductData.class)))
                .willReturn(product);

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

        given(authenticationService.parseToken(VALID_TOKEN)).willReturn(1L);

        given(authenticationService.parseToken(INVALID_TOKEN)).willThrow(new InvalidTokenException(INVALID_TOKEN));

    }

    @Test
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
    void createWithAccessToken() throws Exception {
        mockMvc.perform(post("/products")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"쥐돌이\", \"maker\":\"냥이월드\",\"price\":5000}")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isCreated());

        verify(productService).createProduct(any(ProductData.class));
    }

    @Test
    void createWithWrongAccessToken() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"쥐돌이\", \"maker\":\"냥이월드\",\"price\":5000}")
                        .header("Authorization", "Bearer " + INVALID_TOKEN))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createWithoutAccessToken() throws Exception {
        mockMvc.perform(post("/products")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"쥐돌이\", \"maker\":\"냥이월드\",\"price\":5000}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createWithInvalidAttributes() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"\", \"maker\":\"\",\"price\":0}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateWithValidProduct() throws Exception {
        mockMvc.perform(patch("/products/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"쥐순이\", \"maker\":\"순이월드\",\"price\":1000}")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("쥐순이")));

        verify(productService).updateProduct(eq(1L), any(ProductData.class));
    }

    @Test
    void updateWithInValidProduct() throws Exception {
        mockMvc.perform(patch("/products/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"쥐순이\", \"maker\":\"순이월드\",\"price\":1000}")
                        .header("Authorization", "Bearer " + INVALID_TOKEN))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void updateWithNotAccessToken() throws Exception {
        mockMvc.perform(patch("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"쥐순이\", \"maker\":\"순이월드\",\"price\":1000}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateWithNotExsitedProduct() throws Exception {
        mockMvc.perform(patch("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"name\":\"쥐순이\", \"maker\":\"순이월드\",\"price\":1000}")
                        .header("Authorization", "Bearer " + INVALID_TOKEN))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void destroyWithValidProduct() throws Exception {
        mockMvc.perform(delete("/products/1")
                .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isOk());

        verify(productService).deleteProduct(1L);
    }

    @Test
    void destroyWithInvalidProduct() throws Exception {
        mockMvc.perform(delete("/products/1000")
                .header("Authorization", "Bearer " + INVALID_TOKEN))
                .andExpect(status().isNotFound());

        verify(productService).deleteProduct(1000L);
    }
}
