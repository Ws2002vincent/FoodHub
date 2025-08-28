package foodhub;

/**
 *
 * @author User
 */
public class CustomerModel {
    private String customerID;
    private String username;
    private double credit;
    private String street;
    private String postcode;
    private String state;
    private String profilePic;

    // Constructor
    public CustomerModel(String customerID, String username, double credit, String street, String postcode, String state, String profilePic) {
        this.customerID = customerID;
        this.username = username;
        this.credit = credit;
        this.street = street;
        this.postcode = postcode;
        this.state = state;
        this.profilePic = (profilePic == null || profilePic.isBlank()) ? "default.png" : profilePic;
    }

    // Getters
    public String getCustomerID() {
        return customerID;
    }

    public String getUsername() {
        return username;
    }

    public double getCredit() {
        return credit;
    }

    public String getStreet() {
        return street;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getState() {
        return state;
    }

    public String getProfilePic() {
        return profilePic;
    }

    // Setters
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = (profilePic == null || profilePic.isBlank()) ? "default.png" : profilePic;
    }

    // Convert to file format
    public String toFileFormat() {
        return String.format("%s | %s | %.2f | %s | %s | %s | %s",
            customerID, username, credit, street, postcode, state, profilePic);
    }

    // Create customer object from file string
    public static CustomerModel fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 6) {
            String customerID = parts[0].trim();
            String username = parts[1].trim();
            double credit = Double.parseDouble(parts[2].trim());
            String street = parts[3].trim();
            String postcode = parts[4].trim();
            String state = parts[5].trim();
            String profilePic = (parts.length >= 7) ? parts[6].trim() : "default.png";


            return new CustomerModel(customerID, username, credit, street, postcode, state, profilePic);
        }
        return null;
    }
}
