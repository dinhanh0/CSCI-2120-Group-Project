import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Grocery> cartItems; // List to store grocery items in the cart
    private double totalPrice;           // Total price of items in the cart

    // Constructor
    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    // Add a grocery item to the cart
    public void addItem(Grocery item) {
        cartItems.add(item);
        totalPrice += item.getPrice();
        System.out.println(item.getName() + " added to the cart.");
    }

    // Remove a grocery item from the cart by its name
    public void removeItem(String itemName) {
        Grocery itemToRemove = null;
        for (Grocery item : cartItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            cartItems.remove(itemToRemove);
            totalPrice -= itemToRemove.getPrice();
            System.out.println(itemName + " removed from the cart.");
        } else {
            System.out.println("Item not found in the cart.");
        }
    }

    // Get a grocery item from the cart by its name
    public Grocery getItem(String itemName) {
        for (Grocery item : cartItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        System.out.println("Item not found in the cart.");
        return null;
    }

    // Get the total price of items in the cart
    public double getTotalPrice() {
        return totalPrice;
    }

    // Display all items in the cart
    public void displayCartItems() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in your cart:");
            for (Grocery item : cartItems) {
                System.out.println(item.displayItem());
            }
            System.out.println("Total Price: $" + totalPrice);
        }
    }

    // Clear all items from the cart
    public void clearCart() {
        cartItems.clear();
        totalPrice = 0.0;
        System.out.println("Shopping cart cleared.");
    }
}
