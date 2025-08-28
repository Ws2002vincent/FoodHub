package foodhub;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class foodManager {
    private final String filePath;
    private final List<FoodItem> foodItems;
    private JComboBox<String> VendorCombo;
    private JTextArea DescArea;
    private JTable table;

    public foodManager(String filePath) {
        this.filePath = filePath;
        this.foodItems = loadFoodData(filePath);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JTextArea getDescArea() {
        return DescArea;
    }

    public void setDescArea(JTextArea DescArea) {
        this.DescArea = DescArea;
    }

    public JComboBox<String> getVendorComboBox() {
        return VendorCombo;
    }

    public void setVendorComboBox(JComboBox<String> vendorComboBox) {
        this.VendorCombo = vendorComboBox;
    }

 
    public List<FoodItem> loadFoodData(String filePath) {
        List<FoodItem> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {  
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String desc = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());
                    String availability = parts[4].trim();
                    String imagepath = parts[5].trim();
                    String vendorId = parts[6].trim();
                    items.add(new FoodItem(id, name, desc, availability, vendorId, price, imagepath));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public static Set<String> getVendorIds(List<FoodItem> foodItems) {
        return foodItems.stream()
                .map(FoodItem::getVendorId)
                .collect(Collectors.toSet());
    }

    public void loadVendorIds() {
        VendorCombo.removeAllItems(); 
        Set<String> vendorIds = getVendorIds(foodItems);
        VendorCombo.addItem("All"); 
        for (String vendorId : vendorIds) {
            VendorCombo.addItem(vendorId);
        }
    }

    public List<FoodItem> filterFoodItems(String selectedVendor, String selectedAvailability) {
        return foodItems.stream()
                .filter(food -> (selectedVendor.equals("All") || food.getVendorId().equals(selectedVendor))  &&
                        (selectedAvailability.equals("All") || food.getAvailability().equals(selectedAvailability)))
                .collect(Collectors.toList());
    }

    public void updateTableData(DefaultTableModel tableModel, String vendor, String availability) {
        List<FoodItem> filteredItems = filterFoodItems(vendor, availability);

       
        tableModel.setRowCount(0);

       
        for (FoodItem item : filteredItems) {
            tableModel.addRow(new Object[]{
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getAvailability()
            });
        }
    }
    
    public void showFoodDescription() {
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Get the description from the selected row (assuming column 2 is Description)
                String description = (String) table.getValueAt(selectedRow, 1);
                DescArea.setText(description);
            }
        });
    }
    
    public void saveFoodItems() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        for (FoodItem foodItem : foodItems) {
            writer.write(String.format("%s|%s|%s|%s|%s%n", 
                foodItem.getId(), foodItem.getName(), foodItem.getDesc(), 
                foodItem.getPrice(), foodItem.getAvailability()));
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Cannot save food items to database!", "Error saving food items", JOptionPane.ERROR_MESSAGE);
    }
}
    
    
}