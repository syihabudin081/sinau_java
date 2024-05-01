package com.example.Binarfud.service;

import com.example.Binarfud.model.Product;
import com.example.Binarfud.repository.MerchantRepository;
import com.example.Binarfud.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public ResponseEntity<?> getAllProducts(Pageable pageable) {
        try {
            Page<Product> products = productRepository.findAll(pageable);
            log.info("Products: {}", products);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            log.error("Failed to get products", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity<?> getProductById(UUID id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                log.info("Product: {}", product.get());
                return ResponseEntity.ok(product.get());
            } else {
                log.warn("Product not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Failed to get product", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity<?> saveProduct(Product product) {
        try {
            Product newProduct = productRepository.save(product);
            log.info("Product saved: {}", newProduct);

            return ResponseEntity.status(HttpStatus.CREATED).body("Product saved successfully");
        } catch (Exception e) {
            log.error("Failed to save product", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> deleteProduct(UUID id) {
        try {
            productRepository.deleteById(id);
            log.info("Product deleted");
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete product", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
