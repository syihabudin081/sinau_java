package com.example.Binarfud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.annotations.SoftDelete;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Merchant")
@SoftDelete
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty
    @Column(name = "merchant_name",nullable = false)
    private String merchantName;

    @Column(name = "merchant_location",nullable = false)
    private String merchantLocation;

    @Column(name = "open",nullable = false)
    private boolean open;

}
