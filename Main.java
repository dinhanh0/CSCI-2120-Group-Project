import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Create stores.csv and grocery_items.csv
        CSVFile csvFile = new CSVFile();
        try {
            csvFile.createGroceryCSV();
            csvFile.createStoreCSV();
        } catch (Exception e) {
            System.err.println("Error initializing CSV files: " + e.getMessage());
            return;
        }

        List<String[]> stores = new ArrayList<>();

        // Read data from stores.csv
        try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header line
                }
                String[] storeData = line.split(",");
                if (storeData.length < 2) {
                    System.err.println("Invalid format in stores.csv. Each row must have at least two columns.");
                    continue;
                }
                stores.add(storeData);
            }
        } catch (FileNotFoundException e) {
            System.err.println("stores.csv not found. Please ensure the file exists.");
            return;
        } catch (IOException e) {
            System.err.println("Error reading stores file: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        double budget = 0;

        // Get user's shopping budget
        while (true) {
            try {
                System.out.print("Input your shopping budget: ");
                budget = Double.parseDouble(scanner.nextLine());
                if (budget <= 0) {
                    System.out.println("Budget must be a positive number.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Main menu loop
        while (true) {
            try {
                System.out.println("Menu:");
                System.out.println("1. Select a Store");
                System.out.println("2. View Items in Cart");
                System.out.println("3. Exit");
                System.out.print("Your choice: ");
                int menuChoice = Integer.parseInt(scanner.nextLine());

                switch (menuChoice) {
                    case 1 -> selectStore(scanner, stores, budget);
                    case 2 -> budget = GroceryGuide.viewCart(scanner, budget);
                    case 3 -> {
                        System.out.println("Thank you for using the Grocery Guide. Goodbye!");
                        break;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }

                if (menuChoice == 3) break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
            }
        }
    }

    private static void selectStore(Scanner scanner, List<String[]> stores, double budget) {
        try {
            System.out.print("Input your desired range (in miles): ");
            int range = Integer.parseInt(scanner.nextLine());
            if (range <= 0) {
                System.out.println("Range must be a positive number.");
                return;
            }

            List<String[]> availableStores = new ArrayList<>();
            System.out.println("Stores within " + range + " miles:");
            int index = 1;

            for (String[] store : stores) {
                try {
                    int storeDistance = Integer.parseInt(store[1]);
                    if (storeDistance <= range) {
                        availableStores.add(store);
                        System.out.println(index + ". " + store[0]);
                        index++;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid distance value in stores.csv. Skipping entry.");
                }
            }

            if (availableStores.isEmpty()) {
                System.out.println("No stores found within that range.");
                return;
            }

            System.out.print("Select a store by entering its number: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice > 0 && choice <= availableStores.size()) {
                String selectedStore = availableStores.get(choice - 1)[0];
                System.out.println("You selected: " + selectedStore);
                GroceryGuide.addItem(scanner, budget, selectedStore);
            } else {
                System.out.println("Invalid choice. Returning to main menu.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }
}
