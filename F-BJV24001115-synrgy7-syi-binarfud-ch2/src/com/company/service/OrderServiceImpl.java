// OrderServiceImpl.java
package com.company.service;

import com.company.model.Order;
import com.company.model.Orderable;
import com.company.view.MenuView;
import com.company.view.OrderView;

import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OrderServiceImpl implements OrderService {
    private List<Order> orders;
    private List<Orderable> menu;
    private Scanner scanner;

    public OrderServiceImpl(List<Order> orders, List<Orderable> menu, Scanner scanner) {
        this.orders = orders;
        this.menu = menu;
        this.scanner = scanner;
    }

    @Override
    public void placeOrder() {
        MenuView.displayMenu();
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

    @Override
    public void confirmAndPay() {
        if (orders.isEmpty()) {
            System.out.println("Belum ada pesanan yang dibuat!");
            return;
        }

        OrderView.displayOrderDetails(orders);
        

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

    @Override
    public void saveOrderHistory() {
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
