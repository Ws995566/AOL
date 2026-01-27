package aol;

import java.io.*;
import java.util.Scanner;

public class WorkerAdm {

    private static final String WORKERS_FILE = "workers.txt";
    private Scanner sc = new Scanner(System.in);

    public void workerMenuAdm() {
        while (true) {
            System.out.println("\n=== Worker Management ===");
            System.out.println("1. View Worker");
            System.out.println("2. Add Worker");
            System.out.println("3. Remove Worker");
            System.out.println("4. Back");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewWorker();
                case 2 -> addWorker();
                case 3 -> removeWorker();
                case 4 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void viewWorker() {
        System.out.println("\n=== WORKER LIST ===");
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(WORKERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 2) continue;

                System.out.println("Name   : " + data[0]);
                System.out.println("Status : " + data[1]);
                System.out.println("--------------------");
                found = true;
            }
        } catch (IOException e) {
            System.out.println("No worker data found.");
        }

        if (!found) {
            System.out.println("No workers available.");
        }
    }

    private void addWorker() {
        System.out.print("Worker Name: ");
        String name = sc.nextLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(WORKERS_FILE, true))) {
            bw.write(name + ",AVAILABLE");
            bw.newLine();
            System.out.println("Worker added successfully.");
        } catch (IOException e) {
            System.out.println("Failed to add worker.");
        }
    }

    private void removeWorker() {
        System.out.print("Enter worker name to remove: ");
        String target = sc.nextLine();

        File temp = new File("workers_temp.txt");
        boolean found = false;

        try (
            BufferedReader br = new BufferedReader(new FileReader(WORKERS_FILE));
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp))
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
            System.out.println("Error removing worker.");
            return;
        }

        new File(WORKERS_FILE).delete();
        temp.renameTo(new File(WORKERS_FILE));

        System.out.println(found ? "Worker removed." : "Worker not found.");
    }
}
