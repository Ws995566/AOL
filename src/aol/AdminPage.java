package aol;

import java.util.Scanner;

public class AdminPage {

    private Scanner sc = new Scanner(System.in);

    public void admhomepg() {
        while (true) {
            System.out.println("\nWelcome at Admin Page");
            System.out.println("1. Manage Booking");
            System.out.println("2. Manage Product");
            System.out.println("3. Manage News");
            System.out.println("4. Manage Testimoni");
            System.out.println("5. Manage Worker");
            System.out.println("6. Log Out");
            System.out.print("Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> new BookingAdm().bookingMenuAdm();
                case 2 -> new ProductAdm().productMenuAdm();
                case 3 -> new NewsAdm().newsMenuAdm();
                case 4 -> new TestimoniAdm().testimoniMenuAdm();
                case 5 -> new WorkerAdm().workerMenuAdm();
                case 6 -> logout();
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void logout() {
        Session.currentUserEmail = null;
        System.out.println("Logged out.\n");
        new Login().displayLogin();
    }

}
