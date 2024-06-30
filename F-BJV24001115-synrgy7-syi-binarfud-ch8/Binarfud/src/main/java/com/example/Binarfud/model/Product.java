package com.example.Binarfud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "Product")
@SoftDelete
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_name", nullable = false)
    private String product_name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private int quantity;


    @ManyToOne
    @JoinColumn(name = "merchant_id", referencedColumnName = "id", nullable = false,  updatable = false)
    @JsonIgnoreProperties({"merchantLocation", "open", "products"})
    private Merchant merchant;

}

