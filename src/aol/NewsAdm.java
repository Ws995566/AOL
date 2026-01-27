package aol;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NewsAdm {
    
    private static final String NEWS_FILE = "news.txt";
    private Scanner sc = new Scanner(System.in);
    private LocalDate  newsDate;
    
    public void newsMenuAdm(){
        while(true){
            System.out.println("\n1. View News");
            System.out.println("2. Add News");
            System.out.println("3. Delete News");
            System.out.println("4. Back");
            System.out.println("Choice : ");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice){
                case 1 -> viewNews();
                case 2 -> addNews();
                case 3 -> deleteNews();
                case 4 -> {
                    return;
                }
                
                default -> System.out.println("Invalid Choice");
            }            
        }        
    }
    
    public void viewNews() {
        System.out.println("\n=== LATEST NEWS ===");

        try (BufferedReader br = new BufferedReader(new FileReader(NEWS_FILE))) {
            String line;
            boolean hasNews = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                hasNews = true;
                System.out.println(
                    "Title       : " + data[0] +
                    "\nSub Title   : " + data[1] +
                    "\nDescription : " + data[2] +
                    "\nDate        : " + data[3]
                );
                System.out.println("--------------------");
            }
            if (!hasNews) {
                System.out.println("No news available.");
            }

        } catch (IOException e) {
            System.out.println("No news file found.");
        }
    }
    
    public void addNews(){
        System.out.print("News Title: ");
        String title = sc.nextLine();

        System.out.print("Sub Title: ");
        String subtitle = sc.nextLine();

        System.out.print("Description: ");
        String desc = sc.nextLine();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        while (true) {
            System.out.print("Enter Date (DD-MM-YYYY): ");
            try {
                newsDate = LocalDate.parse(sc.nextLine(), formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Wrong format. Try again.");
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NEWS_FILE, true))) {
            bw.write(title + "," + subtitle + "," + desc + "," + newsDate);
            bw.newLine();
            System.out.println("News added.");
        } catch (IOException e) {
            System.out.println("Failed to add news.");
        }
    }
    
    public void deleteNews(){
        System.out.print("Enter news title to delete: ");
        String target = sc.nextLine();

        File tempFile = new File("temp.txt");
        boolean found = false;

        try (
            BufferedReader br = new BufferedReader(new FileReader(NEWS_FILE));
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
            System.out.println("Error deleting news.");
            return;
        }

        replaceFile(tempFile);
        System.out.println(found ? "News deleted." : "News not found.");
    }
    
    private void replaceFile(File tempFile) {
        File original = new File(NEWS_FILE);
        original.delete();
        tempFile.renameTo(original);
    }
}
