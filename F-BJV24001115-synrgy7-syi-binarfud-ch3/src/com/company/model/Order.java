package com.company.model;

import java.util.UUID;

public class Order {
    private Orderable item;
    private int quantity;
    private String id;


    public Order(Orderable item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.id = UUID.randomUUID().toString();
    }


    public Orderable getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double calculateTotalPrice() {
        return item.getPrice() * quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}