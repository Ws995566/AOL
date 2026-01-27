package aol;

import java.io.*;
import java.util.Scanner;

public class ProductAdm {

    private static final String PRODUCT_FILE = "products.txt";
    private Scanner sc = new Scanner(System.in);

    public void productMenuAdm() {
        while (true) {
            System.out.println("\n=== PRODUCT MANAGEMENT ===");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Back");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewProducts();
                case 2 -> addProduct();
                case 3 -> updateProduct();
                case 4 -> deleteProduct();
                case 5 -> {
                    new AdminPage().admhomepg();
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void viewProducts() {
        System.out.println("\n=== PRODUCT LIST ===");

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println(
                        "Name: " + data[0] +
                        " | Price: " + data[1] +
                        " | Stock: " + data[2]
                );
            }
        } catch (IOException e) {
            System.out.println("No products found.");
        }
    }

    private void addProduct() {
        System.out.print("Product Name: ");
        String name = sc.nextLine();

        System.out.print("Price: ");
        int price = sc.nextInt();

        System.out.print("Stock: ");
        int stock = sc.nextInt();
        sc.nextLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCT_FILE, true))) {
            bw.write(name + "," + price + "," + stock);
            bw.newLine();
            System.out.println("Product added.");
        } catch (IOException e) {
            System.out.println("Failed to add product.");
        }
    }

    private void updateProduct() {
        System.out.print("Enter product name to update: ");
        String target = sc.nextLine();

        File tempFile = new File("temp.txt");
        boolean found = false;

        try (
            BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data[0].equalsIgnoreCase(target)) {
                    found = true;

                    System.out.print("New Price: ");
                    int newPrice = sc.nextInt();

                    System.out.print("New Stock: ");
                    int newStock = sc.nextInt();
                    sc.nextLine();

                    bw.write(data[0] + "," + newPrice + "," + newStock);
                } else {
                    bw.write(line);
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating product.");
            return;
        }

        replaceFile(tempFile);
        System.out.println(found ? "Product updated." : "Product not found.");
    }

    private void deleteProduct() {
        System.out.print("Enter product name to delete: ");
        String target = sc.nextLine();

        File tempFile = new File("temp.txt");
        boolean found = false;

        try (
            BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data[0].equalsIgnoreCase(target)) {
                    found = true;
                    continue;
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error deleting product.");
            return;
        }

        replaceFile(tempFile);
        System.out.println(found ? "Product deleted." : "Product not found.");
    }

    private void replaceFile(File tempFile) {
        File original = new File(PRODUCT_FILE);
        original.delete();
        tempFile.renameTo(original);
    }
}

