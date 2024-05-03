package com.example.Binarfud.service;

import com.example.Binarfud.model.ApiResponse;
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
            ApiResponse response = new ApiResponse(HttpStatus.OK, "Products retrieved successfully", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to get products", e);
            ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<?> getProductById(UUID id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                log.info("Product: {}", product.get());
                ApiResponse response = new ApiResponse(HttpStatus.OK, "Product retrieved successfully", product.get());
                return ResponseEntity.ok(response);
            } else {
                log.warn("Product not found");
                ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND, "Product not found", null);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Failed to get product", e);
            ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<?> saveProduct(Product product) {
        try {
            Product newProduct = productRepository.save(product);
            log.info("Product saved: {}", newProduct);
            ApiResponse response = new ApiResponse(HttpStatus.CREATED, "Product saved successfully", newProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Failed to save product", e);
            ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<?> deleteProduct(UUID id) {
        try {
            productRepository.deleteById(id);
            log.info("Product deleted");
            ApiResponse response = new ApiResponse(HttpStatus.OK, "Product deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to delete product", e);
            ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
