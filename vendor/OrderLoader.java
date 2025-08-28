package foodhub.vendor;

import java.io.*;
import java.util.*;

public class OrderLoader {

    public static List<Order> loadOrders(String filePath, String vendorID) {
        List<Order> orderList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[2].trim().equals(vendorID)) {
                    String orderId = data[0].trim();
                    String customer = data[1].trim();
                    String serviceType = data[3].trim();
                    String date = data[5].trim();
                    double totalAmount = Double.parseDouble(data[7].trim());
                    String orderStatus = data[8].trim();

                    // Extracting ordered items - [F1:2:25.8]
                    String orderedItems = data[4].trim();
                    StringBuilder itemNames = new StringBuilder();

                    try {
                        // Remove brackets and split by commas
                        String[] items = orderedItems.substring(1, orderedItems.length() - 1).split(",");

                        for (String item : items) {
                            // Parse each item 
                            String[] itemDetails = item.split(":");
                            String itemId = itemDetails[0].trim();
                            int quantity = Integer.parseInt(itemDetails[1].trim());

                            // Fetch the item name from MenuInfo.txt based on itemId
                            String itemName = getItemNameFromMenu(itemId);
                            itemNames.append(itemName).append(" x").append(quantity).append(", ");
                        }

                        // Remove trailing comma and space
                        if (itemNames.length() > 0) {
                            itemNames.setLength(itemNames.length() - 2);  // Remove trailing comma
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing ordered items: " + orderedItems);
                        e.printStackTrace();
                    }

                    Order order = new Order(orderId, customer, vendorID, serviceType, date, totalAmount, orderStatus, itemNames.toString());
                    orderList.add(order);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Order history file not found.");
        } catch (IOException e) {
            System.err.println("Error reading order history file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric data in the order file.");
        }
        return orderList;
    }

    private static String getItemNameFromMenu(String itemId) {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Menuinfo.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].trim().equals(itemId)) {
                    return data[1].trim();  // Return the menu item name
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading menu info file: " + e.getMessage());
        }
        return "Unknown Item";
    }
}
