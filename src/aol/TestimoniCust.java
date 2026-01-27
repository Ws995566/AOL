package aol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TestimoniCust {

    private static final String TESTIMONI_FILE = "testimoni.txt";
    private Scanner sc = new Scanner(System.in);

    public void testimoniMenuCust() {
        while (true) {
            System.out.println("\n=== Testimoni Menu ===");
            System.out.println("1. View My Testimoni");
            System.out.println("2. View All Testimoni");
            System.out.println("3. Search Testimoni");
            System.out.println("4. Back");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showMyTestimoni();
                case 2 -> showAllTestimoni();
                case 3 -> searchTestimoni();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public void showMyTestimoni() {
        String email = Session.currentUserEmail;
        boolean found = false;

        System.out.println("\n=== MY TESTIMONI ===");

        try (BufferedReader br = new BufferedReader(new FileReader(TESTIMONI_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 5) continue;

                if (data[0].equalsIgnoreCase(email)) {
                    printTestimoni(data);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("You have no testimoni.");
            }

        } catch (IOException e) {
            System.out.println("Testimoni file not found.");
        }
    }


    public void showAllTestimoni() {
        boolean found = false;

        System.out.println("\n=== HIGHLIGHTED TESTIMONI ===");

        try (BufferedReader br = new BufferedReader(new FileReader(TESTIMONI_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 5) continue;

                if (data[4].equalsIgnoreCase("YES")) {
                    printTestimoni(data);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No highlighted testimoni available.");
            }

        } catch (IOException e) {
            System.out.println("Testimoni file not found.");
        }
    }


    public void searchTestimoni() {
        System.out.print("Enter testimoni title keyword: ");
        String keyword = sc.nextLine().toLowerCase();
        boolean found = false;

        System.out.println("\n=== SEARCH RESULT ===");

        try (BufferedReader br = new BufferedReader(new FileReader(TESTIMONI_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 5) continue;

                if (data[4].equalsIgnoreCase("YES") &&
                    data[1].toLowerCase().contains(keyword)) {

                    printTestimoni(data);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No matching highlighted testimoni found.");
            }

        } catch (IOException e) {
            System.out.println("Testimoni file not found.");
        }
    }


    private void printTestimoni(String[] data) {
        System.out.println(
            "User      : " + data[0] +
            "\nTitle     : " + data[1] +
            "\nText      : " + data[2] +
            "\nDate      : " + data[3] +
            "\nHighlight : " + data[4]
        );
        System.out.println("--------------------");
    }

}
