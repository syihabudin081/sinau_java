import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Binarfud {

    private static final String[] MENU_ITEMS = {
            "Nasi Goreng", "Mie Goreng", "Ayam Goreng", "Es Teh", "Es Jeruk"
    };
    private static final double[] MENU_PRICES = {
            15000, 12000, 13000, 3000, 5000
    };
    private static final int[] orderQuantities = new int[MENU_ITEMS.length];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenuOptions();
            choice = getUserChoice(scanner);
            processChoice(choice, scanner);
        } while (choice != 4);

        scanner.close();
    }

    private static void displayMenuOptions() {
        System.out.println("===== BinarFud Online Ordering System =====");
        System.out.println("1. Lihat Menu");
        System.out.println("2. Pesan Makanan");
        System.out.println("3. Bayar");
        System.out.println("4. Keluar");
    }

    private static int getUserChoice(Scanner scanner) {
        System.out.print("Pilih: ");
        return scanner.nextInt();
    }

    private static void processChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                displayMenu();
                break;
            case 2:
                confirmOrder(scanner);
                break;
            case 3:
                payment();
                break;
            case 4:
                System.out.println("Terima kasih telah menggunakan BinarFud Online Ordering System!");
                break;
            default:
                System.out.println("Pilihan tidak valid. Silakan pilih kembali.");
        }
    }

    private static void displayMenu() {
        System.out.println("===== Menu BinarFud =====");
        for (int i = 0; i < MENU_ITEMS.length; i++) {
            System.out.println((i + 1) + ". " + MENU_ITEMS[i] + " - Rp " + MENU_PRICES[i]);
        }
    }

    private static void confirmOrder(Scanner scanner) {
        int itemChoice;
        int quantity;

        do {
            System.out.print("Pilih makanan/minuman [1-5]: ");
            itemChoice = scanner.nextInt();
            if (itemChoice < 1 || itemChoice > MENU_ITEMS.length) {
                System.out.println("Pilihan tidak valid. Silakan pilih kembali.");
            }
        } while (itemChoice < 1 || itemChoice > MENU_ITEMS.length);

        System.out.print("Jumlah pesanan: ");
        quantity = scanner.nextInt();
        orderQuantities[itemChoice - 1] += quantity;
        System.out.println("Pesanan Anda telah ditambahkan.");
    }

    private static void payment() {
        try (FileWriter fileWriter = new FileWriter("order.txt", true)) {
            double totalOrder = 0;
            for (int i = 0; i < MENU_ITEMS.length; i++) {
                if (orderQuantities[i] > 0) {
                    double totalPrice = orderQuantities[i] * MENU_PRICES[i];
                    fileWriter.write(MENU_ITEMS[i] + " " + orderQuantities[i] + " " + totalPrice + "\n");
                    System.out.println(MENU_ITEMS[i] + " - " + orderQuantities[i] + " x Rp " + MENU_PRICES[i] + " = Rp " + totalPrice);
                    totalOrder += totalPrice;
                }
            }
            System.out.println("Total pesanan: Rp " + totalOrder);
            System.out.println("Pesanan berhasil disimpan!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan pesanan.");
            e.printStackTrace();
        }
    }
}
