//csvfile.java
import java.io.*; //for file handling
import java.util.*; // for list and arraylist

public class CSVFile {
    public void createGroceryCSV() {
        // create file name
        String fileName = "grocery_items.csv";

        //content in csv file
        String data = """
               ItemName,Category,Price,StoreName,Unit
               Cabbage,Vegetables,2.50,SuperMart,head
               Kale,Vegetables,3.00,SuperMart,bunch
               Spinach,Vegetables,2.80,SuperMart,bunch
               Carrot,Vegetables,1.50,HelloHealthy,pound
               Broccoli,Vegetables,2.20,HelloHealthy,head
               Tomato,Vegetables,2.00,HelloHealthy,pound
               Orange,Fruits,3.00,SuperMart,pound
               Apple,Fruits,2.00,SuperMart,pound
               Banana,Fruits,1.20,SuperMart,pound
               Mango,Fruits,1.80,HelloHealthy,each
               Pineapple,Fruits,3.50,HelloHealthy,each
               Strawberries,Fruits,4.00,HelloHealthy,pound
               White bread,Bakery,2.50,BakedTown,loaf
               Wheat bread,Bakery,2.70,BakedTown,loaf
               Croissant,Bakery,3.20,BakedTown,each
               Muffin,Bakery,2.50,BakedTown,each
               Sourdough,Bakery,4.00,BakedTown,loaf
               Bagels,Bakery,3.20,BakedTown,each
               Milk,Dairy,4.00,SuperMart,gallon
               Cheese,Dairy,5.00,SuperMart,pound
               Butter,Dairy,3.50,SuperMart,stick
               Yogurt,Dairy,3.50,HelloHealthy,container
               Cottage cheese,Dairy,4.50,HelloHealthy,pound
               Cream,Dairy,2.80,HelloHealthy,container
               Milk,Dairy,3.80,BakedTown,gallon
               Cream cheese,Dairy,4.00,BakedTown,pound
               Ice cream,Dairy,5.50,BakedTown,container
               Sardines,Protein,4.00,SuperMart,can
               Beef,Protein,10.00,SuperMart,pound
               Chicken breast,Protein,6.50,SuperMart,pound
               Salmon,Protein,12.00,HelloHealthy,pound
               Tofu,Protein,3.00,HelloHealthy,pound
               Pork,Protein,8.00,HelloHealthy,pound
               Steak,Protein,9.00,BakedTown,pound
               Turkey,Protein,7.00,BakedTown,pound
               Sausages,Protein,5.50,BakedTown,pack
               Canned tomato,Canned Items,1.20,SuperMart,can
               Canned Pickle,Canned Items,2.00,SuperMart,jar
               Canned Beans,Canned Items,1.50,SuperMart,can
               Canned corn,Canned Items,1.80,HelloHealthy,can
               Canned tuna,Canned Items,2.20,HelloHealthy,can
               Canned peaches,Canned Items,2.50,HelloHealthy,jar
               Canned soup,Canned Items,2.00,BakedTown,can
               Canned chili,Canned Items,3.00,BakedTown,can
               Canned fruits,Canned Items,1.80,BakedTown,jar
               Frozen corn,Frozen,2.50,SuperMart,bag
               Frozen chicken nuggets,Frozen,6.00,SuperMart,bag
               Frozen pizza,Frozen,7.00,SuperMart,each
               Frozen vegetables,Frozen,3.50,HelloHealthy,bag
               Ice cream,Frozen,4.50,HelloHealthy,container
               Frozen berries,Frozen,5.00,HelloHealthy,pound
               Frozen meals,Frozen,5.50,BakedTown,each
               Frozen waffles,Frozen,3.80,BakedTown,each
               Frozen fries,Frozen,2.70,BakedTown,bag
               Cough drops,Health,3.00,SuperMart,box
               Ibuprofen,Health,5.00,SuperMart,bottle
               Pain relievers,Health,6.50,SuperMart,bottle
               Vitamins,Health,8.00,HelloHealthy,bottle
               First aid kit,Health,12.00,HelloHealthy,kit
               Antiseptic,Health,3.50,HelloHealthy,bottle
               Cough syrup,Health,4.50,BakedTown,bottle
               Bandages,Health,2.50,BakedTown,box
               Hand sanitizer,Health,3.00,BakedTown,bottle
               Shampoo,Personal Care,7.00,SuperMart,bottle
               Acne cleanser,Personal Care,6.00,SuperMart,bottle
               Moisturizer,Personal Care,8.00,SuperMart,jar
               Conditioner,Personal Care,6.50,HelloHealthy,bottle
               Body wash,Personal Care,5.50,HelloHealthy,bottle
               Toothpaste,Personal Care,2.50,HelloHealthy,tube
               Shaving cream,Personal Care,4.00,BakedTown,can
               Deodorant,Personal Care,3.50,BakedTown,stick
               Toothbrush,Personal Care,1.80,BakedTown,each
               """;

        // write data to csv file
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data);
        } catch (IOException e) {
            System.err.println("An error occurred while creating the CSV file: " + e.getMessage());
        }

    }

    public void createStoreCSV() {
        String fileName2 = "stores.csv";
        String data = """
                StoreName,Distance(miles)
                SuperMart,5
                HelloHealthy,10
                BakedTown,15
                """;
        // write data to csv file
        try (FileWriter writer = new FileWriter(fileName2)) {
            writer.write(data);

            //check if file is correctly formated
        } catch (IOException e) {
            System.err.println("An error occurred while creating the CSV file: " + e.getMessage());


        }
    }

    //read store.csv data
    public List<Store> readStoreData() {
        List<Store> stores = new ArrayList<>();
        String fileName = "stores.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();  //skip the header line

            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); //comma separated
                String storeName = data[0].trim();
                double distance = Double.parseDouble(data[1].trim());
                stores.add(new Store(storeName, distance));
            }

            //check if file is correctly formated
        } catch (IOException e) {
            System.err.println("Error reading store data: " + e.getMessage());
        }

        return stores;
    }
}
