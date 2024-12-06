import java.io.*;
import java.util.*;

public class AddItems {

    private static final String ITEM_FILE = "grocery_items.csv"; // Path to the item csv file that contains all our items
    private static final String CART_FILE = "cart.csv";  // Path to where the cart is

    // The function to add an item to the cart
    public static void addItem(Scanner scanner, double budget, String store) {
        // Read all available items from the CSV file
        List<String[]> items = readCsv(ITEM_FILE);

        if (items.isEmpty()) {
            System.out.println("No items available to add.");
            return; // Stop if the file is empty
        }

        // Collect unique categories from the items
        List<String> categories = new ArrayList<>();
        for (String[] item : items) {
            if (!categories.contains(item[1])) { // item[1] is the category
                categories.add(item[1]);
            }
        }

        // Display the available categories
        System.out.println("Available Categories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }

        // Get the category choice from the user
        System.out.print("Select a category (enter number): ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer

        if (categoryChoice < 1 || categoryChoice > categories.size()) {
            System.out.println("Invalid category selection.");
            return; // Exit if the input is invalid
        }

        String selectedCategory = categories.get(categoryChoice - 1);

        // Filter items by both the selected store and the chosen category
        List<String[]> filteredItems = new ArrayList<>();
        for (String[] item : items) {
            if (item[3].equalsIgnoreCase(store) && item[1].equalsIgnoreCase(selectedCategory)) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            System.out.println("No items available in the selected category at the chosen store.");
            return;
        }

        // Display the items in the selected store and category
        System.out.println("Items in " + selectedCategory + " at " + store + ":");
        for (int i = 0; i < filteredItems.size(); i++) {
            System.out.println((i + 1) + ". " + filteredItems.get(i)[0] + " ($" + filteredItems.get(i)[2] + ")");
        }

        // Get the item choice from the user
        System.out.print("Select an item to add to the cart (enter number): ");
        int itemChoice = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer

        if (itemChoice < 1 || itemChoice > filteredItems.size()) {
            System.out.println("Invalid item selection.");
            return;
        }

        String[] selectedItem = filteredItems.get(itemChoice - 1);

        // Add the selected item to the cart file
        try (FileWriter fw = new FileWriter(CART_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            pw.println(String.join(",", selectedItem)); // Save item as a CSV row
            System.out.println("Item added to cart: " + selectedItem[0]);
        } catch (IOException e) {
            System.err.println("Error writing to cart file: " + e.getMessage());
        }
    }


    //This function will display the cart and handles the checkout
    public static double viewCart(Scanner scanner, double budget) {
        // Read all items that is currently in the cart
        List<String[]> cart = readCsv(CART_FILE);
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return budget;
        }

        //This part will calculate the total price of items in the cart they selected the items for
        double totalPrice = 0;
        System.out.println("Items in your cart:");
        for (String[] item : cart) {
            String itemName = item[0];
            double price = Double.parseDouble(item[2]);
            totalPrice += price;
            System.out.println(itemName + " - $" + price);
        }

        System.out.println("Total price: $" + totalPrice);
        System.out.print("Are you ready to check out? (yes/no): ");
        String checkoutChoice = scanner.next();

        if ("yes".equalsIgnoreCase(checkoutChoice)) {
            if (budget >= totalPrice) { //This part checks if the suer has more money than their total budget
                double remainingBudget = budget - totalPrice;
                System.out.println("You successfully bought the items. Remaining budget: $" + remainingBudget);
                System.out.print("Would you like to continue shopping? (yes/no): ");
                if ("yes".equalsIgnoreCase(scanner.next())) {
                    System.out.print("Enter your new budget: ");
                    return scanner.nextDouble(); // User sets a new budget
                } else {
                    System.out.println("Thank you for shopping!");
                    System.exit(0);
                }
            } else {
                System.out.println("You can't afford the items. Would you like to remove some items? (yes/no): ");
                if ("yes".equalsIgnoreCase(scanner.next())) {
                    deleteItem(scanner); //If the user wants to remove items
                } else {
                    System.out.println("Continuing with current cart.");
                }
            }
        }
        return budget; //If there wasn't any changes made it returns
    }

    // This is the function to remove any items from the cart
    private static void deleteItem(Scanner scanner) {
        // Reads all items currently in the cart
        List<String[]> cart = readCsv(CART_FILE);
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        // Show the items in the cart to the user
        System.out.println("Items in your cart:");
        for (int i = 0; i < cart.size(); i++) {
            System.out.println((i + 1) + ". " + cart.get(i)[0] + " ($" + cart.get(i)[2] + ")");
        }

        //This asks the user which item they want to remove
        System.out.print("Select an item to remove (enter number): ");
        int itemChoice = scanner.nextInt();
        scanner.nextLine();

        if (itemChoice > 0 && itemChoice <= cart.size()) {
            cart.remove(itemChoice - 1); //Removes the item you choose
            try (PrintWriter pw = new PrintWriter(new FileWriter(CART_FILE))) {
                for (String[] item : cart) {
                    pw.println(String.join(",", item)); //Rewrites the updated cart to the file
                }
                System.out.println("Item removed successfully.");
            } catch (IOException e) {
                System.err.println("Error updating cart file: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // This is the Function to read a CSV file and returns its content as a list
    private static List<String[]> readCsv(String fileName) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(",")); // Splits each of the line into columns
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return data;
    }
}


