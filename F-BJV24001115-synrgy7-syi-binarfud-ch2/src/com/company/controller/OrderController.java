package com.company.controller;

import com.company.model.Orderable;
import com.company.service.OrderService;
import com.company.view.MenuView;

import java.util.List;
import java.util.Scanner;

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void placeOrder() {
        orderService.placeOrder();
    }

    public void confirmAndPay() {
        orderService.confirmAndPay();
    }

    public void saveOrderHistory() {
        orderService.saveOrderHistory();
    }

    public void displayMenu(List<Orderable> menu) {
        MenuView.displayMenu();
    }

    public int getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
