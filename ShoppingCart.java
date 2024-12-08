import java.util.*;

import java.util.ArrayList;
import java.util.List;

// This class represents a shopping cart that stores items and their quantities.
public class ShoppingCart {
    private List<Grocery> cartItems; // Stores the grocery items added to the cart
    private List<Integer> quantities; // Stores the quantity of each item in the cart

    // Constructor: Initializes an empty shopping cart
    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    // Method to add an item to the cart along with its quantity
    public void addItem(Grocery item, int quantity) {
        cartItems.add(item); // Add the grocery item to the cart
        quantities.add(quantity); // Add the quantity for that item
    }

    // Method to remove an item from the cart by its name
    public void removeItem(String itemName) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getName().equalsIgnoreCase(itemName)) {
                cartItems.remove(i); // Remove the item from the cart
                quantities.remove(i); // Remove its quantity from the list
                System.out.println(itemName + " removed from the cart.");
                return;
            }
        }
        System.out.println("Item not found in the cart."); // Inform the user if the item is not found
    }

    // Method to display all items in the cart with their quantities and prices
    public void displayCartItems() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty."); // Inform the user if the cart is empty
            return;
        }

        System.out.println("Items in your cart:");
        for (int i = 0; i < cartItems.size(); i++) {
            Grocery item = cartItems.get(i); // Get the grocery item
            int quantity = quantities.get(i); // Get the quantity for this item
            System.out.println("- " + item.getName() + " (" + quantity + " x $" + item.getPrice() + ") from " + item.getStoreName());
        }
    }

    // Method to calculate the total price of the items in the cart
    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < cartItems.size(); i++) {
            total += cartItems.get(i).getPrice() * quantities.get(i); // Multiply price by quantity
        }
        return total; // Return the total price
    }
}

