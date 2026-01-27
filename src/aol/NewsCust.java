package aol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class NewsCust {
    private static final String NEWS_FILE = "news.txt";
    private Scanner sc = new Scanner(System.in);
    
    public void newsMenuCust() {
        while (true) {
            System.out.println("\n===  News Menu ===");
            System.out.println("1. View News");
            System.out.println("2. Search News");
            System.out.println("3. Back");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewNews();
                case 2 -> searchNews();
                case 3 -> {
                    new AdminPage().admhomepg();
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
    
    public void viewNews() {
        System.out.println("\n=== NEWS ===");

        try (BufferedReader br = new BufferedReader(new FileReader(NEWS_FILE))) {
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 4) {
                    continue; 
                }

                found = true;
                System.out.println(
                    "Title       : " + data[0] +
                    "\nSub Title   : " + data[1] +
                    "\nDescription : " + data[2] +
                    "\nDate        : " + data[3]
                );
                System.out.println("--------------------");
            }

            if (!found) {
                System.out.println("No news available.");
            }

        } catch (IOException e) {
            System.out.println("News file not found.");
        }
    }

    public void searchNews(){
        System.out.print("Enter News title to search: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(NEWS_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                String title = data[0].toLowerCase();

                if (title.contains(keyword)) {
                    displayNews(data);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("News not found.");
            }

        } catch (IOException e) {
            System.out.println("News file not found.");
        }
    }
    
    private void displayNews(String[] data) {
        System.out.println(
            "Title   : " + data[0] +
            "\nSubtitle  : " + data[1] +
            "\nDesc  : " + data[2] +
            "\nDate: " + data[3]
        );
        System.out.println("--------------------");
    }
}
