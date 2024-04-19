package com.company.service;

import com.company.model.Food;
import com.company.model.Order;
import com.company.model.Orderable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    private OrderServiceImpl orderService;
    private List<Order> orders;
    private List<Orderable> menu;
    private InputStream sysInBackup;

    @BeforeEach
    void setUp() {
        orders = new ArrayList<>();
        menu = new ArrayList<>();
        menu.add(new Food("Nasi Goreng", 15000));

        sysInBackup = System.in;

    }

    @Test
    void placeOrder_Success() {
        String simulatedInput = "1\n2";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        orderService = new OrderServiceImpl(orders, menu, new Scanner(System.in));

        orderService.placeOrder();
        assertEquals(1, orders.size());
        assertEquals("Nasi Goreng", orders.get(0).getItem().getName());
        assertEquals(2, orders.get(0).getQuantity());
    }

    @Test
    void confirmAndPay_Success() {
        orders.add(new Order(new Food("Nasi Goreng", 15000), 2));
        // Simulasi input yang mencoba memesan item yang tidak ada dalam menu
        String simulatedInput = "1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        orderService = new OrderServiceImpl(orders, menu, new Scanner(System.in));

        orderService.confirmAndPay();
        assertTrue(orders.isEmpty());
    }


    @Test
    void placeOrder_ItemNotInMenu_Failure() {
        // Simulasi input yang mencoba memesan item yang tidak ada dalam menu
        String simulatedInput = "5\n2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        orderService = new OrderServiceImpl(orders, menu, new Scanner(System.in));


        orderService.placeOrder();

        assertEquals(0, orders.size()); // Tidak seharusnya ada pesanan yang terbuat
    }


    @Test
    void confirmAndPay_NoOrders_Failure() {
        // Pastikan tidak ada pesanan yang ditambahkan sebelumnya
        orderService = new OrderServiceImpl(orders, menu, new Scanner(System.in));

        assertTrue(orders.isEmpty());

        // Coba konfirmasi dan membayar tanpa ada pesanan
        orderService.confirmAndPay();

        // Pastikan tidak ada yang berubah setelah mencoba konfirmasi dan membayar tanpa pesanan
        assertTrue(orders.isEmpty());
    }


    @Test
    void placeOrder_InvalidInput_Failure() {
        // Simulasi input yang mencoba memesan item yang tidak ada dalam menu
        String simulatedInput = "5\n2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        orderService = new OrderServiceImpl(orders, menu, new Scanner(System.in));


         orderService.placeOrder();

        // Pastikan tidak ada pesanan yang terbuat karena input tidak valid
        assertEquals(0, orders.size());
    }
}