import java.io.*; // Import for file handling
import java.util.*; // Import for user input handling, array list, and list

public class Main {

    public static void main(String[] args) {
        // Create stores.csv and grocery_items.csv
        CSVFile csvFile = new CSVFile();
        csvFile.createGroceryCSV();
        csvFile.createStoreCSV();

        // List to store contents of stores.csv
        List<String[]> stores = new ArrayList<>();
        ShoppingCart shoppingCart = new ShoppingCart(); // Create shopping cart instance

        // Read data from stores.csv
        try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
            String line;
            boolean isFirstLine = true; // Skip the header line

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                String[] storeData = line.split(","); // Comma-separated
                stores.add(storeData);
            }
        } catch (IOException e) {
            System.err.println("Error reading stores file: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello, welcome to the grocery guide!");
        System.out.print("Input your shopping budget: ");
        double budget = scanner.nextDouble();

        // Menu for user interaction
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Select a Store");
            System.out.println("2. View Items in Cart");
            System.out.println("3. Checkout");
            System.out.println("4. Exit");
            System.out.print("Your choice: ");
            int menuChoice = scanner.nextInt();

            switch (menuChoice) {
                case 1 -> selectStore(scanner, stores, budget, shoppingCart);
                case 2 -> viewCart(shoppingCart);
                case 3 -> {
                    shoppingCart.checkout("cart.csv");
                    System.out.println("Cart has been emptied. Thank you for shopping!");
                }
                case 4 -> {
                    System.out.println("Thank you for using the Grocery Guide. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void selectStore(Scanner scanner, List<String[]> stores, double budget, ShoppingCart shoppingCart) {
        System.out.print("Input your desired range (in miles): ");
        int range = scanner.nextInt();
        List<String[]> availableStores = new ArrayList<>();
        System.out.println("Stores within " + range + " miles:");
        int index = 1;

        for (String[] store : stores) {
            String storeName = store[0];
            int storeDistance = Integer.parseInt(store[1]);

            if (storeDistance <= range) {
                availableStores.add(store);
                System.out.println(index + ". " + storeName);
                index++;
            }
        }

        if (availableStores.isEmpty()) {
            System.out.println("No stores found within that range.");
            return;
        }

        System.out.print("Select a store by entering its number: ");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= availableStores.size()) {
            String selectedStore = availableStores.get(choice - 1)[0];
            System.out.println("You selected: " + selectedStore);

            // Add items to the cart
            AddItems.addItem(scanner, budget, selectedStore, shoppingCart);
        } else {
            System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void viewCart(ShoppingCart shoppingCart) {
        System.out.println("Items in your cart:");
        shoppingCart.displayCartItems();
        System.out.println("Total price: $" + shoppingCart.getTotalPrice());
    }
}
