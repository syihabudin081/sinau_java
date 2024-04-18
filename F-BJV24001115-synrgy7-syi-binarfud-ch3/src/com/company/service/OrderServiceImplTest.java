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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        String simulatedInput = "1\n2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        orderService = new OrderServiceImpl(orders, menu, new Scanner(System.in));

    }

    @Test
    void placeOrder() {
        orderService.placeOrder();
        assertEquals(1, orders.size());
        assertEquals("Nasi Goreng", orders.get(0).getItem().getName());
        assertEquals(2, orders.get(0).getQuantity());
    }

    @Test
    void confirmAndPay() {
        // Implementasi pengujian untuk confirmAndPay()
        orders.add(new Order(new Food("Nasi Goreng", 15000), 2)); // Menambahkan pesanan untuk pengujian// Menambahkan pesanan untuk pengujian
        orderService.confirmAndPay();
        assertTrue(orders.isEmpty()); // Memastikan bahwa daftar pesanan kosong setelah konfirmasi dan pembayaran
    }

    @Test
    void saveOrderHistory() {
        // Implementasi pengujian untuk saveOrderHistory()
        orderService.saveOrderHistory();
        // Anda dapat menambahkan pengujian lebih lanjut untuk memeriksa apakah riwayat pesanan telah disimpan dengan benar
        File file = new File("order_history.txt");
        assertTrue(file.exists());
    }
}

