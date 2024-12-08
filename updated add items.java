// AddItems.java

import java.io.*;
import java.util.*;

public class AddItems {
    private static final String ITEM_FILE = "grocery_items.csv"; // Path to the item file
    private static final String CART_FILE = "cart.csv";  // Path to the cart file

    // Method to add an item to the cart with its quantity
    public static void addItem(Scanner scanner, double budget, String store, ShoppingCart cart) {
        // Read all available items from the CSV file
        List<String[]> items = readCsv(ITEM_FILE);

        if (items.isEmpty()) {
            System.out.println("No items available to add."); // Inform the user if the file is empty
            return;
        }

        // Display items available in the selected store
        List<String[]> filteredItems = new ArrayList<>();
        for (String[] item : items) {
            if (item[3].equalsIgnoreCase(store)) { // Check if the item belongs to the selected store
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            System.out.println("No items available in the selected store."); // Inform the user if no items are available
            return;
        }

        System.out.println("Items available in " + store + ":");
        for (int i = 0; i < filteredItems.size(); i++) {
            System.out.println((i + 1) + ". " + filteredItems.get(i)[0] + " ($" + filteredItems.get(i)[2] + ")");
        }

        // Get item choice
        System.out.print("Select an item to add to the cart (enter number): ");
        int itemChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (itemChoice < 1 || itemChoice > filteredItems.size()) {
            System.out.println("Invalid item selection."); // Inform the user if the input is invalid
            return;
        }

        String[] selectedItem = filteredItems.get(itemChoice - 1); // Get the selected item
        String itemName = selectedItem[0]; // Get the item name
        double itemPrice = Double.parseDouble(selectedItem[2]); // Get the item price

        // Prompt the user to input the quantity
        System.out.print("Enter quantity for " + itemName + ": ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Calculate the total cost for the selected quantity
        double totalCost = itemPrice * quantity;

        // Check if the total cost exceeds the budget
        if (totalCost > budget) {
            System.out.println("You can't afford this item. Total cost: $" + totalCost + ", Budget: $" + budget);
            return; // Stop if the user can't afford the item
        }

        // Add the item and its quantity to the shopping cart
        cart.addItem(new Grocery(itemName, selectedItem[1], itemPrice, store), quantity);
        System.out.println("Added " + quantity + " x " + itemName + " to the cart.");
    }

    // Helper method to read a CSV file and return its contents
    private static List<String[]> readCsv(String fileName) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // Skip the header
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(",")); // Split the line by commas and add it to the list
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return data;
    }
}


