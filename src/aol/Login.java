package aol;

import java.io.*;
import java.util.Scanner;

public class Login {
    
    private static final String USER_FILE = "users.txt";
    private Scanner scanner = new Scanner(System.in);

    public void displayLogin() {
        System.out.println("Welcome to HelloMMS");
        showMenu();
    }

    public void showMenu() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                login();
            case 2:
                register();
            case 3:
                System.out.println("Goodbye");
                System.exit(0);
            default:
                System.out.println("Invalid choice");
                showMenu();
        }
    }

    private void login() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 3) continue;

                if (data[0].equals(email) && data[1].equals(password)) {
                    System.out.println("Login successful");

                    // SIMPAN EMAIL KE SESSION
                    Session.currentUserEmail = email;

                    // ARAHKAN BERDASARKAN ROLE
                    if (data[2].equalsIgnoreCase("ADMIN")) {
                        new AdminPage().admhomepg();
                    } else {
                        new CustPage().custhomepg();
                    }
                    return;
                }
            }

            System.out.println("Invalid email or password");

        } catch (IOException e) {
            System.out.println("User file error.");
        }
    }



    private void register() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            if (userExists(email)) {
                System.out.println("User already exists.");
                return;
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE, true))) {
                bw.write(email + "," + password + ",CUSTOMER");
                bw.newLine();
            }

            System.out.println("Registration successful. Please login.");

        } catch (IOException e) {
            System.out.println("Error saving user.");
        }
    }

    private boolean userExists(String email) throws IOException {
        File file = new File(USER_FILE);
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(email + ",")) {
                    return true;
                }
            }
        }
        return false;
    }
}
