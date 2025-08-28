package foodhub.runner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskHistoryLoader {

    public static List<TaskHistory> loadTask(String filePath, String runnerId) {
        List<TaskHistory> taskHistoryList = new ArrayList<>();
        Map<String, String> orderItemsMap = loadOrderItems("C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt");
        Map<String, String> deliveryFeeMap = loadDeliveryFee("C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                // Ensure at least 5 elements exist before accessing indices
                if (data.length < 7) {
                    System.err.println("Skipping invalid task entry: " + line);
                    continue;
                }

                // Extract necessary fields
                String fileRunnerID = data[1].trim(); // Runner ID from file

                // Check if task belongs to the given runner ID
                if (!fileRunnerID.equals(runnerId)) {
                    continue; // Skip tasks that do not belong to this runner
                }

                // Check if the last attribute (index 10) exists and is "true", skip loading the task
                boolean isCompleted = data.length >= 6 && data[6].trim().equalsIgnoreCase("completed");
                boolean isCancelled = data.length >= 6 && data[6].trim().equalsIgnoreCase("Cancelled");

                if (isCompleted || isCancelled) {
                    String taskID = data[0].trim();
                    String runnerID = data[1].trim();
                    String orderID = data[2].trim();
                    String customerName = data[3].trim();
                    String address = data[4].trim();
                    String quests = data[5].trim();
                    String status = data[6].trim();
                    String deliveredDate = data[7].trim();

                    // Get ordered items (if found in Orderhistory.txt)
                    String orderedItems = orderItemsMap.getOrDefault(orderID, "No items found");
                    String deliveryFee = deliveryFeeMap.getOrDefault(orderID, "5");

                    TaskHistory taskHistory = new TaskHistory(taskID, runnerID, orderID, customerName, address, status, deliveredDate, deliveryFee, orderedItems);
                    taskHistoryList.add(taskHistory);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Order history file not found.");
        } catch (IOException e) {
            System.err.println("Error reading order history file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric data in the order file.");
        }
        return taskHistoryList;
    }

    private static Map<String, String> loadOrderItems(String ordersFilePath) {
        Map<String, String> orderItemsMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length > 4) {
                    String orderID = data[0].trim();
                    String orderedItems = data[4].trim(); // Format: "[F1:2:25.8, F4:1:13.0]"

                    StringBuilder itemNames = new StringBuilder();

                    try {
                        // Remove square brackets if present
                        if (orderedItems.startsWith("[") && orderedItems.endsWith("]")) {
                            orderedItems = orderedItems.substring(1, orderedItems.length() - 1);
                        }

                        // Split by comma to get individual items
                        String[] items = orderedItems.split(",");
                        for (String item : items) {
                            String[] itemDetails = item.trim().split(":");
                            if (itemDetails.length >= 2) {
                                String itemId = itemDetails[0].trim(); // Item ID (e.g., F1)
                                int quantity = Integer.parseInt(itemDetails[1].trim()); // Quantity

                                // Fetch item name from menu
                                String itemName = getItemNameFromMenu(itemId);
                                itemNames.append(itemName).append(" x").append(quantity).append(", ");
                            }
                        }

                        // Remove trailing comma and space
                        if (itemNames.length() > 0) {
                            itemNames.setLength(itemNames.length() - 2);
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing ordered items: " + orderedItems);
                        e.printStackTrace();
                    }

                    orderItemsMap.put(orderID, itemNames.toString().isEmpty() ? "No items found" : itemNames.toString());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading order history file: " + e.getMessage());
        }
        return orderItemsMap;
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
    
    private static Map<String, String> loadDeliveryFee(String orderHistoryPath) {
        Map<String, String> deliveryFee = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(orderHistoryPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length >= 1) {
                    String orderID = data[1].trim();
                    String DeliveryFee = data[6].trim();
                    deliveryFee.put(orderID, DeliveryFee);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading customer info file: " + e.getMessage());
        }
        return deliveryFee;
    }
}
