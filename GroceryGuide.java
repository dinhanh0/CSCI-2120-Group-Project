import java.io.*;
import java.util.*;

public class GroceryGuide {

    public static void addItem(Scanner scanner, double budget, String storeName) {
        List<String[]> items = new ArrayList<>();

        // Read items from grocery_items.csv
        try (BufferedReader reader = new BufferedReader(new FileReader("grocery_items.csv"))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] itemData = line.split(",");
                if (itemData.length < 3) {
                    System.err.println("Invalid format in grocery_items.csv. Each row must have at least three columns.");
                    continue;
                }

                items.add(itemData);
            }
        } catch (FileNotFoundException e) {
            System.err.println("grocery_items.csv not found. Please ensure the file exists.");
            return;
        } catch (IOException e) {
            System.err.println("Error reading grocery_items.csv: " + e.getMessage());
            return;
        }

        // Display available items
        System.out.println("Available items in " + storeName + ":");
        for (int i = 0; i < items.size(); i++) {
            String[] item = items.get(i);
            System.out.println((i + 1) + ". " + item[0] + " - $" + item[1] + " per " + item[2]);
        }

        // Get user selection
        System.out.print("Enter the number of the item you want to add to your cart: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice <= 0 || choice > items.size()) {
                System.out.println("Invalid choice. Returning to the main menu.");
                return;
            }

            String[] selectedItem = items.get(choice - 1);
            System.out.println("You selected: " + selectedItem[0] + " ($" + selectedItem[1] + "/" + selectedItem[2] + ")");

            // Get quantity
            System.out.print("Enter the quantity you want to purchase: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity <= 0) {
                System.out.println("Quantity must be greater than zero. Returning to the main menu.");
                return;
            }

            double cost = Double.parseDouble(selectedItem[1]) * quantity;

            if (cost > budget) {
                System.out.println("Insufficient budget. Item not added to your cart.");
            } else {
                budget -= cost;
                System.out.println("Item added to cart! Remaining budget: $" + budget);
                writeToCart(storeName, selectedItem[0], quantity, cost);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private static void writeToCart(String storeName, String itemName, int quantity, double cost) {
        File cartFile = new File("cart.csv");
        boolean fileExists = cartFile.exists();

        try (FileWriter writer = new FileWriter(cartFile, true)) {
            if (!fileExists) {
                writer.write("Store,Item,Quantity,Cost\n"); // Write header if file doesn't exist
            }

            writer.write(storeName + "," + itemName + "," + quantity + "," + cost + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to cart.csv: " + e.getMessage());
        }
    }

    public static double viewCart(Scanner scanner, double budget) {
        File cartFile = new File("cart.csv");

        if (!cartFile.exists()) {
            System.out.println("Your cart is empty.");
            return budget;
        }

        double totalCost = 0;
        System.out.println("Items in your cart:");

        try (BufferedReader reader = new BufferedReader(new FileReader(cartFile))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] cartData = line.split(",");
                if (cartData.length < 4) {
                    System.err.println("Invalid format in cart.csv. Skipping entry.");
                    continue;
                }

                System.out.println(cartData[0] + " - " + cartData[1] + " x" + cartData[2] + " ($" + cartData[3] + ")");
                totalCost += Double.parseDouble(cartData[3]);
            }
        } catch (IOException e) {
            System.err.println("Error reading cart.csv: " + e.getMessage());
        }

        System.out.println("Total cost: $" + totalCost);
        System.out.println("Remaining budget: $" + (budget - totalCost));
        return budget - totalCost;
    }
}
