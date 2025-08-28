package foodhub.vendor;

public class MenuItem {

    private String foodID;
    private String name;
    private String description;
    private double price;
    private String imagePath;

    public MenuItem(String foodID, String name, String description, double price, String imagePath) {
        this.foodID = foodID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getFoodID() {
        return foodID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }
}
