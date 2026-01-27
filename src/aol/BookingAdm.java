package aol;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingAdm {

    private static final String BOOKING_FILE = "bookings.txt";
    private Scanner sc = new Scanner(System.in);
    private static final String WORKER_FILE = "workers.txt";


    public void bookingMenuAdm() {
        while (true) {
            System.out.println("\n=== BOOKING ADMIN MENU ===");
            System.out.println("1. View All Bookings");
            System.out.println("2. Assign Worker");
            System.out.println("3. Back");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showAllBookings();
                case 2 -> assignWorker();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public void showAllBookings() {
        System.out.println("\n=== ALL BOOKINGS ===");

        try (BufferedReader br = new BufferedReader(new FileReader(BOOKING_FILE))) {
            String line;
            int index = 1;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 5) continue;

                System.out.println("[" + index + "]");
                System.out.println("Customer : " + data[0]);
                System.out.println("Date     : " + data[1]);
                System.out.println("Vehicle  : " + data[2]);
                System.out.println("Problem  : " + data[3]);
                System.out.println("Worker   : " + data[4]);
                System.out.println("----------------------");

                index++;
            }

            if (index == 1) {
                System.out.println("No bookings available.");
            }

        } catch (IOException e) {
            System.out.println("Booking file not found.");
        }
    }

    public void assignWorker() {
        System.out.print("Enter customer email: ");
        String email = sc.nextLine();

        System.out.print("Enter booking date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        System.out.print("Enter worker name: ");
        String workerName = sc.nextLine();

        List<String> bookings = new ArrayList<>();
        List<String> workers = new ArrayList<>();

        boolean bookingFound = false;
        boolean workerFound = false;
        boolean workerAvailable = false;

        try (BufferedReader br = new BufferedReader(new FileReader(WORKER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                workers.add(line);
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(workerName)) {
                    workerFound = true;
                    if (data[1].equalsIgnoreCase("AVAILABLE")) {
                        workerAvailable = true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Worker file not found.");
            return;
        }

        if (!workerFound) {
            System.out.println("Worker not found.");
            return;
        }

        if (!workerAvailable) {
            System.out.println("Worker not available at the moment.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(BOOKING_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) {
                    bookings.add(line);
                    continue;
                }

                if (data[0].equalsIgnoreCase(email) && data[1].equals(date)) {
                    data[4] = workerName;
                    bookings.add(String.join(",", data));
                    bookingFound = true;
                } else {
                    bookings.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Booking file error.");
            return;
        }

        if (!bookingFound) {
            System.out.println("Booking not found.");
            return;
        }

        for (int i = 0; i < workers.size(); i++) {
            String[] data = workers.get(i).split(",");
            if (data[0].equalsIgnoreCase(workerName)) {
                data[1] = "BUSY";
                workers.set(i, String.join(",", data));
                break;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKING_FILE))) {
            for (String b : bookings) {
                bw.write(b);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save booking.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(WORKER_FILE))) {
            for (String w : workers) {
                bw.write(w);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to update worker.");
            return;
        }

        System.out.println("Worker assigned successfully.");
    }


}
