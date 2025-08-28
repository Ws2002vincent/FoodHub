package foodhub.runner;

import java.io.*;
import java.util.*;

public class TaskLoader {

    public static List<Task> loadTask(String filePath) {
        List<Task> taskList = new ArrayList<>();
        Map<String, String> customerAddresses = loadCustomerAddresses("C:\\FoodHub\\src\\foodhub\\Database\\Customer.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String orderStatus = "Completed";
            String serviceType = "Delivery";

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                //Validate that the order status and service type match the filter
                if (data[8].trim().equals(orderStatus) && data[3].trim().equals(serviceType)) {

                    // Check if the last attribute (index 10) exists and is "true", skip loading the task
                    if (data.length > 10 && data[10].trim().equalsIgnoreCase("true")) {
                        continue; // Skip this task because it has been accepted
                    }
                    
                    String orderId = data[0].trim();
                    String customer = data[1].trim();
                    String date = data[5].trim();
                    String deliveryFee = data[6].trim();
                    double totalAmount = Double.parseDouble(data[7].trim());

                    // Extract ordered items
                    String orderedItems = data[4].trim();
                    StringBuilder itemNames = new StringBuilder();

                    try {
                        String[] items = orderedItems.substring(1, orderedItems.length() - 1).split(",");
                        for (String item : items) {
                            String[] itemDetails = item.split(":");
                            String itemId = itemDetails[0].trim();
                            int quantity = Integer.parseInt(itemDetails[1].trim());

                            String itemName = getItemNameFromMenu(itemId);
                            itemNames.append(itemName).append(" x").append(quantity).append(", ");
                        }

                        if (itemNames.length() > 0) {
                            itemNames.setLength(itemNames.length() - 2);
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing ordered items: " + orderedItems);
                        e.printStackTrace();
                    }

                    // Get the matching address for the customer (if available)
                    String customerAddress = customerAddresses.getOrDefault(customer, "Address not found");

                    Task task = new Task(orderId, customer, serviceType, date, totalAmount, orderStatus, itemNames.toString(), deliveryFee, customerAddress);
                    taskList.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Order history file not found.");
        } catch (IOException e) {
            System.err.println("Error reading order history file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric data in the order file.");
        }
        return taskList;
    }

    private static Map<String, String> loadCustomerAddresses(String customerFilePath) {
        Map<String, String> customerAddresses = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(customerFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length >= 6) {
                    String customerName = data[1].trim();
                    String address = data[3].trim() + ", " + data[4].trim() + ", " + data[5].trim();
                    customerAddresses.put(customerName, address);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading customer info file: " + e.getMessage());
        }
        return customerAddresses;
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
