//store.java

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private String name;
    private double distance;
    private List<String> items; // List to hold grocery items

    // Constructor
    public Store(String name, double distance) {
        this.name = name;
        this.distance = distance;
        this.items = new ArrayList<>();
    }
}


