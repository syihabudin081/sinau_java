package com.example.Binarfud.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "Order_Detail")
@SoftDelete
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id",  updatable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", updatable = false)
    private Product product;
}
