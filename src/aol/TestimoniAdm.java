package aol;

import java.io.*;
import java.util.Scanner;

public class TestimoniAdm {

    private static final String TESTIMONI_FILE = "testimoni.txt";
    private Scanner sc = new Scanner(System.in);

    public void testimoniMenuAdm() {
        while (true) {
            System.out.println("\n=== Testimoni Admin Menu ===");
            System.out.println("1. View All Testimoni");
            System.out.println("2. Delete Testimoni");
            System.out.println("3. Highlight / Unhighlight Testimoni");
            System.out.println("4. Back");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showAllTestimoni();
                case 2 -> deleteTestimoni();
                case 3 -> toggleHighlight();
                case 4 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public void showAllTestimoni() {
        System.out.println("\n=== ALL TESTIMONI ===");
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(TESTIMONI_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 5) continue;

                found = true;
                printTestimoni(data);
            }

            if (!found) {
                System.out.println("No testimoni available.");
            }

        } catch (IOException e) {
            System.out.println("Testimoni file not found.");
        }
    }

    public void deleteTestimoni() {
        System.out.print("Enter testimoni title to delete: ");
        String target = sc.nextLine();

        File tempFile = new File("temp_testimoni.txt");
        boolean deleted = false;

        try (
            BufferedReader br = new BufferedReader(new FileReader(TESTIMONI_FILE));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 5) continue;

                if (data[1].equalsIgnoreCase(target)) {
                    deleted = true;
                    continue;
                }

                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error deleting testimoni.");
            return;
        }

        replaceFile(tempFile);
        System.out.println(deleted ? "Testimoni deleted." : "Testimoni not found.");
    }

    public void toggleHighlight() {
        System.out.print("Enter testimoni title to toggle highlight: ");
        String target = sc.nextLine();

        File tempFile = new File("temp_testimoni.txt");
        boolean updated = false;

        try (
            BufferedReader br = new BufferedReader(new FileReader(TESTIMONI_FILE));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 5) continue;

                if (data[1].equalsIgnoreCase(target)) {
                    data[4] = data[4].equalsIgnoreCase("YES") ? "NO" : "YES";
                    updated = true;
                }

                bw.write(String.join(",", data));
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error updating highlight.");
            return;
        }

        replaceFile(tempFile);
        System.out.println(updated ? "Highlight updated." : "Testimoni not found.");
    }

    private void replaceFile(File tempFile) {
        File original = new File(TESTIMONI_FILE);
        original.delete();
        tempFile.renameTo(original);
    }

    private void printTestimoni(String[] data) {
        System.out.println(
            "User      : " + data[0] +
            "\nTitle     : " + data[1] +
            "\nContent   : " + data[2] +
            "\nDate      : " + data[3] +
            "\nHighlight : " + data[4]
        );
        System.out.println("--------------------");
    }
}
