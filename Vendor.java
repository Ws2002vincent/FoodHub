package foodhub;

/**
 *
 * @author User
 */
public class Vendor extends User{
    private String vendorID;
    private String username;
    private String stallName; // New field for stall name
    private double revenue;
    private String available; // Represented as "true" or "false"
    private String profilePic;

    // Constructor
    public Vendor(String vendorID, String username, String stallName, double revenue, String available, String profilePic) {
        this.vendorID = vendorID;
        this.username = username;
        this.stallName = stallName;
        this.revenue = revenue;
        this.available = (available.equalsIgnoreCase("true") || available.equalsIgnoreCase("t")) ? "true" : "false";
        this.profilePic = (profilePic == null || profilePic.isBlank()) ? "default.png" : profilePic;
    }

    // Getters
    public String getVendorID() {
        return vendorID;
    }

    public String getUsername() {
        return username;
    }

    public String getStallName() {
        return stallName;
    }

    public double getRevenue() {
        return revenue;
    }

    public String getAvailable() {
        return available;
    }

    public String getProfilePic() {
        return profilePic;
    }

    // Setters
    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setAvailable(String available) {
        this.available = (available.equalsIgnoreCase("true") || available.equalsIgnoreCase("t")) ? "true" : "false";
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = (profilePic == null || profilePic.isBlank()) ? "default.png" : profilePic;
    }

    // Convert to file format
    public String toFileFormat() {
        return String.format("%s | %s | %s | %.2f | %s | %s",
            vendorID, username, stallName, revenue, available, profilePic);
    }

    // Create vendor object from file string
    public static Vendor fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 6) { // Adjusted to account for the stall name
            String vendorID = parts[0].trim();
            String username = parts[1].trim();
            String stallName = parts[2].trim(); // New stall name field
            double revenue = Double.parseDouble(parts[3].trim());
            String available = parts[4].trim();
            String profilePic = parts[5].trim();

            return new Vendor(vendorID, username, stallName, revenue, available, profilePic);
        }
        return null;
    }
}
