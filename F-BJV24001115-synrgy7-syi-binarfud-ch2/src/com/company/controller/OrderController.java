package com.company.controller;

import com.company.model.Order;
import com.company.model.Orderable;
import com.company.view.MenuView;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OrderController {
    private static List<Order> orders;
    private static List<Orderable> menu;
    private static Scanner scanner;

    public OrderController(List<Order> orders, List<Orderable> menu, Scanner scanner) {
        this.orders = orders;
        this.menu = menu;
        this.scanner = scanner;
    }

    public static void placeOrder() {
        MenuView.displayMenu(menu);
        System.out.println("Pilih menu (masukkan nomor): ");
        try {
            int itemIndex = scanner.nextInt() - 1;
            if (itemIndex < 0 || itemIndex >= menu.size()) {
                System.out.println("Menu tidak tersedia!");
                return;
            }

            Orderable selectedItem = menu.get(itemIndex);
            System.out.println("Masukkan jumlah pesanan: ");
            int quantity = scanner.nextInt();
            if (quantity <= 0) {
                System.out.println("Jumlah pesanan tidak valid!");
                return;
            }

            Order order = new Order(selectedItem, quantity);
            orders.add(order);
            System.out.println("Pesanan berhasil ditambahkan!");
        } catch (InputMismatchException e) {
            System.out.println("Input jumlah pesanan harus berupa angka!");
            scanner.next(); // Clear the invalid input from scanner
        }
    }

    public static void confirmAndPay() {
        if (orders.isEmpty()) {
            System.out.println("Belum ada pesanan yang dibuat!");
            return;
        }

        System.out.println("=== Detail Pesanan ===");
        for (Order order : orders) {
            Orderable item = order.getItem();
            int quantity = order.getQuantity();
            double price = item.getPrice();
            double subtotal = price * quantity;
            System.out.println(item.getName() + " - " + quantity + "x - Rp" + price + " = Rp" + subtotal);
        }
        double total = orders.stream().mapToDouble(Order::calculateTotalPrice).sum();
        System.out.println("Total Harga: Rp" + total);

        System.out.println("Pilihan:");
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke Menu Utama");
        System.out.println("3. Keluar");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                saveOrderHistory();
                break;
            case 2:
                // Kembali ke menu utama
                break;
            case 3:
                System.out.println("Keluar aplikasi...");
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }

    public static void saveOrderHistory() {
        try( FileWriter writer = new FileWriter("order_history.txt");) {
            writer.write("=== Detail Pesanan ===\n");
            for (Order order : orders) {
                Orderable item = order.getItem();
                int quantity = order.getQuantity();
                double price = item.getPrice();
                double subtotal = price * quantity;
                writer.write(item.getName() + " - " + quantity + "x - Rp" + price + " = Rp" + subtotal + "\n");
            }
            double total = orders.stream().mapToDouble(Order::calculateTotalPrice).sum();
            writer.write("Total Harga: Rp" + total + "\n");
            writer.close();
            System.out.println("Riwayat pesanan berhasil disimpan!");
        } catch (Exception e) {
            System.out.println("Gagal menyimpan riwayat pesanan!");
        }
    }


}

