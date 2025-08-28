/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package foodhub;

import java.awt.Color;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Chan Jia Zhil
 */
public class CustomerMenu_2 extends javax.swing.JFrame {

    /**
     * Creates new form CustomerMenu
     */
    private ArrayList<FoodCard> foodCards = new ArrayList<>();
    private ArrayList<String[]> vendorReviews = new ArrayList<>();
    private List<String[]> customerList = new ArrayList<>();
    private String selectedVendor;
    private String vendorsName;
    private String vendorsDescription;
    private double vendorsRating;
    private String lastCartID;
    private String CartFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Cart.txt";
    private String feedbacksFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Feedbacks.txt";
    private String customerFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Customer.txt";
    private String username;
    private ArrayList<CartCard> cartCards = new ArrayList<>();

    public CustomerMenu_2(String vendorID, double rating, String username) {
        Customer currentCustomer = new Customer();
        currentCustomer.setSelectedVendor(vendorID);
        this.selectedVendor = currentCustomer.getSelectedVendor();
        this.vendorsRating = rating;
        this.username = username;

        initComponents();
        showFoodName();
        // get and show vendor's name and description
        showVendorNameDescriptionAndRating();
        loadVendorReviews();
        calculateCartQuantityAndTotalPrice();
        showCartItems();
        loadCustomerProfile();
        setCustomerProfilePicAndUsername();
        getContentPane().setBackground(Color.WHITE);
        foodPanel.setOpaque(true);
        foodPanel.setBackground(Color.WHITE);

        foodSearch.setText("Search Foods...");

        foodSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (foodSearch.getText().equals("Search Foods...")) {
                    foodSearch.setText("");
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (foodSearch.getText().isEmpty()) {
                    foodSearch.setText("Search Foods...");
                }
            }
        });

        // Add real-time search functionality
        foodSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterFoods(foodSearch.getText());
            }
        });
    }

    // Method to filter foods based on search input
    private void filterFoods(String searchText) {
        foodPanel.removeAll();
        ArrayList<FoodCard> filteredCards = new ArrayList<>();

        for (FoodCard card : foodCards) {
            if (card.foodName.getText().toLowerCase().contains(searchText.toLowerCase())) {
                filteredCards.add(card);
            }
        }

        // Use GridBagLayout for more control over placement
        foodPanel.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new java.awt.Insets(0, 8, 0, 0); // Spacing between cards

        for (int i = 0; i < filteredCards.size(); i++) {
            foodPanel.add(filteredCards.get(i), gbc);

            // Update grid position
            gbc.gridx++;
            if (gbc.gridx == 3) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }

        // Refresh the panel
        foodPanel.revalidate();
        foodPanel.repaint();
    }

    // Method to load all the reviews related to the selected Vendor
    private void loadVendorReviews() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.feedbacksFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts[2].equals(selectedVendor)) {
                    vendorReviews.add(parts);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showFoodName() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Menuinfo.txt"))) {
            String line;

            //Clear the vendorCards list
            foodCards.clear();
            foodPanel.removeAll();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts[6].equals(this.selectedVendor) && parts[4].equals("Available")) {
                    // calculate each vendor's overall rating

//                String vendorID = parts[0];
//                double averageRating = calculateAverageRating(vendorID);
                    // Create a new VendorCard for each vendor
                    FoodCard card = new FoodCard();
                    String selectedFoodID = parts[0];
                    String selectedFoodName = parts[1];
                    String selectedFoodPrice = parts[3];
                    String foodImg = parts[5];
                    card.foodPrice.setText("RM " + selectedFoodPrice);
                    String imagePath = "C:\\FoodHub\\src\\foodhub\\images\\" + foodImg; // Image file path
                    try {
                        ImageIcon icon = new ImageIcon(imagePath);
                        // Scale the image to fit the button
                        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust size as needed
                        card.foodImage.setIcon(new ImageIcon(img));
                    } catch (Exception e) {
                        System.out.println("Image not found for vendor: " + vendorsName);
                        e.printStackTrace();
                    }
                    card.foodImage.addActionListener(e -> {
                        // Create a dialog for item quantity selection

                        JDialog quantityDialog = new JDialog();
                        quantityDialog.setTitle("Select Quantity");

                        // Create a JPanel to hold components with GridBagLayout for structure
                        JPanel panel = new JPanel();
                        panel.setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 10, 5, 10); // Add padding around components

                        // Label for quantity input
                        JLabel label = new JLabel("Enter the quantity you want to add:");
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.gridwidth = 2;  // Span across two columns
                        gbc.anchor = GridBagConstraints.CENTER;
                        panel.add(label, gbc);

                        // Create a JSpinner for quantity selection
                        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 100, 1);  // Starting value, min, max, step
                        JSpinner quantitySpinner = new JSpinner(model);
                        gbc.gridx = 0;
                        gbc.gridy = 1;
                        gbc.gridwidth = 2;  // Span across two columns
                        panel.add(quantitySpinner, gbc);

                        // Create an "Add to Cart" button
                        JButton addToCartButton = new JButton("Add to Cart");
                        addToCartButton.addActionListener(addToCartEvent -> {
                            // Get the selected quantity
                            int quantity = (int) quantitySpinner.getValue();
                            double totalFoodPrice = Double.parseDouble(selectedFoodPrice) * quantity;
                            String str_quantity = Integer.toString(quantity);
                            String str_total_price = Double.toString(totalFoodPrice);
                            // Add the item to the cart 
                            recordAddToCartItems(str_quantity, selectedFoodID,
                                    selectedFoodName, str_total_price, selectedFoodPrice, foodImg);
                            // Close the dialog after adding to the cart
                            quantityDialog.dispose();
                        });

                        // Button alignment and styling
                        gbc.gridx = 0;
                        gbc.gridy = 2;
                        gbc.gridwidth = 2;  // Button spans both columns
                        gbc.anchor = GridBagConstraints.CENTER;
                        panel.add(addToCartButton, gbc);

                        // Set dialog properties
                        quantityDialog.add(panel);
                        quantityDialog.setSize(300, 180);
                        quantityDialog.setLocationRelativeTo(null);  // Center the dialog
                        quantityDialog.setResizable(false); // Prevent resizing of the dialog
                        quantityDialog.setVisible(true);  // Show the dialog
                    });

                    card.foodName.setText(parts[1]);
                    // Load star image and show it
