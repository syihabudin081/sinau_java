package com.company;
import com.company.model.Beverage;
import com.company.model.Food;
import com.company.model.Orderable;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static final List<Orderable> menu = new ArrayList<>();

    // Private constructor to hide the implicit public one
    private Data() {}

    static void initializeMenu() {
        menu.add(new Food("Nasi Goreng", 15000));
        menu.add(new Food("Mie Goreng", 12000));
        menu.add(new Food("Ayam Goreng", 10000));
        menu.add(new Food("Ayam Bakar", 15000));
        menu.add(new Food("Rendang", 20000));
        menu.add(new Food("Ayam Sayur", 12000));
        menu.add(new Beverage("Es Teh", 5000, "Medium"));
        menu.add(new Beverage("Es Jeruk", 6000, "Medium"));
        menu.add(new Beverage("Es Campur", 10000, "Large"));
        menu.add(new Beverage("Es Kuwut", 12000, "Large"));
    }

    public static List<Orderable> getMenu() {
        return menu;
    }

    public static void displayMenu() {
        System.out.println("Menu:");
        menu.stream().forEach(item -> {
            if (item instanceof Food) {
                Food foodItem = (Food) item;
                System.out.println(menu.indexOf(item) + 1 + ". " + foodItem.getName() + " - Rp" + foodItem.getPrice());
            } else if (item instanceof Beverage) {
                Beverage beverageItem = (Beverage) item;
                System.out.println(menu.indexOf(item) + 1 + ". " + beverageItem.getName() + " (" + beverageItem.getSize() + ") - Rp" + beverageItem.getPrice());
            }
        });
    }
}
