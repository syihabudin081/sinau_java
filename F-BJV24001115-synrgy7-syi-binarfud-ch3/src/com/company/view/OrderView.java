package com.company.view;

import com.company.model.Order;
import com.company.model.Orderable;

import java.util.List;
import java.util.Optional;

public class OrderView {

    // Private constructor to hide the implicit public one
    private OrderView() {}


    public static void displayOrderDetails(List<Order> orders) {
        System.out.println("=== Detail Pesanan ===");
        orders.forEach(order -> {
            Orderable item = order.getItem();
            int quantity = order.getQuantity();
            double price = item.getPrice();
            double subtotal = price * quantity;
            System.out.println(item.getName() + " - " + quantity + "x - Rp" + price + " = Rp" + subtotal);
        });
        double total = orders.stream().mapToDouble(Order::calculateTotalPrice).sum();
        System.out.println("Total Harga: Rp" + total);
    }

    public static Optional<Order> findOrderById(List<Order> orders, String orderId) {
        return orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst();
    }

}
