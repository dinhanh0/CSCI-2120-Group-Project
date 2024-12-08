//Main.java

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Create CSV files
        CSVFile csvFile = new CSVFile();
        csvFile.createGroceryCSV();
        csvFile.createStoreCSV();

        // Read store data into a list
        List<String[]> stores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                stores.add(line.split(","));
            }
        } catch (IOException e) {
            System.err.println("Error reading stores file: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello, welcome to the grocery guide!");
        System.out.print("Input your shopping budget: ");
        double budget = scanner.nextDouble();

        ShoppingCart cart = new ShoppingCart(); // Create a shopping cart

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Select a Store");
            System.out.println("2. View Items in Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");
            int menuChoice = scanner.nextInt();

            switch (menuChoice) {
                case 1 -> selectStore(scanner, stores, budget, cart); // Select a store and add items
                case 2 -> {
                    cart.displayCartItems(); // View items in the cart
                    System.out.println("Total Price: $" + cart.getTotalPrice());
                    System.out.print("Are you done shopping? (yes/no): ");
                    String done = scanner.next();
                    if (done.equalsIgnoreCase("yes")) {
                        System.out.println("Thank you for shopping! Goodbye.");
                        return; // Exit the program
                    }
                }
                case 3 -> {
                    System.out.println("Thank you for using the Grocery Guide!");
                    return; // Exit the program
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void selectStore(Scanner scanner, List<String[]> stores, double budget, ShoppingCart cart) {
        System.out.print("Input your desired range (in miles): ");
        int range = scanner.nextInt();

        List<String[]> availableStores = new ArrayList<>();
        for (String[] store : stores) {
            if (Integer.parseInt(store[1]) <= range) {
                availableStores.add(store);
            }
        }

        if (availableStores.isEmpty()) {
            System.out.println("No stores found within that range.");
            return;
        }

        System.out.println("Stores within " + range + " miles:");
        for (int i = 0; i < availableStores.size(); i++) {
            System.out.println((i + 1) + ". " + availableStores.get(i)[0]);
        }

        System.out.print("Select a store by entering its number: ");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= availableStores.size()) {
            String selectedStore = availableStores.get(choice - 1)[0];
            System.out.println("You selected: " + selectedStore);
            AddItems.addItem(scanner, budget, selectedStore, cart);
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