//                ImageIcon starIcon = new ImageIcon(getClass().getResource("foodhub/images/FoodHub_star_img.png"));
//                card.foodRating.setText(String.format("%.2f", averageRating));
//                card.vendorStarImg.setIcon(starIcon);

                    // Add the card to the panel
                    foodCards.add(card);
                    foodPanel.add(card);
                }
            }
            // Calculate the number of rows needed (2 cards per row)
            int rows = (int) Math.ceil(foodCards.size() / 3);
            // Set the GridLayout: rows x 2 columns
            foodPanel.setLayout(new java.awt.GridLayout(rows, 3, 10, 10));

            // Refresh the panel
            foodPanel.revalidate();
            foodPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showVendorNameDescriptionAndRating() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Vendor.txt"))) {
            String line;
            String vendorImageName = "";
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (selectedVendor.equals(parts[0])) {
                    vendorsName = parts[2];
                    vendorsDescription = parts[6];
                    vendorImageName = parts[5];
                    break;
                }
            }
            String imagePath = "C:\\FoodHub\\src\\foodhub\\images\\" + vendorImageName; // Image file path
            try {
                ImageIcon icon = new ImageIcon(imagePath);
                // Scale the image to fit the label
                Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust size as needed
                vendorImage.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                System.out.println("Image not found for vendor: " + selectedVendor);
                e.printStackTrace();
            }
            vendorName.setText(vendorsName);
            vendorDescription.setText(vendorsDescription);
            vendorRating.setText(String.valueOf(vendorsRating));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCartUniqueID() {
        File file = new File(this.CartFileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            if (reader.readLine() == null) {
                this.lastCartID = "1";
            } else {
                String line;
                String lastID = "";
                try (BufferedReader bw = new BufferedReader(new FileReader(file))) {
                    while ((line = bw.readLine()) != null) {
                        String[] parts = line.split(" \\| ");
                        if (parts.length > 0) {
                            lastID = parts[0];
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int intLastID = Integer.valueOf(lastID.substring(1));
                intLastID += 1;
                lastID = Integer.toString(intLastID);
                this.lastCartID = lastID;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recordAddToCartItems(String quantity, String selectedFoodID,
            String selectedFoodName, String totalSelectedFoodPrice, String selectedFoodPrice, String vendorImageName) {
        getCartUniqueID();
        String quantityOfSelectedItem = quantity;
        String foodID = selectedFoodID;
        String foodName = selectedFoodName;
        String totalFoodPrice = totalSelectedFoodPrice;
        String foodPrice = selectedFoodPrice;
        String vendorsImageName = vendorImageName;
        try (FileWriter fw = new FileWriter(this.CartFileName, true)) {

            fw.write("C" + this.lastCartID + " | " + this.username + " | " + foodID + " | " + foodName
                    + " | " + quantityOfSelectedItem + " | " + foodPrice + " | " + totalFoodPrice
                    + " | " + this.selectedVendor + " | " + vendorsImageName + " | "
                    + "No_Place_Order" + "\n");
        } catch (IOException e) {
            System.out.println("Can't register add to cart details");
            e.printStackTrace();
        }
        int newCartID = Integer.valueOf(this.lastCartID);
        newCartID += 1;
        this.lastCartID = String.valueOf(newCartID);
        showCartItems();
        calculateCartQuantityAndTotalPrice();
    }

    private void showCartItems() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.CartFileName))) {
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
                        System.out.println("Image not found for vendor: " + vendorsName);
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
            BufferedReader reader = new BufferedReader(new FileReader(this.CartFileName));
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
            File originalFile = new File(this.CartFileName);
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
            BufferedReader reader = new BufferedReader(new FileReader(this.CartFileName));
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
            File originalFile = new File(this.CartFileName);
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
            BufferedReader reader = new BufferedReader(new FileReader(this.CartFileName));
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

    private void loadCustomerProfile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.customerFileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|"); // Split by '|'
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim(); // Trim whitespace
                }

                if (data.length > 6) { // Ensure valid data
                    String customerName = data[1]; // Index 1 (Customer Name)
                    String imageName = data[6]; // Index 6 (Image Name)
                    this.customerList.add(new String[]{customerName, imageName});
                }
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        foodSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        foodPanel = new javax.swing.JPanel();
        vendorReview = new javax.swing.JButton();
        vendorName = new javax.swing.JLabel();
        vendorDescription = new javax.swing.JLabel();
        vendorRating = new javax.swing.JLabel();
        vendorStarImg = new javax.swing.JLabel();
        vendorImage = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        back = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cartPanel = new javax.swing.JPanel();
        quantity = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        order = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        profilePicGUI = new javax.swing.JLabel();
        usernameGUI = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 580));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        foodSearch.setBackground(new java.awt.Color(247, 220, 203));
        foodSearch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 220, 203), null, null));

        foodPanel.setLayout(new java.awt.GridLayout(0, 3));
        jScrollPane1.setViewportView(foodPanel);

        vendorReview.setText("Check Review");
        vendorReview.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        vendorReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendorReviewActionPerformed(evt);
            }
        });

        vendorName.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        vendorName.setText("Vendor Name");

        vendorDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        vendorDescription.setText("Description");

        vendorRating.setText("Rating");

        vendorStarImg.setText("⭐");

        vendorImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vendorImage.setText("Vendor Image");

        jLabel2.setBackground(new java.awt.Color(247, 127, 102));
        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("             Choose Your Food");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        jLabel2.setOpaque(true);

        back.setBackground(new java.awt.Color(247, 127, 102));
        back.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        back.setText("Back");
        back.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

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
            .addGap(0, 319, Short.MAX_VALUE)
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

        jLabel7.setBackground(new java.awt.Color(51, 204, 0));
        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        jLabel7.setText("💲");
        jLabel7.setToolTipText("");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        jLabel7.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("jLabel5");
        jLabel5.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("jLabel3");
        jLabel3.setOpaque(true);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(profilePicGUI, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(usernameGUI, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(vendorImage, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(vendorRating, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vendorStarImg))
                                    .addComponent(vendorName, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(vendorDescription, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(vendorReview, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(back)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(foodSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(order, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(88, 88, 88)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(quantity)
                                    .addComponent(total)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 931, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 11, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(vendorImage, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(foodSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(vendorName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vendorDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(vendorRating, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(vendorStarImg, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(4, 4, 4)
                                        .addComponent(vendorReview))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9))
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(order)))
                        .addContainerGap())))
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(profilePicGUI, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameGUI)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void vendorReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendorReviewActionPerformed
        JDialog dialog = new JDialog();
        dialog.setTitle("User's Reviews");

        // Create a container for holding multiple reviews
        JPanel reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));  // Vertically align reviews
        HashMap<String, String> customerProfiles = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.customerFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length > 6) {  // Ensure there are enough columns
                    customerProfiles.put(parts[1], parts[6]);  // Store Name -> Image
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(this.feedbacksFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length > 5 && parts[2].equals(this.selectedVendor)) {
                    String customerName = parts[3];  // Extract customer name
                    String reviewText = parts[5];
                    
                    String profileImage = customerProfiles.getOrDefault(customerName, "default.png");
                    String imagePath = "C:\\FoodHub\\src\\foodhub\\images\\" + profileImage;
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    
                    // Create a new ReviewCard for each feedback entry
                    ReviewCard card = new ReviewCard();
                    card.reviewUsername.setText(parts[3]);
                    card.reviewDescription.setText(parts[5]);
                    card.reviewUserProfile.setIcon(new ImageIcon(img));
                    reviewsPanel.add(card);  // Add the card to the reviewsPanel
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add the scrollable reviews panel to the dialog
        dialog.add(new JScrollPane(reviewsPanel));

        // Set dialog properties
        dialog.setSize(1150, 300);
        dialog.setLocationRelativeTo(null);  // Center the dialog
        dialog.setVisible(true);  // Show the dialog
    }//GEN-LAST:event_vendorReviewActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        CustomerMenu cm = new CustomerMenu(this.username);
        cm.showCartItems();
        cm.setVisible(true);
        dispose();

    }//GEN-LAST:event_backActionPerformed

    private void orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderActionPerformed
        new Customer_Order(this.username).setVisible(true);
        dispose();
    }//GEN-LAST:event_orderActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated

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
            java.util.logging.Logger.getLogger(CustomerMenu_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerMenu_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerMenu_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerMenu_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerMenu_2("", 0, "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JPanel cartPanel;
    private javax.swing.JPanel foodPanel;
    private javax.swing.JTextField foodSearch;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton order;
    private javax.swing.JLabel profilePicGUI;
    private javax.swing.JLabel quantity;
    private javax.swing.JLabel total;
    private javax.swing.JLabel usernameGUI;
    private javax.swing.JLabel vendorDescription;
    private javax.swing.JLabel vendorImage;
    private javax.swing.JLabel vendorName;
    private javax.swing.JLabel vendorRating;
    private javax.swing.JButton vendorReview;
    private javax.swing.JLabel vendorStarImg;
    // End of variables declaration//GEN-END:variables
}
