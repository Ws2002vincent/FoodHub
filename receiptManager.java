package foodhub;

import java.io.*;
import java.util.*;

public class receiptManager {
    private String orderID;
    private String customerID; // New: Customer ID
    private String customerName;
    private String vendorID;
    private String paidTo; // Vendor Name
    private String orderType;
    private String orderItems;
    private String paymentDate;
    private String paymentTime;
    private double totalAmount;
    private String paymentStatus;
    private String paidBy;

    // Constructor
    public receiptManager(String orderID) {
        this.orderID = orderID;
        loadOrderDetails();
    }

    // Reads Orderhistory.txt and extracts details based on Order ID
    private void loadOrderDetails() {
        String filePath = "src/foodhub/Database/Orderhistory.txt"; 
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                
                if (parts[0].equals(orderID)) { // Match Order ID
                    this.customerName = parts[1]; // Paid By (Customer Name)
                    this.vendorID = parts[2]; // Vendor ID (V16, V17, etc.)
                    this.orderType = parts[3]; // Dine-in, Takeaway, etc.
                    this.orderItems = parts[4]; // Extract ordered items
                    this.paymentDate = parts[5].split(" ")[0]; // Extract only the date part
                    this.paymentTime = parts[5].split(" ")[1]; // Extract only the time
                    this.totalAmount = Double.parseDouble(parts[7]);
                    this.paymentStatus = parts[8];
                    this.paidBy = parts[1]; // Paid By (Customer Name)

                    if (this.orderItems == null || this.orderItems.trim().isEmpty()) {
                        this.orderItems = "No items found";
                    } else {
                        this.orderItems = getFormattedItems(this.orderItems); // Convert item IDs to names
                    }

                    // Fetch Customer ID from Customer.txt
                    this.customerID = fetchCustomerID(this.customerName);
                    
                    loadVendorDetails(); // Fetch Vendor Name (Paid To)
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error: Could not read Orderhistory.txt");
            e.printStackTrace();
        }
    }

    // Reads Customer.txt and fetches Customer ID based on Name
    private String fetchCustomerID(String customerName) {
        String filePath = "src/foodhub/Database/Customer.txt"; // Path to Customer.txt
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                CustomerModel customer = CustomerModel.fromString(line);
                if (customer != null && customer.getUsername().equalsIgnoreCase(customerName)) {
                    return customer.getCustomerID();
                }
            }
        } catch (IOException e) {
            System.err.println("Error: Could not read Customer.txt");
            e.printStackTrace();
        }
        return "Unknown"; // Return "Unknown" if no ID is found
    }

    // Reads Vendor.txt and fetches vendor name based on Vendor ID
    private void loadVendorDetails() {
        String filePath = "src/foodhub/Database/Vendor.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts[0].equals(vendorID)) { // Match Vendor ID
                    this.paidTo = parts[2]; // Vendor Name
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error: Could not read Vendor.txt");
            e.printStackTrace();
        }
    }

    // Reads Menuinfo.txt to fetch item names
    private String getFormattedItems(String orderItems) {
        String filePath = "src/foodhub/Database/Menuinfo.txt"; 
        HashMap<String, String> menuMap = new HashMap<>();
        
        // Load Menu Info
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length > 1) {
                    menuMap.put(parts[0], parts[1]); // Store Item ID -> Item Name
                }
            }
        } catch (IOException e) {
            System.err.println("Error: Could not read Menuinfo.txt");
            e.printStackTrace();
        }

        // Convert item IDs to names
        StringBuilder formattedItems = new StringBuilder();
        orderItems = orderItems.replace("[", "").replace("]", ""); // Clean up brackets
        String[] items = orderItems.split(","); 
        
        for (String item : items) {
            String[] details = item.split(":"); 
            String itemName = menuMap.getOrDefault(details[0], "Unknown Item");
            formattedItems.append(itemName).append(" x").append(details[1]).append(" - RM").append(details[2]).append("\n");
        }
        return formattedItems.toString();
    }

    // ==============================
    // ✅ Getters
    // ==============================

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerID() { 
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getOrderItems() {
        return orderItems;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public String getPaidBy() { 
        return customerName + " (ID: " + customerID + ")";
    }
}
