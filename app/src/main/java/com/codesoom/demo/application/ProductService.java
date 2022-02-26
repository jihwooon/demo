package com.codesoom.demo.application;

import com.codesoom.demo.ProductNotFoundException;
import com.codesoom.demo.domain.Product;
import com.codesoom.demo.domain.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return findProduct(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product source) {
        Product product = findProduct(id);
        product.change(source);
        return product;
    }

    public Product deleteProduct(Long id) {
        Product product = findProduct(id);
        productRepository.delete(product);
        return product;
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
