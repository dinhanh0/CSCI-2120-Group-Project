//main.java
import java.io.*; //import for file handling
import java.util.*; //import for user input handling, array list and list

public class Main {

    public static void main(String[] args) {
        //create stores.csv and grocery_items.csv
        CSVFile csvFile = new CSVFile();
        csvFile.createGroceryCSV();
        csvFile.createStoreCSV();

        //List functionality implemented using arraylist
        //used to store contents of stores.csv
        List<String[]> stores = new ArrayList<>();

        //read data from stores.csv
        try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
            String line;

            //check for first header line since its format will be different
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // skip header line
                }

                String[] storeData = line.split(","); // comma-separated
                stores.add(storeData);
            }

            //check for incorrectly formatted stores.csv file
        } catch (IOException e) {
            System.err.println("Error reading stores file: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello, welcome to the grocery guide!");
        System.out.print("Input your shopping budget: ");
        double budget = scanner.nextDouble();

        //menu option for user to see
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Select a Store");
            System.out.println("2. View Items in Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");
            int menuChoice = scanner.nextInt();

            //handle functionalities for each options
            switch (menuChoice) {
                case 1 -> selectStore(scanner, stores, budget);
                case 2 -> budget = GroceryGuide.viewCart(scanner, budget);
                case 3 -> {
                    System.out.println("Thank you for using the Grocery Guide. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void selectStore(Scanner scanner, List<String[]> stores, double budget) {
        // Filter and display stores within the desired range
        System.out.print("Input your desired range (in miles): ");
        int range = scanner.nextInt();
        List<String[]> availableStores = new ArrayList<>();
        System.out.println("Stores within " + range + " miles:");
        int index = 1;

        //iterate through store List store user input distance and filter stores available
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

        // Let the user pick a store
        System.out.print("Select a store by entering its number: ");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= availableStores.size()) {
            String selectedStore = availableStores.get(choice - 1)[0];
            System.out.println("You selected: " + selectedStore);

            // AddItems.csv functionality
            GroceryGuide.addItem(scanner, budget, selectedStore);
        } else {
            System.out.println("Invalid choice. Returning to main menu.");
        }
    }
}

