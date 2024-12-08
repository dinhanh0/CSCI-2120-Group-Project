while (true) {
    System.out.println("Menu:");
    System.out.println("1. Select a Store");
    System.out.println("2. View Items in Cart");
    System.out.println("3. Checkout");
    System.out.println("4. Exit");
    System.out.print("Your choice: ");
    int menuChoice = scanner.nextInt();

    switch (menuChoice) {
        case 1 -> selectStore(scanner, stores, budget);
        case 2 -> budget = AddItems.viewCart(scanner, budget);
        case 3 -> {
            shoppingCart.checkout("cart.csv");
            budget = 0; // Reset budget after checkout
        }
        case 4 -> {
            System.out.println("Thank you for using the Grocery Guide. Goodbye!");
            scanner.close();
            return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
    }
}
