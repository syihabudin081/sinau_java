package com.company.model;

public class Order {
    private Orderable item;
    private int quantity;

    public Order(Orderable item, int quantity) {
        this.item = item;
        this.quantity = quantity;
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
}