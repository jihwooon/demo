//REST
// /products -> Create, Read
// /products/{id} -> Read, Update, Delete
package com.codesoom.demo.controller;

import com.codesoom.demo.application.ProductService;
import com.codesoom.demo.domain.Product;
import com.codesoom.demo.dto.ProductData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return productService.createProduct(productData);
    }

    @PatchMapping("{id}")
    public Product update(@PathVariable Long id, @RequestBody @Valid ProductData productData) {
        return productService.updateProduct(id, productData);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
