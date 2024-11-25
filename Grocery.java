public class Grocery {
    private String name;         // Name of the item (e.g., "Apple")
    private String category;     // Category of the item (e.g., "Fruits")
    private double price;        // Price of the item
    private String storeName;    // Name of the store selling the item

    // Constructor
    public Grocery(String name, String category, double price, String storeName) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.storeName = storeName;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getStoreName() {
        return storeName;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    // Method to display details about the grocery item
    public boolean displayItem() {
        System.out.println("Item Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Price: $" + price);
        System.out.println("Store: " + storeName);
        return false;
    }

    // Main method for testing
    public static void main(String[] args) {
        // Example usage
        Grocery apple = new Grocery("Apple", "Fruits", 1.20, "Super Mart");
        Grocery milk = new Grocery("Milk", "Dairy", 3.50, "Hello Healthy");

        // Display items
        apple.displayItem();
        System.out.println();
        milk.displayItem();
    }
}