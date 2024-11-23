import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //create csv files
        CSVFile object = new CSVFile();
        object.createGroceryCSV();
        object.createStoreCSV();

        List<String[]> stores = new ArrayList<>();
        // Read data from CSV file (assuming it's tab-separated)
        try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
            String line;
            boolean isFirstLine = true;  // Flag to skip the header line

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the first line
                }
                String[] storeData = line.split(",");  // comma separated
                stores.add(storeData);  // Add store data to the list
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }



        System.out.println("Hello, welcome to the grocery guide!");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Input your shopping budget: ");
        double budget = scanner.nextDouble();

        System.out.print("Input your desired range (in miles): ");
        int range = scanner.nextInt();


        //filter and display stores within desired range and store the store info
        List<String[]> availableStores = new ArrayList<>();
        boolean found = false;
        System.out.println("Stores within " + range + " miles:");
        for (String[] store : stores) {
            String storeName = store[0];
            int storeDistance = Integer.parseInt(store[1]);

            // If the store's distance is within the desired range (inclusive)
            if (storeDistance <= range) {
                availableStores.add(store);
                System.out.println(storeName);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No store found within that range.");
        }

        // Let the user pick a store
        System.out.print("Select a store by entering the corresponding number: ");
        int choice = scanner.nextInt();

        // Check if the choice is valid
        if (choice > 0 && choice <= availableStores.size()) {
            String selectedStore = availableStores.get(choice - 1)[0];  // Get store name
            System.out.println("You selected: " + selectedStore);
        } else {
            System.out.println("Invalid choice. Please select a valid store number.");
        }



        // Close the scanner
        scanner.close();
    }
}

