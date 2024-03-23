package com.company.view;

import com.company.model.Orderable;

import java.util.List;

public class MenuView {
    public static void displayMenu(List<Orderable> menu) {
        System.out.println("======= Menu =======");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i).getName() + " - Rp" + menu.get(i).getPrice());
        }
    }
}
