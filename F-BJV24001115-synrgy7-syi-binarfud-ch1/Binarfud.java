import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Binarfud {
    static String[] menuItems = {"Nasi Goreng", "Mie Goreng", "Ayam Goreng", "Es Teh", "Es Jeruk"};
    static int[] menuPrices = {15000, 12000, 13000, 3000, 5000};
    static int[] orderQuantities = new int[menuItems.length];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("===== BinarFud Online Ordering System =====");
            System.out.println("1. Lihat Menu");
            System.out.println("2. Pesan Makanan");
            System.out.println("3. Bayar");
            System.out.println("4. Keluar");
            System.out.print("Pilih: ");
            choice = scanner.nextInt();

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
        } while (choice != 4);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("===== Menu BinarFud =====");
        for (int i = 0; i < menuItems.length; i++) {
            System.out.println((i + 1) + ". " + menuItems[i] + " - Rp " + menuPrices[i]);
        }
    }

    private static void confirmOrder(Scanner scanner) {
        int choice;

        do {
            System.out.print("Pilih makanan/minuman [1-5]: ");
            choice = scanner.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("Pilihan tidak valid. Silakan pilih kembali.");
            }
        } while (choice < 1 || choice > 5);

        System.out.print("Jumlah pesanan: ");
        int quantity = scanner.nextInt();

        orderQuantities[choice - 1] += quantity;

        System.out.println("Pesanan Anda telah ditambahkan.");
    }

    private static void payment() {
        try {
            FileWriter fileWriter = new FileWriter("order.txt", true);
            for (int i = 0; i < menuItems.length; i++) {
                if (orderQuantities[i] > 0) {
                    int total = orderQuantities[i] * menuPrices[i];
                    fileWriter.write(menuItems[i] + " " + orderQuantities[i] + " " + total + "\n");
                    System.out.println(menuItems[i] + " - " + orderQuantities[i] + " x Rp " + menuPrices[i] + " = Rp " + total);
                }
            }
            fileWriter.close();
            System.out.println("Pesanan berhasil disimpan!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan pesanan.");
            e.printStackTrace();
        }
    }
}
