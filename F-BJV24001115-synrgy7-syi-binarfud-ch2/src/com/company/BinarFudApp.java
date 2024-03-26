
package com.company;
import com.company.controller.OrderController;
import com.company.model.*;
import com.company.service.OrderServiceImpl;
import com.company.view.MenuView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BinarFudApp {
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        Data.initializeMenu();

        OrderServiceImpl orderService = new OrderServiceImpl(new ArrayList<>(), Data.getMenu(), scanner);
        OrderController orderController = new OrderController(orderService);

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
                        orderController.displayMenu();
                        break;
                    case 2:
                        orderController.placeOrder();
                        break;
                    case 3:
                        orderController.confirmAndPay();
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




}
