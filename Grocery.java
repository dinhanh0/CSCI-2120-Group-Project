
// this class represents a grocery item with attributes such as name, category, price, and store name

public class Grocery {
    private String name;         // the name of the item
    private String category;     // the category of the item
    private double price;        // the price of the item
    private String storeName;    // the name of the store selling the item
    private String unit;

    // this is the Constructor
    public Grocery(String name, String category, double price, String storeName) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.storeName = storeName;
        this.unit = unit;

    }

    // these are Getters
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
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }



    // these are Setters
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

    // these are the methods to display details about the grocery item
    public void displayItem() {
        System.out.println("Item Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Price: $" + price);
        System.out.println("Store: " + storeName);
    }

}
