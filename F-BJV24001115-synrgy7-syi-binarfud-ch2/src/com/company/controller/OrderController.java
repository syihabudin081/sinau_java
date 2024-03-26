package com.company.controller;


import com.company.service.OrderService;
import com.company.view.MenuView;



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

    public void displayMenu() {
        MenuView.displayMenu();
    }

}
