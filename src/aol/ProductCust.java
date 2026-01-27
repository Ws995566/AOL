package aol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ProductCust {

    private static final String PRODUCT_FILE = "products.txt";
    private Scanner sc = new Scanner(System.in);
    
    public void productMenuCust() {
        while (true) {
            System.out.println("\n=== PRODUCT MANAGEMENT ===");
            System.out.println("1. View Products");
            System.out.println("2. Search Product");
            System.out.println("3. Back");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showProducts();
                case 2 -> searchProduct();
                case 3 -> {
                    new AdminPage().admhomepg();
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public void showProducts() {
        System.out.println("\n=== AVAILABLE PRODUCTS ===");

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int stock = Integer.parseInt(data[2]);

                if (stock > 0) {
                    found = true;
                    printProduct(data);
                }
            }

            if (!found) {
                System.out.println("No products available.");
            }

        } catch (IOException e) {
            System.out.println("Product file not found.");
        }
    }

    public void searchProduct() {
        System.out.print("Enter product name to search: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                String productName = data[0].toLowerCase();

                if (productName.contains(keyword)) {
                    printProduct(data);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Product not found.");
            }

        } catch (IOException e) {
            System.out.println("Product file not found.");
        }
    }

    private void printProduct(String[] data) {
        System.out.println(
            "Name   : " + data[0] +
            "\nPrice  : " + data[1] +
            "\nStock  : " + data[2]
        );
        System.out.println("--------------------");
    }
}
