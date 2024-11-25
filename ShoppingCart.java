import java.util.*;

// this class represents a shopping cart where users can add, remove, display items, and calculate the total price

public class ShoppingCart {
    // this is a list to store all the grocery items added to the shopping cart
    private List<Grocery> cartItems;

    // this is a constructor that initializes an empty shopping cart
    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
    }

    // this method adds item to the cart
    public void addItem(Grocery item) {
        cartItems.add(item);
    }

    // this method removes item from the cart by name
    public void removeItem(String itemName) {
        cartItems.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }

    // this method displays the cart items
    public void displayCartItems() {
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        for (Grocery item : cartItems) {
            System.out.println("- " + item.getName() + " ($" + item.getPrice() + ") from " + item.getStoreName());
        }
    }

    // this method calculates the total price of the cart
    public double getTotalPrice() {
        return cartItems.stream().mapToDouble(Grocery::getPrice).sum();
    }
}
