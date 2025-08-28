/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package foodhub;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Chan Jia Zhil
 */
public class CustomerMenu extends javax.swing.JFrame {

    /**
     * Creates new form CustomerMenu
     */
    private HashMap<String, ArrayList<Double>> feedbackMap = new HashMap<>();
    private ArrayList<VendorCard> vendorCards = new ArrayList<>();
    private ArrayList<CartCard> cartCards = new ArrayList<>();
    Customer currentCustomer = new Customer();
    private String username;
    private String cartFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Cart.txt";
    private String orderHistoryFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt";
    private String paymentFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Payment.txt";
    private String groupedOrdersFileName = "C:\\FoodHub\\src\\foodhub\\Database\\GroupedOrders.txt";
    private String menuInfoFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Menuinfo.txt";
    private String vendorFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Vendor.txt";
    private String customerFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Customer.txt";
    private String customerComplaintsFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Complaints.txt";
    private String notificationFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Notification.txt";
    private String feedbackFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Feedbacks.txt";
    private String runnerFeedbackFileName = "C:\\FoodHub\\src\\foodhub\\Database\\RunnerFeedback";
    private String deliveriesFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Deliveries.txt";
    private String tasksFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Tasks.txt";
    private String lastOrderHistoryID;
    private String lastCustomerComplaintID;
    private String lastPaymentID;
    private String customerCredit;
    private String customerNotificationCounts;

