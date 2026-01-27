package aol;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Set;

public class BookingCust {

    private static final String BOOKING_FILE = "bookings.txt";

    private LocalDate bookingDate;
    private String vehicleName;
    private String problemDescription;

    private Scanner sc = new Scanner(System.in);

    public void customerBookingMenu() {
        while (true) {
            System.out.println("\n=== Booking Menu ===");
            System.out.println("1. Book Service");
            System.out.println("2. View My Booking");
            System.out.println("3. Back");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> runBooking();
                case 2 -> viewMyBookings();
                case 3 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void dateInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            System.out.print("Enter Date (DD-MM-YYYY): ");
            try {
                bookingDate = LocalDate.parse(sc.nextLine(), formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Wrong format. Try again.");
            }
        }
    }

    private void nameInput() {
        Set<String> validNames = Set.of("mobil", "motor", "car", "motorcycle");

        while (true) {
            System.out.print("Enter Vehicle Name: ");
            vehicleName = sc.nextLine().toLowerCase();

            if (validNames.contains(vehicleName)) break;
            System.out.println("Invalid vehicle. Try again.");
        }

        System.out.print("Problem Description: ");
        problemDescription = sc.nextLine();
    }

    private void saveBooking() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKING_FILE, true))) {
            bw.write(
                Session.currentUserEmail + "," +
                bookingDate + "," +
                vehicleName + "," +
                problemDescription + "," +
                "UNASSIGNED"
            );
            bw.newLine();
            System.out.println("Booking saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save booking.");
        }
    }



    private void runBooking() {
        dateInput();
        nameInput();
        saveBooking();
    }

    private void viewMyBookings() {
        boolean found = false;

        System.out.println("\n=== MY BOOKINGS ===");

        try (BufferedReader br = new BufferedReader(new FileReader(BOOKING_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                if (data.length < 5) continue;
                if (data[0].equalsIgnoreCase(Session.currentUserEmail)) {
                    System.out.println(
                        "Date    : " + data[1] +
                        "\nVehicle : " + data[2] +
                        "\nProblem : " + data[3] +
                        "\nWorker  : " + data[4]
                    );
                    System.out.println("--------------------");
                    found = true;
                }
            }
            if (!found) {
                System.out.println("You have no bookings.");
            }
        } catch (IOException e) {
            System.out.println("Booking file not found.");
        }
    }
}
