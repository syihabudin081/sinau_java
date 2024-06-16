package com.example.Binarfud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Orders")
@SoftDelete
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_time")
    @CreationTimestamp
    private Timestamp orderTime;

    @Column(name = "destination_address", nullable = false)
    private String destinationAddress;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",  updatable = false, nullable = false)
    private Users user;

    @ToString.Exclude
    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties({"order"})
    private List<OrderDetail> orderDetails;

}