    public CustomerMenu(String username) {
        this.username = username;
        initComponents();
        loadFeedbacks();
        showVendorNameAndRating();
        calculateCartQuantityAndTotalPrice();
        showCartItems();
        getOrderHistoryUniqueID();
        setCustomerProfilePicAndUsername();
        getLatestCustomerComplaintID();
        getLatestPaymentID();
        refundCancelledOrder();
        refundRunnerCancelledOrder();
        getContentPane().setBackground(Color.WHITE);
        vendorPanel.setBackground(Color.WHITE);
        cartPanel.setBackground(Color.WHITE);
        jScrollPane1.setBackground(Color.WHITE);

        vendorSearch.setText("Search Vendors...");

        vendorSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (vendorSearch.getText().equals("Search Vendors...")) {
                    vendorSearch.setText("");
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vendorSearch.getText().isEmpty()) {
                    vendorSearch.setText("Search Vendors...");
                }
            }
        });

        // Add real-time search functionality
        vendorSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterVendors(vendorSearch.getText());
            }
        });
        countCustomerNotifications();
    }

    // Method to filter vendors based on search input
    private void filterVendors(String searchText) {
        vendorPanel.removeAll();
        ArrayList<VendorCard> filteredCards = new ArrayList<>();

        for (VendorCard card : vendorCards) {
            if (card.vendorName.getText().toLowerCase().contains(searchText.toLowerCase())) {
                filteredCards.add(card);
            }
        }

        // Use GridBagLayout for more control over placement
        vendorPanel.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new java.awt.Insets(0, 8, 0, 0); // Spacing between cards

        for (int i = 0; i < filteredCards.size(); i++) {
            vendorPanel.add(filteredCards.get(i), gbc);

            // Update grid position
            gbc.gridx++;
            if (gbc.gridx == 3) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }

        // Refresh the panel
        vendorPanel.revalidate();
        vendorPanel.repaint();
    }

    private void loadFeedbacks() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Feedbacks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                String vendorID = parts[2];
                double rating = Double.parseDouble(parts[4]);

                feedbackMap.putIfAbsent(vendorID, new ArrayList<>());
                feedbackMap.get(vendorID).add(rating);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to calculate the average rating for a vendor
    private double calculateAverageRating(String vendorID) {
        if (!feedbackMap.containsKey(vendorID)) {
            return 0.0; // No ratings available
        }
        ArrayList<Double> ratings = feedbackMap.get(vendorID);
        double sum = 0;
        for (double rating : ratings) {
            sum += rating;
        }
        return sum / ratings.size();
    }

    private void showVendorNameAndRating() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Vendor.txt"))) {
            String line;
            //Clear the vendorCards list
            vendorCards.clear();
            vendorPanel.removeAll();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts[4].equals("true")) {
                    // calculate each vendor's overall rating
                    String vendorID = parts[0];
                    String vendorImageName = parts[5].trim();
                    double averageRating = calculateAverageRating(vendorID);
                    // Create a new VendorCard for each vendor
                    VendorCard card = new VendorCard();
                    // Assign each button an unique vendor ID
                    card.vendorImage.addActionListener(e -> {
                        new CustomerMenu_2(vendorID, averageRating, this.username).setVisible(true);
                        dispose();
                    });
                    String imagePath = "C:\\FoodHub\\src\\foodhub\\images\\" + vendorImageName; // Image file path
                    try {
                        ImageIcon icon = new ImageIcon(imagePath);
                        // Scale the image to fit the button
                        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust size as needed
                        card.vendorImage.setIcon(new ImageIcon(img));
                    } catch (Exception e) {
                        System.out.println("Image not found for vendor: " + vendorID);
                        e.printStackTrace();
                    }
                    card.vendorName.setText(parts[2]);
                    // Load star image and show it
//                ImageIcon starIcon = new ImageIcon(getClass().getResource("foodhub/images/FoodHub_star_img.png"));
                    card.vendorRating.setText(String.format("%.2f", averageRating));
//                card.vendorStarImg.setIcon(starIcon);

                    // Add the card to the panel
                    vendorCards.add(card);
                    vendorPanel.add(card);
                }
            }
            // Calculate the number of rows needed (3 cards per row)
            int rows = (int) Math.ceil(vendorCards.size() / 3);
            // Set the GridLayout: rows x 3 columns
            vendorPanel.setLayout(new java.awt.GridLayout(rows, 3, 10, 10));

            // Refresh the panel
            vendorPanel.revalidate();
            vendorPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCartItems() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.cartFileName))) {
            String line;

            //Clear the vendorCards list
            cartCards.clear();
            cartPanel.removeAll();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts[1].equals(this.username) && parts[9].equals("No_Place_Order")) {
                    // Create a new VendorCard for each vendor
                    CartCard card = new CartCard();
                    // Assign each button an unique vendor ID
                    String foodImg = parts[8].trim();
                    String imagePath = "C:\\FoodHub\\src\\foodhub\\images\\" + foodImg; // Image file path
                    try {
                        ImageIcon icon = new ImageIcon(imagePath);
                        // Scale the image to fit the button
                        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust size as needed
                        card.cartPic.setIcon(new ImageIcon(img));
                    } catch (Exception e) {
                        System.out.println("Image not found for vendor");
                        e.printStackTrace();
                    }
                    card.cartEdit.addActionListener(e -> {
                        JDialog quantityDialog = new JDialog();
                        quantityDialog.setTitle("Edit Quantity");
                        JPanel panel = new JPanel();
                        panel.setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 10, 5, 10);
                        JLabel label = new JLabel("Enter the quantity you want to edit:");
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.gridwidth = 2;
                        gbc.anchor = GridBagConstraints.CENTER;
                        panel.add(label, gbc);
                        int currentQuantity = Integer.parseInt(parts[4]);
                        SpinnerNumberModel model = new SpinnerNumberModel(currentQuantity, 1, 100, 1);
                        JSpinner quantitySpinner = new JSpinner(model);
                        gbc.gridx = 0;
                        gbc.gridy = 1;
                        gbc.gridwidth = 2;
                        panel.add(quantitySpinner, gbc);
                        JButton addToCartButton = new JButton("Edit");
                        addToCartButton.addActionListener(addToCartEvent -> {
                            int quantity = (int) quantitySpinner.getValue();
                            editSelectedCart(Integer.toString(quantity), parts[0]);
                            quantityDialog.dispose();
                        });
                        gbc.gridx = 0;
                        gbc.gridy = 2;
                        gbc.gridwidth = 2;  // Button spans both columns
                        gbc.anchor = GridBagConstraints.CENTER;
                        panel.add(addToCartButton, gbc);
                        quantityDialog.add(panel);
                        quantityDialog.setSize(300, 180);
                        quantityDialog.setLocationRelativeTo(null);
                        quantityDialog.setResizable(false);
                        quantityDialog.setVisible(true);
                    });
                    card.cartDelete.addActionListener(e -> {
                        deleteSelectedCart(parts[0]);
                    });
                    card.cartFoodName.setText(parts[3]);
                    card.cartQuantity.setText(parts[4]);
                    card.cartPrice.setText(parts[5]);
                    card.cartTotalPrice.setText(parts[6]);

                    // Add the card to the panel
                    cartCards.add(card);
                    cartPanel.add(card);
                }
                // Calculate the number of rows needed (3 cards per row)
                int rows = (int) Math.ceil(cartCards.size() / 1.0);
                // Set the GridLayout: rows x 3 columns
                cartPanel.setLayout(new java.awt.GridLayout(rows, 1, 10, 10));

                // Refresh the panel
                cartPanel.revalidate();
                cartPanel.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedCart(String selectedCart) {
        String cart = selectedCart;
        try {
            // Read from the original file
            BufferedReader reader = new BufferedReader(new FileReader(this.cartFileName));
            // Write to a temporary file
            BufferedWriter writer = new BufferedWriter(new FileWriter("Cart_temp.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                // Skip the target row to delete
                if (!line.startsWith(cart)) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            reader.close();
            writer.close();

            // Replace the original file with the temporary file
            File originalFile = new File(this.cartFileName);
            File tempFile = new File("Cart_temp.txt");

            if (originalFile.delete()) {
                tempFile.renameTo(originalFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cartPanel.removeAll(); // Clear previous components
        showCartItems();       // Reload cart items
        cartPanel.revalidate(); // Revalidate the panel
        cartPanel.repaint();
        calculateCartQuantityAndTotalPrice();
    }

    private void editSelectedCart(String quantity, String selectedRow) {
        String newQuantity = quantity;
        String row = selectedRow;
        try {
            // Read from the original file
            BufferedReader reader = new BufferedReader(new FileReader(this.cartFileName));
            // Write to a temporary file
            BufferedWriter writer = new BufferedWriter(new FileWriter("Cart_temp.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the row matches the target row ID
                if (line.startsWith(row)) {
                    // Split the row by '|' to extract individual columns
                    String[] columns = line.split(" \\| ");

                    // Modify the quantity (index 4) to the new value
                    columns[4] = String.valueOf(newQuantity);
                    int quantity2 = Integer.valueOf(newQuantity);
                    double singlePrice = Double.valueOf(columns[5]);
                    columns[6] = String.valueOf(quantity2 * singlePrice);

                    // Reconstruct the row with the modified quantity
                    String updatedLine = String.join(" | ", columns);
                    writer.write(updatedLine);
                } else {
                    // Write the unmodified row to the temporary file
                    writer.write(line);
                }
                writer.newLine();
            }

            reader.close();
            writer.close();

            // Replace the original file with the updated file
            File originalFile = new File(this.cartFileName);
            File tempFile = new File("Cart_temp.txt");

            if (originalFile.delete()) {
                tempFile.renameTo(originalFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cartPanel.removeAll(); // Clear previous components
        showCartItems();       // Reload cart items
        cartPanel.revalidate(); // Revalidate the panel
        cartPanel.repaint();
        calculateCartQuantityAndTotalPrice();
    }

    private void calculateCartQuantityAndTotalPrice() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.cartFileName));
            String line;
            int totalQuantity = 0;
            double totalPrice = 0.00;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts[1].equals(this.username) && parts[9].equals("No_Place_Order")) {
                    int quantity = Integer.valueOf(parts[4]);
                    totalQuantity += quantity;
                    double price = Double.parseDouble(parts[6]);
                    totalPrice += price;
                }
            }
            reader.close();
            String totalQuantity2 = String.format("%.2f", totalPrice);
            total.setText("Total: RM " + totalQuantity2);
            quantity.setText("Quantity: " + String.valueOf(totalQuantity));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCustomerOrderHistory() {
        List<String[]> orders = new ArrayList<>();
        Map<String, List<String[]>> groupedOrders = new LinkedHashMap<>();
        int groupCounter = 1;
        Map<String, Integer> groupMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(this.orderHistoryFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length >= 10) {
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = parts[i].trim(); // Trim each part
                    }
                    if (parts[1].equals(this.username)) { // Check if username matches
                        String dateTime = parts[5]; // 5th index (Date & Time)

                        // If this Date & Time is not already grouped, create a new list
                        groupedOrders.putIfAbsent(dateTime, new ArrayList<>());
                        groupedOrders.get(dateTime).add(parts);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.groupedOrdersFileName))) {
            for (Map.Entry<String, List<String[]>> entry : groupedOrders.entrySet()) {
                String dateTime = entry.getKey();
                List<String[]> groupList = entry.getValue();

                // Assign a unique group number
                groupMap.put(dateTime, groupCounter++);

                for (String[] order : groupList) {
                    // Insert the group number at index 1 (second position)
                    List<String> updatedOrder = new ArrayList<>(Arrays.asList(order));
                    updatedOrder.add(1, String.valueOf(groupMap.get(dateTime)));

                    // Write the updated order to the file
                    writer.write(String.join(" | ", updatedOrder));
                    writer.newLine();
                }
            }

            System.out.println("Orders grouped and written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JDialog dialog = new JDialog();
        dialog.setTitle("My Orders");
        JPanel customerOrderHistoryPanel = new JPanel();
        customerOrderHistoryPanel.setLayout(new BoxLayout(customerOrderHistoryPanel, BoxLayout.Y_AXIS));

        try (BufferedReader reader = new BufferedReader(new FileReader(this.groupedOrdersFileName))) {
            String line;
            Map<String, Double> groupTotalPrices = new HashMap<>(); // Stores total price per group
            Map<String, String[]> groupRepresentatives = new HashMap<>(); // Stores first row per group

            // Step 1: Read all orders and store data
            while ((line = reader.readLine()) != null) {
                String[] order = line.split("\\|");
                for (int i = 0; i < order.length; i++) {
                    order[i] = order[i].trim();
                }

                String groupNumber = order[1];  // Group Number
                double totalPrice = 0.0;

                // Parse total price safely
                try {
                    totalPrice = Double.parseDouble(order[8].trim());  // Ensure no spaces interfere
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing total price for order: " + Arrays.toString(order));
                    continue;
                }

                // Accumulate total price for each group
                groupTotalPrices.put(groupNumber, groupTotalPrices.getOrDefault(groupNumber, 0.0) + totalPrice);

                // Store first occurrence of each group for display
                groupRepresentatives.putIfAbsent(groupNumber, order);
            }

            // Step 2: After reading all orders, create a card for each group
            for (String groupNumber : groupRepresentatives.keySet()) {
                String[] orderss = groupRepresentatives.get(groupNumber);

                // Create a new CustomerOrderHistoryCard instance
                CustomerOrderHistoryCard COHC = new CustomerOrderHistoryCard();
                COHC.jButton2.setEnabled(false);
                COHC.jButton3.setEnabled(false);
                COHC.jButton4.setEnabled(false);
                COHC.jButton5.setEnabled(false);

                // Extract relevant values
                String orderMethod = orderss[4];  // Order Method
                String newOrderMethod = changeOrderMethodFormat(orderMethod);
                String orderDate = orderss[6];    // Date & Time
                String deliveryFee = orderss[7];  // Delivery Fee
                String orderStatus = orderss[9];  // Order Status
                String runnerAnswer = orderss[11];

                if (!orderMethod.equals("Delivery") && orderStatus.equals("Pending")) {
                    COHC.jButton2.setEnabled(true);
                }
                if (!orderMethod.equals("Delivery") && orderStatus.equals("Completed")) {
                    COHC.jButton3.setEnabled(true);
                    COHC.jButton4.setEnabled(true);
                }
                if (!orderMethod.equals("Delivery") && orderStatus.equals("Vendor Reviewed")) {
                    COHC.jButton3.setEnabled(true);
                }
                if (!orderMethod.equals("Delivery") && orderStatus.equals("Vendor and Runner Reviewed")) {
                    COHC.jButton3.setEnabled(true);
                }
                if (orderMethod.equals("Delivery") && orderStatus.equals("Pending")) {
                    COHC.jButton2.setEnabled(true);
                }
                if (orderMethod.equals("Delivery") && orderStatus.equals("Completed") && runnerAnswer.equals("true")) {
                    COHC.jButton3.setEnabled(true);
                    COHC.jButton4.setEnabled(true);
                }
                if (orderMethod.equals("Delivery") && orderStatus.equals("Vendor Reviewed") && runnerAnswer.equals("true")) {
                    COHC.jButton3.setEnabled(true);
                    COHC.jButton5.setEnabled(true);
                }
                if (orderMethod.equals("Delivery") && orderStatus.equals("Vendor and Runner Reviewed") && runnerAnswer.equals("true")) {
                    COHC.jButton3.setEnabled(true);
                }
                double totalGroupPrice = groupTotalPrices.get(groupNumber) + Double.valueOf(deliveryFee); // Get total price for the group

                // Assign values to the card labels
                COHC.jLabel1.setText(groupNumber);
                COHC.jLabel3.setText("Order " + groupNumber);
                COHC.jLabel4.setText("Date: " + orderDate);
                COHC.jLabel5.setText("Total Price: RM " + String.format("%.2f", totalGroupPrice)); // Corrected total price
                COHC.jLabel9.setText(newOrderMethod);
                COHC.jLabel11.setText("RM " + deliveryFee + "0");
                COHC.jLabel7.setText(orderStatus);
                COHC.jLabel13.setText("Order " + groupNumber + " / " + groupRepresentatives.size());
                COHC.jButton1.addActionListener(e -> {
                    String selectedGroup = groupNumber;
                    JDialog dialog2 = new JDialog();
                    dialog.setTitle("My Order Details");
                    JPanel customerOrderHistoryPanel2 = new JPanel();
                    customerOrderHistoryPanel2.setLayout(new BoxLayout(customerOrderHistoryPanel2, BoxLayout.Y_AXIS));

                    // Step 1: Read Vendor.txt and store VendorID -> VendorName
                    Map<String, String> vendorMap = new HashMap<>();
                    try (BufferedReader reader2 = new BufferedReader(new FileReader(this.vendorFileName))) {
                        String line2;
                        while ((line2 = reader2.readLine()) != null) {
                            String[] columns = line2.split("\\|");
                            if (columns.length > 2) {
                                String vendorID = columns[0].trim(); // V1
                                String vendorName = columns[2].trim(); // Jacob's Burger
                                vendorMap.put(vendorID, vendorName);
                            }
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }

                    // Step 2: Read MenuInfo.txt and store FoodID -> FoodName & Price
                    Map<String, String> foodMap = new HashMap<>();
                    try (BufferedReader reader2 = new BufferedReader(new FileReader(this.menuInfoFileName))) {
                        String line2;
                        while ((line2 = reader2.readLine()) != null) {
                            String[] columns = line2.split("\\|");
                            if (columns.length > 4) {
                                String foodID = columns[0].trim();     // F1
                                String foodName = columns[1].trim();   // Nasi Lemak Ayam Rendang
                                String foodPrice = columns[3].trim();  // 12.9
                                String foodImg = columns[5].trim();  // Food Image
                                foodMap.put(foodID, foodName + "|" + foodPrice + "|" + foodImg);
                            }
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }

                    // Step 3: Read Orders.txt and generate order cards
                    try (BufferedReader reader2 = new BufferedReader(new FileReader(this.groupedOrdersFileName))) {
                        String line2;
                        int count = 1;
                        while ((line2 = reader2.readLine()) != null) {
                            String[] columns = line2.split("\\|");
                            if (columns.length > 5) {
                                String orderID = columns[0].trim();
                                String groupOrder = columns[1].trim();
                                String customerName = columns[2].trim();
                                String vendorID = columns[3].trim();
                                String foodList = columns[5].trim();

                                // Show only for logged-in user
                                if (!customerName.equals(this.username)) {
                                    continue;
                                }

                                String vendorName = vendorMap.getOrDefault(vendorID, "Unknown Vendor");

                                // Parse food list
                                foodList = foodList.replaceAll("[\\[\\]]", ""); // Remove brackets
                                String[] foodItems = foodList.split(",");

                                // Build card content
                                for (String item : foodItems) {
                                    String[] parts = item.trim().split(":"); // Example: F17:2:12.00
                                    if (parts.length == 3 && groupOrder.equals(selectedGroup)) {
                                        String foodID = parts[0];
                                        int quantity = Integer.parseInt(parts[1]);
                                        double singlePrice = Double.parseDouble(parts[2]);
                                        double totalPrice = quantity * singlePrice;

                                        if (foodMap.containsKey(foodID)) {
                                            String[] foodDetails = foodMap.get(foodID).split("\\|");
                                            String foodName = foodDetails[0];
                                            String foodImg = foodDetails[2];
                                            String imagePath = "C:\\FoodHub\\src\\foodhub\\images\\" + foodImg; // Image file path
                                            ImageIcon icon = new ImageIcon(imagePath);
                                            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust size as needed

                                            // Print card format
                                            CustomerOrderHistoryCard_2 COHC2 = new CustomerOrderHistoryCard_2();
                                            COHC2.jLabel1.setText(String.valueOf(count));
                                            COHC2.jLabel3.setText(vendorName);
                                            COHC2.jLabel6.setText(foodName);
                                            COHC2.jLabel2.setText("x" + quantity);
                                            COHC2.jLabel5.setText(String.valueOf(singlePrice) + "0");
                                            COHC2.jLabel4.setText(String.valueOf(totalPrice) + "0");
                                            COHC2.jLabel7.setIcon(new ImageIcon(img));
                                            count += 1;
                                            customerOrderHistoryPanel2.add(COHC2);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    dialog2.add(new JScrollPane(customerOrderHistoryPanel2));

                    dialog2.setSize(900, 500);
                    dialog2.setLocationRelativeTo(null);  // Center the dialog
                    dialog2.setVisible(true);  // Show the dialog

                });
                COHC.jButton2.addActionListener(e -> {
                    String selectedOrderForCancel = groupNumber;
                    int result = JOptionPane.showConfirmDialog(
                            null,
                            "Do you want to cancel Order " + selectedOrderForCancel + "?",
                            "Cancel Order",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (result == JOptionPane.YES_OPTION) {
                        ArrayList<String> selectedOrdersArray = new ArrayList<>();
                        try (BufferedReader reader2 = new BufferedReader(new FileReader(this.groupedOrdersFileName))) {
                            String line2;
                            double totalPrice = 0.0;
                            String deliveryFee2 = "";
                            while ((line2 = reader2.readLine()) != null) {
                                String[] columns = line2.split("\\|");
                                if (columns[1].trim().equals(selectedOrderForCancel) && columns[2].trim().equals(this.username)) {
                                    selectedOrdersArray.add(columns[0].trim());
                                    double totalPrice2 = Double.valueOf(columns[8]);
                                    totalPrice += totalPrice2;
                                    deliveryFee2 = columns[7];
                                }
                            }
                            List<String> updatedLines = new ArrayList<>();
                            try (BufferedReader br = new BufferedReader(new FileReader(this.orderHistoryFileName))) {
                                String line3;
                                while ((line3 = br.readLine()) != null) {
                                    String[] parts = line3.split(" \\| "); // Split by " | "

                                    if (selectedOrdersArray.contains(parts[0])) {
                                        parts[8] = "Cancelled"; // Update status
                                    }

                                    updatedLines.add(String.join(" | ", parts)); // Reconstruct the line
                                }
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }

                            // Write the modified content back to the file
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.orderHistoryFileName))) {
                                for (String updatedLine : updatedLines) {
                                    bw.write(updatedLine);
                                    bw.newLine(); // Ensure each line remains separate
                                }
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                            IncreaseCreditAfterCancel(Double.valueOf(this.customerCredit), totalPrice + Double.valueOf(deliveryFee2));
                            JOptionPane.showMessageDialog(null, "Order " + selectedOrderForCancel + " Cancelled Successfully.",
                                    "Info", JOptionPane.INFORMATION_MESSAGE);
                            setCustomerProfilePicAndUsername();
                            dialog.dispose();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                COHC.jButton3.addActionListener(e -> {
                    String selectedOrderForReorder = groupNumber;
                    int result = JOptionPane.showConfirmDialog(
                            null,
                            "Do you want to reorder Order " + selectedOrderForReorder + "?",
                            "Submit Complaint",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (result == JOptionPane.YES_OPTION) {
                        ArrayList<String> selectedOrdersArray = new ArrayList<>();
                        try (BufferedReader reader2 = new BufferedReader(new FileReader(this.groupedOrdersFileName))) {
                            String line2;
                            double totalPrice = 0.0;
                            String deliveryFee2 = "";
                            while ((line2 = reader2.readLine()) != null) {
                                String[] columns = line2.split("\\|");
                                if (columns[1].trim().equals(selectedOrderForReorder) && columns[2].trim().equals(this.username)) {
                                    selectedOrdersArray.add(columns[0].trim());
                                    double totalPrice2 = Double.valueOf(columns[8]);
                                    totalPrice += totalPrice2;
                                    deliveryFee2 = columns[7];
                                }
                            }
                            if (Double.valueOf(this.customerCredit) >= totalPrice) {
                                try {
                                    // Read all lines from the file
                                    List<String> lines = new ArrayList<>();
                                    List<String> newLines = new ArrayList<>();
                                    BufferedReader br = new BufferedReader(new FileReader(this.orderHistoryFileName));
                                    String line3;
                                    String newTimestamp = new SimpleDateFormat("dd-MM-yyyy hh:mm:ssa").format(new Date());

                                    while ((line3 = br.readLine()) != null) {
                                        lines.add(line3);
                                        String[] parts = line3.split(" \\| ");

                                        // If the order ID matches, duplicate it
                                        if (selectedOrdersArray.contains(parts[0])) {
                                            String newOrderID = this.lastOrderHistoryID;

                                            // Replace the order ID and timestamp
                                            parts[0] = newOrderID;
                                            parts[5] = newTimestamp;
                                            parts[8] = "Pending";
                                            parts[10] = "none";

                                            // Join the updated parts back into a string
                                            String newLine = String.join(" | ", parts);
                                            newLines.add(newLine);
                                            int intLastID = Integer.parseInt(this.lastOrderHistoryID.substring(2)); // Get "002" -> 2
                                            intLastID++; // Increment the ID
                                            this.lastOrderHistoryID = String.format("OR%03d", intLastID); // Format as "OR003", "OR004", etc.
                                        }
                                    }
                                    br.close();

                                    // Write back all lines, including new duplicates
                                    BufferedWriter bw = new BufferedWriter(new FileWriter(this.orderHistoryFileName));
                                    for (String l : lines) {
                                        bw.write(l);
                                        bw.newLine();
                                    }
                                    for (String nl : newLines) {
                                        bw.write(nl);
                                        bw.newLine();
                                    }
                                    bw.close();
                                    updateCustomerCredit(Double.valueOf(this.customerCredit), totalPrice + Double.valueOf(deliveryFee2));
                                    JOptionPane.showMessageDialog(null, "Order " + selectedOrderForReorder + " Reordered Successfully.",
                                            "Info", JOptionPane.INFORMATION_MESSAGE);
                                    setCustomerProfilePicAndUsername();
                                    dialog.dispose();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Insufficient credit, please topup first."
                                        + "\n\n - FoodHub",
                                        "Info", JOptionPane.WARNING_MESSAGE);
                            }

                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                COHC.jButton4.addActionListener(e -> {
                    String selectedOrderForReview = groupNumber;
                    JDialog dialog4 = new JDialog();
                    dialog4.setTitle("Customer Vendor Feedback");
                    JPanel feedbackPanel = new JPanel();
                    feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));

                    CustomerFeedbackCard CFC = new CustomerFeedbackCard();
                    CFC.jTextArea1.setLineWrap(true);
                    CFC.jTextArea1.setWrapStyleWord(true);

                    CFC.jTextArea1.addFocusListener(new java.awt.event.FocusListener() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent e) {
                            if (CFC.jTextArea1.getText().equals("Please provide your feedback here.")) {
                                CFC.jTextArea1.setText(""); // Clear text when focused
                                CFC.jTextArea1.setForeground(Color.BLACK); // Change text color to black
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent e) {
                            if (CFC.jTextArea1.getText().isEmpty()) {
                                CFC.jTextArea1.setText("Please provide your feedback here."); // Reset placeholder text
                                CFC.jTextArea1.setForeground(Color.GRAY); // Set text color back to gray
                            }
                        }
                    });

                    feedbackPanel.add(CFC);
                    dialog4.add(feedbackPanel);

                    dialog4.setSize(486, 450);
                    dialog4.setLocationRelativeTo(null);
                    dialog4.setVisible(true);
                    CFC.jButton1.addActionListener(e2 -> {
                        String feedback = CFC.jTextArea1.getText();
                        String rating = CFC.getSelectedRating();
                        if (rating.equals("")) {
                            JOptionPane.showMessageDialog(null, "Please select a rating.",
                                    "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else if (feedback.equals("Please provide your feedback here.") || feedback.equals("")) {
                            JOptionPane.showMessageDialog(null, "Please provide your feedback.",
                                    "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            int result = JOptionPane.showConfirmDialog(
                                    null,
                                    "Do you want to provide feedback for Order " + selectedOrderForReview + "?",
                                    "Submit Complaint",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE
                            );

                            if (result == JOptionPane.YES_OPTION) {
                                String ordersFileName = this.groupedOrdersFileName;
                                String feedbackFileName = this.feedbackFileName;

                                List<String> groupOrders = new ArrayList<>();

                                try (BufferedReader br = new BufferedReader(new FileReader(ordersFileName))) {
                                    String line4;

                                    // Read the orders file and find orders with matching group number
                                    while ((line4 = br.readLine()) != null) {
                                        String[] data = line4.split(" \\| ");
                                        int currentGroup = Integer.parseInt(data[1]);  // Extract group number

                                        if (String.valueOf(currentGroup).equals(selectedOrderForReview)) {
                                            groupOrders.add(line4);
                                        }
                                    }
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }

                                // If no matching group orders found, return
                                if (groupOrders.isEmpty()) {
                                    System.out.println("❌ No orders found for group " + groupNumber);
                                    return;
                                }

                                try (BufferedWriter bw = new BufferedWriter(new FileWriter(feedbackFileName, true))) {
                                    int feedbackId = getNextFeedbackId(feedbackFileName); // Get next FBxxx ID

                                    for (String order : groupOrders) {
                                        String[] orderData = order.split(" \\| ");
                                        String orderNumber = orderData[0];  // Order ID (ORxxx)
                                        String vendorId = orderData[3];     // Vendor ID
                                        String username = orderData[2];     // Username

                                        // Format feedback entry
                                        String feedbackEntry = String.format(
                                                "FB%03d | %s | %s | %s | %s | %s",
                                                feedbackId++, orderNumber, vendorId, username, rating, feedback);

                                        // Write to feedback.txt
                                        bw.write(feedbackEntry);
                                        bw.newLine();
                                    }

                                    System.out.println("✅ Feedback inserted for group " + groupNumber + " successfully!");
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                                updateMainOrdersFile1(groupOrders, this.orderHistoryFileName);
                                dialog4.dispose();
                                JOptionPane.showMessageDialog(null, "Thanks for your feedback for the vendor/s. It's beneficial for us to provide a"
                                        + "better service to you." + "\n\n - FoodHub",
                                        "Info", JOptionPane.INFORMATION_MESSAGE);
                                dialog.dispose();
                            }
                        }
                    });
                });
                COHC.jButton5.addActionListener(e -> {
                    String selectedOrderForReview = groupNumber;
                    JDialog dialog4 = new JDialog();
                    dialog4.setTitle("Customer Runner Feedback");
                    JPanel feedbackPanel = new JPanel();
                    feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));

                    CustomerFeedbackCard CFC = new CustomerFeedbackCard();
                    CFC.jTextArea1.setLineWrap(true);
                    CFC.jTextArea1.setWrapStyleWord(true);

                    CFC.jTextArea1.addFocusListener(new java.awt.event.FocusListener() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent e) {
                            if (CFC.jTextArea1.getText().equals("Please provide your feedback here.")) {
                                CFC.jTextArea1.setText(""); // Clear text when focused
                                CFC.jTextArea1.setForeground(Color.BLACK); // Change text color to black
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent e) {
                            if (CFC.jTextArea1.getText().isEmpty()) {
                                CFC.jTextArea1.setText("Please provide your feedback here."); // Reset placeholder text
                                CFC.jTextArea1.setForeground(Color.GRAY); // Set text color back to gray
                            }
                        }
                    });

                    feedbackPanel.add(CFC);
                    dialog4.add(feedbackPanel);

                    dialog4.setSize(486, 450);
                    dialog4.setLocationRelativeTo(null);
                    dialog4.setVisible(true);
                    CFC.jButton1.addActionListener(e2 -> {
                        String feedback = CFC.jTextArea1.getText();
                        String rating = CFC.getSelectedRating();
                        if (rating.equals("")) {
                            JOptionPane.showMessageDialog(null, "Please select a rating.",
                                    "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else if (feedback.equals("Please provide your feedback here.") || feedback.equals("")) {
                            JOptionPane.showMessageDialog(null, "Please provide your feedback.",
                                    "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            int result = JOptionPane.showConfirmDialog(
                                    null,
                                    "Do you want to provide feedback for Order " + selectedOrderForReview + "?",
                                    "Submit Complaint",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE
                            );

                            if (result == JOptionPane.YES_OPTION) {
                                List<String> groupOrders = new ArrayList<>();

                                // Step 1: Read groupedOrders.txt and find orders matching selectedOrderForReview
                                try (BufferedReader br = new BufferedReader(new FileReader(this.groupedOrdersFileName))) {
                                    String line5;
                                    while ((line5 = br.readLine()) != null) {
                                        String[] data = line5.split(" \\| ");
                                        if (data.length > 1 && data[1].equals(selectedOrderForReview)) {
                                            groupOrders.add(data[0]); // Extract Order ID (e.g., OR004)
                                        }
                                    }
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }

                                // Step 2: Read tasks.txt and find corresponding runner for each order
                                Map<String, String> orderToRunner = new HashMap<>();
                                Map<String, String> orderToCustomer = new HashMap<>();

                                try (BufferedReader br = new BufferedReader(new FileReader(this.tasksFileName))) {
                                    String line6;
                                    while ((line6 = br.readLine()) != null) {
                                        String[] data = line6.split(" \\| ");
                                        if (data.length > 3) {
                                            String orderId = data[2];  // Index 2: Order ID
                                            String runnerId = data[1]; // Index 1: Runner ID
                                            String customerName = data[3]; // Index 3: Customer Name

                                            orderToRunner.put(orderId, runnerId);
                                            orderToCustomer.put(orderId, customerName);
                                        }
                                    }
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                }

                                // Step 3: Insert feedback into Deliveries.txt
                                try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.deliveriesFileName, true))) {
                                    int deliveryId = getNextDeliveryId(this.deliveriesFileName); // Auto-increment Dxx ID

                                    for (String orderId : groupOrders) {
                                        if (orderToRunner.containsKey(orderId)) {
                                            String runnerId = orderToRunner.get(orderId);
                                            String customerName = orderToCustomer.get(orderId);

                                            String deliveryEntry = String.format("D%02d | %s | %s | %s | %s",
                                                    deliveryId, runnerId, customerName, rating, feedback);
                                            bw.write(deliveryEntry);
                                            bw.newLine();
                                            deliveryId++;
                                        }
                                    }
                                    System.out.println("✅ Feedback inserted successfully!");
                                } catch (IOException e7) {
                                    e7.printStackTrace();
                                }
                                updateMainOrdersFile2(groupOrders, this.orderHistoryFileName);
                                dialog4.dispose();
                                JOptionPane.showMessageDialog(null, "Thanks for your feedback for the runner. It's beneficial for us to provide a"
                                        + "better service to you." + "\n\n - FoodHub",
                                        "Info", JOptionPane.INFORMATION_MESSAGE);
                                dialog.dispose();
                            }
                        }
                    });
                });

                // Add the card to the UI panel
                customerOrderHistoryPanel.add(COHC);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.add(new JScrollPane(customerOrderHistoryPanel));

        dialog.setSize(900, 500);
        dialog.setLocationRelativeTo(null);  // Center the dialog
        dialog.setVisible(true);  // Show the dialog
    }

    private void updateMainOrdersFile1(List<String> ordersToUpdate, String fileName) {
        String tempFileName = "temp_orders.txt";
        File inputFile = new File(fileName);
        File tempFile = new File(tempFileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile)); BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");

                if (parts.length >= 10) { // Ensure enough fields exist
                    String orderId = parts[0]; // OR001, OR002, etc.

                    // Check if this order needs to be updated
                    for (String order : ordersToUpdate) {
                        if (order.startsWith(orderId)) {
                            parts[8] = "Vendor Reviewed"; // Change 8th index to "Reviewed"
                            break;
                        }
                    }
                }

                // Write the updated line
                writer.write(String.join(" | ", parts) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace old file with the updated one
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }

        System.out.println("✅ Orders file updated successfully!");
    }

    private void updateMainOrdersFile2(List<String> ordersToUpdate, String fileName) {
        String tempFileName = "temp_orders.txt";
        File inputFile = new File(fileName);
        File tempFile = new File(tempFileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile)); BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");

                if (parts.length >= 10) { // Ensure enough fields exist
                    String orderId = parts[0]; // OR001, OR002, etc.

                    // Check if this order needs to be updated
                    for (String order : ordersToUpdate) {
                        if (order.startsWith(orderId)) {
                            parts[8] = "Vendor and Runner Reviewed"; // Change 8th index to "Reviewed"
                            break;
                        }
                    }
                }

                // Write the updated line
                writer.write(String.join(" | ", parts) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace old file with the updated one
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }

        System.out.println("✅ Orders file updated successfully!");
    }

    private String changeOrderMethodFormat(String orderMethod) {
        if (orderMethod.equals("Take_Away")) {
            return ("Take Away");
        } else if (orderMethod.equals("Dine_In")) {
            return ("Dine In");
        } else {
            return (orderMethod);
        }
    }

    private void getOrderHistoryUniqueID() {
        File file = new File(this.orderHistoryFileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String lastLine = null;
            String line;

            // Read until the last line
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }

            if (lastLine == null) {
                // If the file is empty, start with ID OR001
                this.lastOrderHistoryID = "OR001";
            } else {
                // Extract the ID part (first column)
                String[] parts = lastLine.split(" \\| ");
                String lastID = parts[0]; // e.g., "OR002"

                // Extract the numeric part, increment it, and zero-pad
                int intLastID = Integer.parseInt(lastID.substring(2)); // Get "002" -> 2
                intLastID++; // Increment the ID
                this.lastOrderHistoryID = String.format("OR%03d", intLastID); // Format as "OR003", "OR004", etc.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerProfilePicAndUsername() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.customerFileName))) {
            String line;
            String profilePic = "";
            String credit = "";
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts[1].equals(this.username) && parts.length > 6) {
                    profilePic = parts[6];
                    credit = parts[2];
                }
            }
            String imagePath = "C:\\FoodHub\\src\\foodhub\\images\\" + profilePic;
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            profilePicGUI.setIcon(new ImageIcon(img));
            usernameGUI.setText(this.username);
            jLabel8.setText(credit);
            this.customerCredit = credit;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLatestCustomerComplaintID() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.customerComplaintsFileName))) {
            String lastLine = null;
            String line;

            while ((line = br.readLine()) != null) {
                lastLine = line;
            }

            if (lastLine == null) {
                this.lastCustomerComplaintID = "CM1";
            } else {
                String[] parts = lastLine.split(" \\| ");
                String lastID = parts[0];

                int intLastID = Integer.parseInt(lastID.substring(2));
                intLastID++; // Increment the ID
                this.lastCustomerComplaintID = String.format("CM%d", intLastID);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLatestPaymentID() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.paymentFileName))) {
            String lastLine = null;
            String line;

            while ((line = br.readLine()) != null) {
                lastLine = line;
            }

            if (lastLine == null) {
                this.lastPaymentID = "P1";
            } else {
                String[] parts = lastLine.split(" \\| ");
                String lastID = parts[0];

                int intLastID = Integer.parseInt(lastID.substring(1));
                intLastID++; // Increment the ID
                this.lastPaymentID = String.format("P%d", intLastID);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCustomerCredit(double currentCredit, double totalPrice) {
        double newCredit = currentCredit - totalPrice;
        String formattedCredit = String.format("%.2f", newCredit);

        List<String> fileContent = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(this.customerFileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");

                // Check if this is the row to update
                if (parts[1].equals(this.username)) {
                    parts[2] = String.valueOf(formattedCredit); // Update balance (index 2)
                    found = true;
                }

                // Reconstruct and store updated line
                fileContent.add(String.join(" | ", parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.customerFileName))) {
            for (String updatedLine : fileContent) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void IncreaseCreditAfterCancel(double currentCredit, double totalAmount) {
        double newAmount = currentCredit + totalAmount;
        String newAmount2 = String.format("%.2f", newAmount);

        List<String> fileContent = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(this.customerFileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");

                // Check if this is the row to update
                if (parts[1].equals(this.username)) {
                    parts[2] = String.valueOf(newAmount2); // Update balance (index 2)
                    found = true;
                }

                // Reconstruct and store updated line
                fileContent.add(String.join(" | ", parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.customerFileName))) {
            for (String updatedLine : fileContent) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void countCustomerNotifications() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.notificationFileName))) {
            String line;
            int count = 0;
            int notifiedCount = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts[2].equals(this.username) && (parts[6].equals("Unread"))) {
                    count += 1;
                } else if (parts[2].equals(this.username) && (parts[6].equals("Notified"))) {
                    notifiedCount += 1;
                }
            }
            this.customerNotificationCounts = String.valueOf(count);

            if (notifiedCount > 0 && count > 0) {
                int totalCount = count + notifiedCount;
                this.customerNotificationCounts = String.valueOf(totalCount);
                JOptionPane.showMessageDialog(null, "You have notification/s, please check them."
                        + "\n\n - FoodHub",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            } else if (notifiedCount > 0) {
                this.customerNotificationCounts = String.valueOf(notifiedCount);
            } else if (count > 0) {
                JOptionPane.showMessageDialog(null, "You have notification/s, please check them."
                        + "\n\n - FoodHub",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            jButton5.setText("Notification (" + this.customerNotificationCounts + ")");
            if (count == 0 && notifiedCount == 0) {
                jButton5.setText("Notification");
            }

            List<String> fileContent = new ArrayList<>();
            boolean found = false;

            try (BufferedReader br2 = new BufferedReader(new FileReader(this.notificationFileName))) {
                String line2;

                while ((line2 = br2.readLine()) != null) {
                    String[] parts = line2.split(" \\| ");

                    // Check if this is the row to update
                    if (parts[2].equals(this.username) && parts[6].equals("Unread")) {
                        parts[6] = String.valueOf("Notified");
                        found = true;
                    }

                    // Reconstruct and store updated line
                    fileContent.add(String.join(" | ", parts));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.notificationFileName))) {
                for (String updatedLine : fileContent) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refundCancelledOrder() {
        File inputFile = new File(this.orderHistoryFileName);
        File tempFile = new File("temp_orderhistory.txt");
        File notificationFile = new File(this.notificationFileName);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile)); BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile)); BufferedWriter notifWriter = new BufferedWriter(new FileWriter(notificationFile, true))) { // Append mode

            String line;
            Map<String, List<String[]>> groupedOrders = new LinkedHashMap<>();
            List<String[]> updatedOrders = new ArrayList<>();
            Set<String> groupsToCancel = new HashSet<>();
            Map<String, Integer> groupNumberMap = new LinkedHashMap<>();
            int orderCounter = 1; // "Order 1", "Order 2", etc.
            int notificationId = getNextNotificationId(notificationFile); // Get the next notification ID
            double totalRefundAmount = 0.0;

            // Step 1: Read file and group orders by Date & Time (Index 5)
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" \\| ");
                updatedOrders.add(data);

                if (data[1].equals(this.username)) {
                    groupedOrders.putIfAbsent(data[5], new ArrayList<>());
                    groupedOrders.get(data[5]).add(data);

                    // If any order in the group is cancelled, mark the entire group
                    if ((!data[3].equals("Delivery") || data[3].equals("Delivery")) && data[8].equals("Cancelled") && data[10].equals("none")) {
                        groupsToCancel.add(data[5]);
                    }
                }
            }

            br.close(); // ✅ Close before replacing file

            for (String groupDateTime : groupedOrders.keySet()) {
                groupNumberMap.put(groupDateTime, orderCounter++);
            }

            // Step 2: Process refund for affected groups
            for (String groupDateTime : groupsToCancel) {
                List<String[]> orderGroup = groupedOrders.get(groupDateTime);
                double refundAmount = 0.0;
                boolean deliveryFeeRefunded = false;

                for (String[] order : orderGroup) {
                    refundAmount += Double.parseDouble(order[7]); // Refund product price
                    if (!deliveryFeeRefunded && Double.parseDouble(order[6]) > 0) {
                        refundAmount += Double.parseDouble(order[6]); // Refund delivery fee once
                        deliveryFeeRefunded = true;
                    }
                    order[8] = "Refunded"; // Change status to "Refunded"
                }

                int sequentialGroupNumber = groupNumberMap.get(groupDateTime);

                totalRefundAmount += refundAmount;

                // Step 3: Insert notification using "Order X"
                String notification = String.format(
                        "N%d | %s | %s | Order Status | Your %s has been refunded. RM %.2f has been returned to your account. | %s | Unread",
                        notificationId++, "Refund System", orderGroup.get(0)[1], // Vendor & Username
                        "Order " + sequentialGroupNumber, refundAmount, getCurrentDateTime());

                notifWriter.write(notification);
                notifWriter.newLine();
            }

            // Step 4: Write updated data to temp file
            for (String[] order : updatedOrders) {
                bw.write(String.join(" | ", order));
                bw.newLine();
            }

            bw.flush();
            bw.close();
            notifWriter.flush();
            notifWriter.close();

            // Step 5: Replace old file with updated file
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("❌ Error: Unable to update order history file.");
                return;
            }

            String newTotalRefundAmount = String.format("%.2f", totalRefundAmount);
            processRefund(newTotalRefundAmount);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNextNotificationId(File notificationFile) {
        int maxId = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(notificationFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" \\| ");
                if (data[0].startsWith("N")) {
                    int id = Integer.parseInt(data[0].substring(1)); // Remove "N" and parse the number
                    maxId = Math.max(maxId, id);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maxId + 1; // Return the next available notification ID
    }

    private String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mma");
        return LocalDateTime.now().format(dtf);
    }

    private void processRefund(String refund) {
        double refunds = Double.valueOf(refund);
        double newCredit = (Double.valueOf(this.customerCredit) + refunds);
        String newCredits = String.format("%.2f", newCredit);

        List<String> fileContent = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(this.customerFileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");

                // Check if this is the row to update
                if (parts[1].equals(this.username)) {
                    parts[2] = newCredits; // Update balance (index 2)
                    found = true;
                }

                // Reconstruct and store updated line
                fileContent.add(String.join(" | ", parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.customerFileName))) {
            for (String updatedLine : fileContent) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get next feedback ID (e.g., FB027 → FB028)
    private int getNextFeedbackId(String feedbackFileName) {
        int maxId = 0; // Assume last feedback was FB026

        try (BufferedReader br = new BufferedReader(new FileReader(feedbackFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("FB")) {
                    int id = Integer.parseInt(line.substring(2, 5)); // Extract number from FB027
                    maxId = Math.max(maxId, id);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maxId + 1;
    }

    private void refundRunnerCancelledOrder() {
        File inputFile = new File(this.orderHistoryFileName);
        File tempFile = new File("temp_orderhistory.txt");
        File notificationFile = new File(this.notificationFileName);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile)); BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile)); BufferedWriter notifWriter = new BufferedWriter(new FileWriter(notificationFile, true))) { // Append mode

            String line;
            Map<String, List<String[]>> groupedOrders = new LinkedHashMap<>();
            List<String[]> updatedOrders = new ArrayList<>();
            Set<String> groupsToCancel = new HashSet<>();
            Map<String, Integer> groupNumberMap = new LinkedHashMap<>();
            int orderCounter = 1; // "Order 1", "Order 2", etc.
            int notificationId = getNextNotificationId(notificationFile); // Get the next notification ID
            double totalRefundAmount = 0.0;

            // Step 1: Read file and group orders by Date & Time (Index 5)
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" \\| ");
                updatedOrders.add(data);

                if (data[1].equals(this.username)) {
                    groupedOrders.putIfAbsent(data[5], new ArrayList<>());
                    groupedOrders.get(data[5]).add(data);

                    // If any order in the group is cancelled, mark the entire group
                    if (data[3].equals("Delivery") && data[10].equals("false") && !data[8].equals("Refunded")) {
                        groupsToCancel.add(data[5]);
                    }
                }
            }

            for (String groupDateTime : groupedOrders.keySet()) {
                groupNumberMap.put(groupDateTime, orderCounter++);
            }

            br.close(); // ✅ Close before replacing file

            // Step 2: Process refund for affected groups
            for (String groupDateTime : groupsToCancel) {
                List<String[]> orderGroup = groupedOrders.get(groupDateTime);
                double refundAmount = 0.0;
                boolean deliveryFeeRefunded = false;

                for (String[] order : orderGroup) {
                    refundAmount += Double.parseDouble(order[7]); // Refund product price
                    if (!deliveryFeeRefunded && Double.parseDouble(order[6]) > 0) {
                        refundAmount += Double.parseDouble(order[6]); // Refund delivery fee once
                        deliveryFeeRefunded = true;
                    }
                    order[8] = "Refunded"; // Change status to "Refunded"
                }

                totalRefundAmount += refundAmount;
                int sequentialGroupNumber = groupNumberMap.get(groupDateTime);
                // Step 3: Insert notification using "Order X"
                String notification = String.format(
                        "N%d | %s | %s | Order Status | Your %s has been refunded. RM %.2f has been returned to your account. | %s | Unread",
                        notificationId++, "Refund System", orderGroup.get(0)[1], // Vendor & Username
                        "Order " + sequentialGroupNumber, refundAmount, getCurrentDateTime());

                notifWriter.write(notification);
                notifWriter.newLine();
            }

            // Step 4: Write updated data to temp file
            for (String[] order : updatedOrders) {
                bw.write(String.join(" | ", order));
                bw.newLine();
            }

            bw.flush();
            bw.close();
            notifWriter.flush();
            notifWriter.close();

            // Step 5: Replace old file with updated file
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("❌ Error: Unable to update order history file.");
                return;
            }

            String newTotalRefundAmount = String.format("%.2f", totalRefundAmount);
            processRefund(newTotalRefundAmount);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getNextDeliveryId(String filename) {
        int lastId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("D")) {
                    String[] parts = line.split(" \\| ");
                    lastId = Math.max(lastId, Integer.parseInt(parts[0].substring(1))); // Extract numeric part
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastId + 1; // Increment ID
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vendorSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        vendorPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cartPanel = new javax.swing.JPanel();
        quantity = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        order = new javax.swing.JButton();
        order1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        profilePicGUI = new javax.swing.JLabel();
        usernameGUI = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1200, 580));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        vendorSearch.setBackground(new java.awt.Color(247, 220, 203));
        vendorSearch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 220, 203), null, null));
        vendorSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        vendorSearch.setOpaque(true);

        vendorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        vendorPanel.setLayout(new java.awt.GridLayout(0, 3));
        jScrollPane1.setViewportView(vendorPanel);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("jLabel1");
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(247, 127, 102));
        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("             Find Your Vendor");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        jLabel2.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("jLabel1");
        jLabel3.setOpaque(true);

        jLabel7.setBackground(new java.awt.Color(51, 204, 0));
        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        jLabel7.setText("💲");
        jLabel7.setToolTipText("");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        jLabel7.setOpaque(true);

        jLabel8.setBackground(new java.awt.Color(204, 255, 204));
        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("0.00");
        jLabel8.setToolTipText("");
        jLabel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        jLabel8.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(255, 102, 51));
        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        jLabel9.setText(" 🛒");
        jLabel9.setToolTipText("");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        jLabel9.setOpaque(true);

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("jLabel4");
        jLabel4.setOpaque(true);

        cartPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout cartPanelLayout = new javax.swing.GroupLayout(cartPanel);
        cartPanel.setLayout(cartPanelLayout);
        cartPanelLayout.setHorizontalGroup(
            cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );
        cartPanelLayout.setVerticalGroup(
            cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 328, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(cartPanel);

        quantity.setBackground(new java.awt.Color(255, 102, 51));
        quantity.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        quantity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        quantity.setText("Quantity: 0");
        quantity.setToolTipText("");
        quantity.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        quantity.setOpaque(true);

        total.setBackground(new java.awt.Color(255, 102, 51));
        total.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total.setText("Total: RM 0.00");
        total.setToolTipText("");
        total.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        total.setOpaque(true);

        order.setBackground(new java.awt.Color(247, 127, 102));
        order.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        order.setText("Confirm Order");
        order.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderActionPerformed(evt);
            }
        });

        order1.setBackground(new java.awt.Color(247, 127, 102));
        order1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        order1.setText("Log Out");
        order1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        order1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                order1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(247, 127, 102));
        jButton1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jButton1.setText("Order/ Transaction");
        jButton1.setOpaque(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        profilePicGUI.setBackground(new java.awt.Color(255, 102, 51));
        profilePicGUI.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        profilePicGUI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profilePicGUI.setText("Pic");
        profilePicGUI.setToolTipText("");
        profilePicGUI.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        profilePicGUI.setOpaque(true);

        usernameGUI.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        usernameGUI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameGUI.setText("jLabel5");
        usernameGUI.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jButton2.setBackground(new java.awt.Color(247, 127, 102));
        jButton2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jButton2.setText("Send Complaint");
        jButton2.setOpaque(true);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(247, 127, 102));
        jButton4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jButton4.setText("Credit Topup");
        jButton4.setOpaque(true);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(247, 127, 102));
        jButton5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jButton5.setText("Notification");
        jButton5.setOpaque(true);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(247, 127, 102));
        jButton6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jButton6.setText("View Transaction");
        jButton6.setOpaque(true);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(profilePicGUI, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(usernameGUI, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(order1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(vendorSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 266, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(112, 112, 112)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(order, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(total)
                                            .addComponent(quantity))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(3, 3, 3)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(vendorSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(order)
                        .addContainerGap())))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(profilePicGUI, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameGUI)
                .addGap(38, 38, 38)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(order1)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderActionPerformed
        new Customer_Order(this.username).setVisible(true);
        dispose();
    }//GEN-LAST:event_orderActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        getCustomerOrderHistory();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JDialog dialog = new JDialog();
        dialog.setTitle("Customer Complaint");

        JPanel complaintPanel = new JPanel();
        complaintPanel.setLayout(new BoxLayout(complaintPanel, BoxLayout.Y_AXIS));

        CustomerComplaintCard CCC = new CustomerComplaintCard();
        CCC.jTextArea1.setLineWrap(true);
        CCC.jTextArea1.setWrapStyleWord(true);
        CCC.jTextArea1.setText("Provide your feedback here...");

        CCC.jTextArea1.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (CCC.jTextArea1.getText().equals("Provide your feedback here...")) {
                    CCC.jTextArea1.setText(""); // Clear text when focused
                    CCC.jTextArea1.setForeground(Color.BLACK); // Change text color to black
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (CCC.jTextArea1.getText().isEmpty()) {
                    CCC.jTextArea1.setText("Provide your feedback here..."); // Reset placeholder text
                    CCC.jTextArea1.setForeground(Color.GRAY); // Set text color back to gray
                }
            }
        });

        complaintPanel.add(CCC);

        dialog.add(complaintPanel);

        dialog.setSize(486, 360);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        CCC.jButton1.addActionListener(e -> {
            String selectedComboBoxItem = (String) CCC.jComboBox1.getSelectedItem();
            String complaintComment = CCC.jTextArea1.getText().trim();

            if (selectedComboBoxItem.equals("-")) {
                JOptionPane.showMessageDialog(null, "Please select a complaint category.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (CCC.jTextArea1.getText().equals("Provide your feedback here...")) {
                    JOptionPane.showMessageDialog(null, "Please give comment.", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int result = JOptionPane.showConfirmDialog(
                            null,
                            "Do you want to submit this complaint?",
                            "Submit Complaint",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (result == JOptionPane.YES_OPTION) {
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.customerComplaintsFileName, true))) {
                            bw.write(this.lastCustomerComplaintID + " | " + this.username + " | " + selectedComboBoxItem + " | "
                                    + complaintComment + " | " + "");
                            bw.newLine();
                            bw.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                        getLatestCustomerComplaintID();
                        dialog.dispose();
                        JOptionPane.showMessageDialog(null, "Complaint sent successfully, we hope to investigate it and give answers ASAP."
                                + "\n\n - FoodHub",
                                "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JDialog dialog = new JDialog();
        dialog.setTitle("Customer TopUp");

        JPanel topUpPanel = new JPanel();
        topUpPanel.setLayout(new BoxLayout(topUpPanel, BoxLayout.Y_AXIS));

        CustomerTopUpCard CTUC = new CustomerTopUpCard();
        CTUC.jTextField1.setText("Amount...");

        CTUC.jTextField1.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (CTUC.jTextField1.getText().equals("Amount...")) {
                    CTUC.jTextField1.setText(""); // Clear text when focused
                    CTUC.jTextField1.setForeground(Color.BLACK); // Change text color to black
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (CTUC.jTextField1.getText().isEmpty()) {
                    CTUC.jTextField1.setText("Amount..."); // Reset placeholder text
                    CTUC.jTextField1.setForeground(Color.GRAY); // Set text color back to gray
                }
            }
        });

        topUpPanel.add(CTUC);

        dialog.add(topUpPanel);

        dialog.setSize(486, 170);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        CTUC.jButton1.addActionListener(e -> {
            String topUpAmount = CTUC.jTextField1.getText().trim();

            if (topUpAmount.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a topup amount.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else if (!topUpAmount.matches("\\d+(\\.\\d{1,2})?$")) {
                JOptionPane.showMessageDialog(null, "Invalid money value format.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to request for credit topup?",
                        "Submit Complaint",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (result == JOptionPane.YES_OPTION) {
                    double amount = Double.parseDouble(topUpAmount);
                    String formattedAmount = String.format("%.2f", amount);

                    String newTimestamp = new SimpleDateFormat("dd-MM-yyyy hh:mm:ssa").format(new Date());
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.paymentFileName, true))) {
                        bw.write(this.lastPaymentID + " | " + this.username + " | " + formattedAmount + " | "
                                + newTimestamp + " | " + "Pending");
                        bw.newLine();
                        bw.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    getLatestPaymentID();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(null, "Topup request sent successfully, please wait for notification."
                            + "\n\n - FoodHub",
                            "Info", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
    }//GEN-LAST:event_jButton4ActionPerformed

    private void order1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_order1ActionPerformed
        dispose();
    }//GEN-LAST:event_order1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        JDialog dialog = new JDialog();
        dialog.setTitle("Notifications");
        JPanel customerNotificationPanel = new JPanel();
        customerNotificationPanel.setLayout(new BoxLayout(customerNotificationPanel, BoxLayout.Y_AXIS));

        try (BufferedReader br = new BufferedReader(new FileReader(this.notificationFileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts[2].equals(this.username)) {
                    CustomerNotificationCard CNC = new CustomerNotificationCard();
                    CNC.jTextArea1.setEditable(false);
                    CNC.jTextArea1.setLineWrap(true);
                    CNC.jTextArea1.setWrapStyleWord(true);
                    CNC.jTextArea1.setText(parts[4]);
                    CNC.jLabel1.setText(parts[5]);
                    CNC.jLabel2.setText(parts[3]);
                    CNC.jLabel4.setText("By: " + parts[1]);
                    customerNotificationPanel.add(CNC);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.add(new JScrollPane(customerNotificationPanel));

        dialog.setSize(900, 350);
        dialog.setLocationRelativeTo(null);  // Center the dialog
        dialog.setVisible(true);  // Show the dialog

        List<String> fileContent = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br2 = new BufferedReader(new FileReader(this.notificationFileName))) {
            String line2;

            while ((line2 = br2.readLine()) != null) {
                String[] parts = line2.split(" \\| ");

                // Check if this is the row to update
                if (parts[2].equals(this.username) && parts[6].equals("Notified")) {
                    parts[6] = String.valueOf("Read");
                    found = true;
                }

                // Reconstruct and store updated line
                fileContent.add(String.join(" | ", parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.notificationFileName))) {
            for (String updatedLine : fileContent) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        countCustomerNotifications();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        JDialog dialog = new JDialog();
        dialog.setTitle("View Top-Up Receipts");
        JPanel customerTopUpTransactionPanel = new JPanel();
        customerTopUpTransactionPanel.setLayout(new BoxLayout(customerTopUpTransactionPanel, BoxLayout.Y_AXIS));
        Map<String, String[]> paymentMap = new HashMap<>();

        try (BufferedReader br2 = new BufferedReader(new FileReader(this.paymentFileName))) {
            String line2;

            while ((line2 = br2.readLine()) != null) {
                String[] parts = line2.split(" \\| ");
                if (parts.length > 3 && parts[1].equals(this.username)) {
                    String name = parts[1];
                    String amount = parts[2];
                    String requestTime = parts[3];
                    String status = parts[4];
                    paymentMap.put(name + "|" + status, new String[]{amount, requestTime});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(this.notificationFileName))) {
            String line;
            int count = 1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length >= 7 && parts[2].equals(this.username) && parts[3].equals("Payment Status")) {
                    String name = parts[2];  // User name
                    String status = parts[4].contains("accepted") ? "Accepted" : "Declined";
                    String answeringTime = parts[5]; // Answering time

                    String[] paymentData = paymentMap.get(name + "|" + status);
                    String amount = paymentData[0];
                    String requestTime = paymentData[1];

                    CustomerTopUpTranCard CTUTC = new CustomerTopUpTranCard();
                    if (status.equals("Declined")) {
                        CTUTC.jButton1.setEnabled(false);
                    }
                    CTUTC.jLabel1.setText(String.valueOf(count));
                    CTUTC.jLabel2.setText(amount);
                    CTUTC.jLabel3.setText(String.valueOf(requestTime));
                    CTUTC.jButton1.addActionListener(e -> {
                        JDialog dialog2 = new JDialog();
                        dialog2.setTitle("View Top-Up Receipts");

                        // Create Panel for Receipt
                        JPanel receiptPanel = new JPanel();
                        receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
                        receiptPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

                        // Add Labels for Receipt Content
                        JLabel titleLabel = new JLabel("Digital Receipt");
                        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
                        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel dateLabel = new JLabel("Date: " + answeringTime);
                        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel amountLabel = new JLabel("(+) Credit: RM " + amount);
                        amountLabel.setFont(new Font("Arial", Font.BOLD, 18));
                        amountLabel.setForeground(new Color(34, 139, 34)); // Green color
                        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel companyLabel = new JLabel("Company: FoodHub Sdn. Bhd.");
                        companyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                        companyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel thankYouLabel = new JLabel("- Thanks for choosing us! -");
                        thankYouLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                        thankYouLabel.setForeground(Color.GRAY);
                        thankYouLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        // Add Labels to Panel
                        receiptPanel.add(titleLabel);
                        receiptPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
                        receiptPanel.add(dateLabel);
                        receiptPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                        receiptPanel.add(amountLabel);
                        receiptPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                        receiptPanel.add(companyLabel);
                        receiptPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                        receiptPanel.add(thankYouLabel);

                        // Add Panel to Dialog
                        dialog2.add(receiptPanel);
                        dialog2.setSize(300, 250);
                        dialog2.setLocationRelativeTo(null); // Center the dialog
                        dialog2.setVisible(true); // Show the dialog
                    });

                    count += 1;
                    customerTopUpTransactionPanel.add(CTUTC);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.add(new JScrollPane(customerTopUpTransactionPanel));

        dialog.setSize(900, 350);
        dialog.setLocationRelativeTo(null);  // Center the dialog
        dialog.setVisible(true);  // Show the dialog
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CustomerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerMenu("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cartPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton order;
    private javax.swing.JButton order1;
    private javax.swing.JLabel profilePicGUI;
    private javax.swing.JLabel quantity;
    private javax.swing.JLabel total;
    private javax.swing.JLabel usernameGUI;
    private javax.swing.JPanel vendorPanel;
    private javax.swing.JTextField vendorSearch;
    // End of variables declaration//GEN-END:variables
}
