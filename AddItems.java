// Additems.java

import java.io.*;
import java.util.*;

public class AddItems {

    private static final String ITEM_FILE = "grocery_items.csv"; // Path to the item CSV file that contains all our items
    private static final String CART_FILE = "cart.csv";  // Path to where the cart is

    public static void addItem(Scanner scanner, double budget, String store) {
        while (true) { // Loop to allow adding multiple items
            // Read all available items from the CSV file
            List<String[]> items = readCsv(ITEM_FILE);

            // If the CSV file is empty or there are no items
            if (items.isEmpty()) {
                System.out.println("No items available to add.");
                return; // Stop the function
            }

            // Collect unique categories from the items (e.g., Vegetables, Fruits)
            List<String> categories = new ArrayList<>();
            for (String[] item : items) {
                if (!categories.contains(item[1])) { // item[1] is the category column
                    categories.add(item[1]);
                }
            }

            // Display the available categories to the user
            System.out.println("Available Categories:");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i));
            }

            // Ask the user to select a category
            System.out.print("Select a category (enter number): ");
            int categoryChoice = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer

            // Validate the user's category choice
            if (categoryChoice < 1 || categoryChoice > categories.size()) {
                System.out.println("Invalid category selection.");
                continue; // Restart the loop if the choice is invalid
            }

            // Get the selected category name
            String selectedCategory = categories.get(categoryChoice - 1);

            // Filter items based on the selected store and category
            List<String[]> filteredItems = new ArrayList<>();
            for (String[] item : items) {
                if (item[3].equalsIgnoreCase(store) && item[1].equalsIgnoreCase(selectedCategory)) {
                    filteredItems.add(item);
                }
            }

            // If no items are available in the selected category at the store
            if (filteredItems.isEmpty()) {
                System.out.println("No items available in the selected category at the chosen store.");
                continue; // Restart the loop
            }

            // Display the filtered items to the user
            System.out.println("Items in " + selectedCategory + " at " + store + ":");
            for (int i = 0; i < filteredItems.size(); i++) {
                System.out.println((i + 1) + ". " + filteredItems.get(i)[0] + " ($" + filteredItems.get(i)[2] + " per " + filteredItems.get(i)[4] + ")");
            }

            // Ask the user to select an item
            System.out.print("Select an item to add to the cart (enter number): ");
            int itemChoice = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer

            // Validate the user's item choice
            if (itemChoice < 1 || itemChoice > filteredItems.size()) {
                System.out.println("Invalid item selection.");
                continue; // Restart the loop
            }

            // Get the selected item's details
            String[] selectedItem = filteredItems.get(itemChoice - 1);

            // Ask the user for the quantity they want to purchase
            System.out.print("Enter the quantity for " + selectedItem[0] + " (in " + selectedItem[4] + "): ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer

            // Validate the quantity (must be at least 1)
            if (quantity < 1) {
                System.out.println("Invalid quantity. Must be at least 1.");
                continue; // Restart the loop
            }

            // Calculate the total price for the selected quantity
            double itemPrice = Double.parseDouble(selectedItem[2]);
            double totalItemPrice = itemPrice * quantity;

            // Check if the user has enough budget for the selected quantity
            if (totalItemPrice > budget) {
                System.out.println("You don't have enough budget to purchase " + quantity + " of " + selectedItem[0] + ".");
                continue; // Restart the loop
            }

            // Add the selected item with quantity to the cart file
            try (FileWriter fw = new FileWriter(CART_FILE, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter pw = new PrintWriter(bw)) {

                // Append the item details and quantity to the cart file
                pw.println(String.join(",", selectedItem) + "," + quantity); // Save item as a CSV row with quantity
                System.out.println(quantity + " x " + selectedItem[0] + " added to cart. Total cost: $" + totalItemPrice);

                // Update the budget
                budget -= totalItemPrice;
                System.out.println("Remaining budget: $" + budget);
            } catch (IOException e) {
                System.err.println("Error writing to cart file: " + e.getMessage());
            }

            // Ask if the user wants to add another item
            System.out.print("Do you want to add another item? (yes/no): ");
            String continueChoice = scanner.nextLine();

            if (!"yes".equalsIgnoreCase(continueChoice)) {
                break; // Exit the loop if the user does not want to add more items
            }
        }
    }


    // This function will display the cart and handle the checkout process
    public static double viewCart(Scanner scanner, double budget) {
        // Read all items currently in the cart
        List<String[]> cart = readCsv(CART_FILE);

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return budget; // Return the budget unchanged
        }

        // Display the items in the cart
        double totalPrice = 0;
        System.out.println("Items in your cart:");
        for (String[] item : cart) {
            if (item.length < 6) {
                System.out.println("Cart contains incomplete item data. Skipping entry.");
                continue; // Skip incomplete entries
            }

            String itemName = item[0];
            double price = Double.parseDouble(item[2]);
            int quantity = Integer.parseInt(item[5]); // Get the quantity column
            double itemTotalPrice = price * quantity;
            totalPrice += itemTotalPrice;
            System.out.println(quantity + " x " + itemName + " ($" + price + " each) = $" + itemTotalPrice);
        }

        // Show the total price of the cart
        System.out.println("Total Price: $" + totalPrice);

        // Ask the user if they are ready to check out
        System.out.print("Are you ready to check out? (yes/no): ");
        String checkoutChoice = scanner.next();

        if ("yes".equalsIgnoreCase(checkoutChoice)) {
            if (budget >= totalPrice) { // Check if the user has enough budget
                double remainingBudget = budget - totalPrice;
                System.out.println("Checkout successful! Remaining budget: $" + remainingBudget);

                // Clear the cart after successful checkout
                try (FileWriter fw = new FileWriter(CART_FILE)) {
                    fw.write(""); // Overwrite the cart file with an empty state
                } catch (IOException e) {
                    System.err.println("Error clearing the cart: " + e.getMessage());
                }

                return remainingBudget; // Return the remaining budget
            } else {
                System.out.println("Insufficient funds. Please remove items or increase your budget.");
            }
        } else {
            System.out.println("Checkout canceled. Returning to main menu.");
        }

        return budget; // Return the original budget if checkout is canceled
    }

    // This is the Function to read a CSV file and returns its content as a list
    private static List<String[]> readCsv(String fileName) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(",")); // Splits each line into columns
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return data;
    }
}

