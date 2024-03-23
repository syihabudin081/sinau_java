package com.company.view;

import com.company.model.Order;
import com.company.model.Orderable;

import java.util.List;

public class OrderView {
    public static void displayOrderDetails(List<Order> orders) {
        System.out.println("=== Detail Pesanan ===");
        for (Order order : orders) {
            Orderable item = order.getItem();
            int quantity = order.getQuantity();
            double price = item.getPrice();
            double subtotal = price * quantity;
            System.out.println(item.getName() + " - " + quantity + "x - Rp" + price + " = Rp" + subtotal);
        }
    }

    public static void displayTotalPrice(double total) {
        System.out.println("Total Harga: Rp" + total);
    }
}
