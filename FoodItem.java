
package foodhub;

public class FoodItem {
    private String id;
    private String name;
    private String desc;
    private String availability;
    private String vendorId;
    private double price;
    private String imagePath;

    public FoodItem(String id, String name, String desc, String availability, String vendorId, double price, String imagePath) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.availability = availability;
        this.vendorId = vendorId;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }
    
    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public String getAvailability() {
        return availability;
    }

    public String getVendorId() {
        return vendorId;
    }

    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }
}
