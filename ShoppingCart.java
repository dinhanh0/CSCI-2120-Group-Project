import java.util.*;

public class ShoppingCart {
    private List<Grocery> cartItems;

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
    }

    // Add item to cart
    public void addItem(Grocery item) {
        cartItems.add(item);
    }

    // Remove item from cart by name
    public void removeItem(String itemName) {
        cartItems.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }

    // Display cart items
    public void displayCartItems() {
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        for (Grocery item : cartItems) {
            System.out.println("- " + item.getName() + " ($" + item.getPrice() + ") from " + item.getStoreName());
        }
    }

    // Calculate total price of cart
    public double getTotalPrice() {
        return cartItems.stream().mapToDouble(Grocery::getPrice).sum();
    }
}
