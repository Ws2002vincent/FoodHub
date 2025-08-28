/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import static foodhub.FilePaths.NOTIFICATION_FILE;
import java.awt.HeadlessException;
/**
 *
 * @author User
 */
public class foodController {

    private foodManager foodManager;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextArea DescArea;
    private JLabel labelVendorId;   
    private JLabel labelFoodName;
    private JLabel labelPrice;
    private JLabel labelsetImageFood;
    private String globalFoodID;
    public String sessionID;
    
    SessionID session = new SessionID();

    public JLabel getSetImageFood() {
        return labelsetImageFood;
    }

    public void setSetImageFood(JLabel setImageFood) {
        this.labelsetImageFood = labelsetImageFood;
    }
    
    public foodManager getFoodManager() {
        return foodManager;
    }

    public void setFoodManager(foodManager foodManager) {
        this.foodManager = foodManager;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
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

    public JLabel getLabelVendorId() {
        return labelVendorId;
    }

    public void setLabelVendorId(JLabel labelVendorId) {
        this.labelVendorId = labelVendorId;
    }

    public JLabel getLabelFoodName() {
        return labelFoodName;
    }

    public void setLabelFoodName(JLabel labelFoodName) {
        this.labelFoodName = labelFoodName;
    }

    public JLabel getLabelPrice() {
        return labelPrice;
    }

    public void setLabelPrice(JLabel labelPrice) {
        this.labelPrice = labelPrice;
    }

    // Constructor to initialize all the fields
    public foodController(foodManager foodManager, DefaultTableModel tableModel, JTable table, JTextArea DescArea,
                          JLabel labelVendorId, JLabel labelFoodName, JLabel labelPrice, JLabel setImageFood) {
        this.foodManager = foodManager;
        this.tableModel = tableModel;
        this.table = table;
        this.DescArea = DescArea;
        this.labelVendorId = labelVendorId;
        this.labelFoodName = labelFoodName;
        this.labelPrice = labelPrice;
        this.labelsetImageFood = setImageFood;
    }

    // Method to update the JLabel values based on the selected row in the table
    public void updateLabelsOnRowSelection() {
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
               
                String vendorId = (String) table.getValueAt(selectedRow, 0); 
                String foodName = (String) table.getValueAt(selectedRow, 1);
                
                
                String description = foodManager.getFoodItems().stream()
                        .filter(food -> food.getVendorId().equals(vendorId))  
                        .map(food -> food.getDesc())
                        .findFirst().orElse("Description not found");

                
                DescArea.setText(description);

               
                labelVendorId.setText("Vendor ID: " + vendorId); 
                labelFoodName.setText("Food Name: " + foodName);

               
                double price = foodManager.getFoodItems().stream()
                        .filter(food -> food.getVendorId().equals(vendorId))  
                        .map(food -> food.getPrice())
                        .findFirst().orElse(0.0);
                
                labelPrice.setText("Price: $" + price);
            }
        });
    }

    // Method to update the table with filtered data
    public void updateTable(String selectedVendor, String selectedAvailability) {
        
        var filteredFoodItems = foodManager.filterFoodItems(selectedVendor, selectedAvailability);

        tableModel.setRowCount(0);
       
        for (FoodItem foodItem : filteredFoodItems) {
            tableModel.addRow(new Object[] {foodItem.getId(), foodItem.getName()});  
            System.out.println("Change Approaches: " + filteredFoodItems);
        }
    }
    
public void ShowFoodDesc() {

    table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {

        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String foodId = (String) table.getValueAt(selectedRow, 0);
            FoodItem selectedFood = getFoodItemById(foodId);
            String imagePath = "src/foodhub/images/" + selectedFood.getImagePath();
            System.out.println("Get image path validation: True : " + imagePath);
            System.out.println("Successfully set:" + imagePath);
            
            if (selectedFood != null) {
                globalFoodID = foodId;
                labelsetImageFood.setText("");
                DescArea.setText(selectedFood.getDesc());
                labelFoodName.setText(selectedFood.getName());
                labelPrice.setText("RM" + selectedFood.getPrice());
                labelVendorId.setText(selectedFood.getVendorId());
               
                File imgFile = new File(imagePath);
                if (!imgFile.exists()) {
                    System.out.println("Image file does not exist: " + imagePath);
                    return; 
                }
             
                try {
                    BufferedImage bufferedImage = ImageIO.read(imgFile);
                    if (bufferedImage == null) {
                        System.out.println("Failed to read image: " + imagePath);
                        return;
                    }
                    
                    Image scaledImage = bufferedImage.getScaledInstance(169, 139, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    labelsetImageFood.setIcon(scaledIcon); // Set the image icon
                    System.out.println("Successfully loaded image.");

                } catch (IOException ex) {

                    System.out.println("Error loading image: " + ex.getMessage());
                }
            }
        }
    });
}
// Helper method to get the FoodItem by its ID from foodManager
private FoodItem getFoodItemById(String foodId) {
   
    List<FoodItem> foodItems = foodManager.getFoodItems(); 

    for (FoodItem item : foodItems) {
        if (item.getId().equals(foodId)) {
            return item;  
        }
        
    }
    return null;  
}

public void deleteFoodItem() {
    int selectedRow = table.getSelectedRow();  
    if (selectedRow != -1) {
       
        String foodID = (String) tableModel.getValueAt(selectedRow, 0);

       
        FoodItem foodItem = foodManager.getFoodItems().stream()
                .filter(f -> f.getId().equals(foodID))
                .findFirst()
                .orElse(null);

       
        if (foodItem != null) {
            foodManager.getFoodItems().remove(foodItem);  
            foodManager.saveFoodItems();  
            tableModel.removeRow(selectedRow);
            
                try {
            sendNotification();
        }  catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error sending notification to vendor! please contact them immediately!: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
            

            JOptionPane.showMessageDialog(null, "Food item deleted successfully!");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Please select a food item to delete.");
    }
}


private void sendNotification() {
    String foodId = globalFoodID;
    System.out.println("globalFoodID is registered as: " + foodId);
    FoodItem selectedFood = getFoodItemById(foodId);
    String foodName = labelFoodName.getText();
    System.out.println("Food name is registered as:" + foodName);
    String prefix = "N";
    String newID = IdGenerator.getNextRoleID(prefix, NOTIFICATION_FILE);
        System.out.println("Generated latest Notification ID:" + newID);
    String senderId = SessionManager.getInstance().getSessionID();
        System.out.println("SenderID registered as: " + senderId);
    String receiverId = labelVendorId.getText();
        System.out.println("ReceiverID registered as: " + receiverId);
    String title = "Deleted Food Item";
    String content = String.format("Your Food" + foodName + "has been deleted due to violation against our policy; inappropriate images or content may have caused this issue.", foodName);
    String view = "unread";
 
    String currentTime = new formatManager().getTime();
 
    String notificationLine = String.format("%s | %s | %s | %s | %s | %s |%s%n", 
        newID, senderId, receiverId, title, content, currentTime, view);

    try {
        writeNotificationToFile(notificationLine);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error saving notification to database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void writeNotificationToFile(String notificationLine) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTIFICATION_FILE, true))) {
        writer.write(notificationLine);
    }
}
}
        



    

