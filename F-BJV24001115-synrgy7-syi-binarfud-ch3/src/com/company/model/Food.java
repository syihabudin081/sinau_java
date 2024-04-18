package com.company.model;

public class Food extends Product implements Orderable {
    public Food(String name, double price) {
        super(name, price);
    }
}