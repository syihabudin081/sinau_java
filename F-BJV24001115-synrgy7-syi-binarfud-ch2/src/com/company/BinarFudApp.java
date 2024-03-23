
package com.company;
import com.company.controller.OrderController;
import com.company.model.*;
import com.company.view.MenuView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BinarFudApp {
    private static final List<Orderable> menu = new ArrayList<>();
    private static final List<Order> orders = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        initializeMenu();
        OrderController orderController = new OrderController(orders, menu, scanner);

        boolean exit = false;

        while (!exit) {
            System.out.println("\n======= BinarFud =======\n");
            System.out.println("1. Lihat Menu");
            System.out.println("2. Pesan Makanan/Minuman");
            System.out.println("3. Konfirmasi dan Bayar");
            System.out.println("4. Keluar");

            try {
                System.out.print("Pilih: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        MenuView.displayMenu(menu);
                        break;
                    case 2:
                        OrderController.placeOrder();
                        break;
                    case 3:
                        OrderController.confirmAndPay();
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Masukkan harus berupa angka!");
                scanner.next(); // Clear the invalid input from scanner
            }
        }

        scanner.close();
    }

    private static void initializeMenu() {
        menu.add(new Food("Nasi Goreng", 15000));
        menu.add(new Food("Mie Goreng", 12000));
        menu.add(new Food("Ayam Goreng", 10000));
        menu.add(new Food("Ayam Bakar", 15000));
        menu.add(new Food("Ayam Sayur", 12000));
        menu.add(new Beverage("Es Teh", 5000));
        menu.add(new Beverage("Es Jeruk", 6000));

    }


}
