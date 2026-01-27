package aol;

import java.util.Scanner;

public class CustPage {

    private Scanner sc = new Scanner(System.in);

    public void custhomepg() {
        while (true) {
            System.out.println("\nWelcome at Customer Page");
            System.out.println("1. Booking Service");
            System.out.println("2. View Product");
            System.out.println("3. View News");
            System.out.println("4. Testimoni");
            System.out.println("5. Log Out");
            System.out.print("Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> bookingMenu();
                case 2 -> new ProductCust().productMenuCust();
                case 3 -> new NewsCust().newsMenuCust();
                case 4 -> new TestimoniCust().testimoniMenuCust();
                case 5 -> logout();
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void bookingMenu() {
        new BookingCust().customerBookingMenu();
    }

    private void logout() {
        Session.currentUserEmail = null;
        System.out.println("Logged out.\n");
        new Login().displayLogin();
    }
}
