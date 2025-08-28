/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package foodhub;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Chan Jia Zhil
 */
public class Customer_Order extends javax.swing.JFrame {

    /**
     * Creates new form Customer_Order
     */
    private String username;
    private double deliveryFee;
    private String cartFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Cart.txt";
    private String orderHistoryFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt";
    private String paymentFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Payment.txt";
    private String customerFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Customer.txt";
    private ArrayList<String> CartIDs = new ArrayList<>();
    private javax.swing.ButtonGroup buttonGroup;
    private double totalPrice = 0.00;
    private String lastOrderHistoryID;
    private String selectedOrderMethod;
    private String lastPaymentID;
    private String customerStreet;
    private String customerPostCode;
    private String customerState;
    private String customerCredit;
    private String customerNewCredit;

    public Customer_Order(String username) {
        this.username = username;
        initComponents();
        buttonGroup = new javax.swing.ButtonGroup();
        buttonGroup.add(jRadioButton1);
        buttonGroup.add(jRadioButton2);
        buttonGroup.add(jRadioButton3);
        jLabel22.setVisible(false);
        getCustomerAddress();
        showOrderSummary();
        getUniquePaymentID();
        getOrderHistoryUniqueID();
    }

    private void showOrderSummary() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.cartFileName))) {
            String line;
            int count = 1;
            jPanel1.removeAll();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts[1].equals(this.username) && parts[9].equals("No_Place_Order")) {
                    CartIDs.add(parts[0]);
                    double price = Double.valueOf(parts[6]);
                    totalPrice += price;
                    OrderSummaryCard OSC = new OrderSummaryCard();
                    OSC.jLabel1.setText(String.valueOf(count));
                    OSC.jLabel2.setText(parts[3]);
                    OSC.jLabel3.setText("x" + parts[4]);
                    OSC.jLabel4.setText(parts[5]);
                    OSC.jLabel5.setText(parts[6]);
                    String foodImg = parts[8].trim();
                    String imagePath = "C:\\FoodHub\\src\\foodhub\\images\\" + foodImg; // Image file path
                    try {
                        ImageIcon icon = new ImageIcon(imagePath);
                        // Scale the image to fit the label
                        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust size as needed
                        OSC.jLabel6.setIcon(new ImageIcon(img));
                    } catch (Exception e) {
                        System.out.println("Image not found for vendor: " + parts[7]);
                        e.printStackTrace();
                    }
                    jPanel1.add(OSC);
                    ++count;
                }
            }
            String totalPrice2 = String.format("%.2f", totalPrice);
            jLabel10.setText(String.valueOf(totalPrice2));
            double newCredit = Double.valueOf(this.customerCredit) - this.totalPrice;
            String newCredit2 = String.format("%.2f", newCredit);
            jLabel24.setText(newCredit2);
            this.customerNewCredit = String.valueOf(newCredit2);
            jLabel14.setText("RM 0.00");
            br.close();
            int rows = (int) Math.ceil(CartIDs.size() / 1.0);
            jPanel1.setLayout(new java.awt.GridLayout(rows, 1, 10, 10));
            jPanel1.revalidate();
            jPanel1.repaint();
        } catch (IOException e) {
            e.printStackTrace();
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

    private void updateOrderedItems() {
        File file = new File(this.cartFileName);
        File file2 = new File("temp.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file2));

            String line;
            // Read each line of the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // Split by "|"
                // Ensure the line has exactly 10 parts and matches the CartID
                if (parts.length == 10 && CartIDs.contains(parts[0].trim())) {
                    parts[9] = "Placed_Order"; // Replace the 9th index (ensure leading space)
                }
                // Trim each part to remove leading/trailing spaces, then join back
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                String updatedLine = String.join(" | ", parts);
                writer.write(updatedLine);
                writer.newLine();
            }

            // Close the streams
            reader.close();
            writer.close();

            // Replace original file with updated file
            if (file.delete()) {
                file2.renameTo(file);
                System.out.println("File updated successfully.");
            } else {
                System.out.println("Error updating the file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUniquePaymentID() {
        File file = new File(this.paymentFileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            if (reader.readLine() == null) {
                this.lastPaymentID = "1";
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
                this.lastPaymentID = lastID;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCustomerAddress() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.customerFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts[1].equals(this.username)) {
                    this.customerStreet = parts[3];
                    this.customerPostCode = parts[4];
                    this.customerState = parts[5];
                    this.customerCredit = parts[2];
                    break;
                }
            }
            jTextArea1.setText(this.customerStreet);
            jTextField1.setText(this.customerPostCode);
            jTextField2.setText(this.customerState);
            jLabel12.setText(this.customerCredit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCustomerCredit(double currentCredit, double totalPrice) {
        double deliveryFee = Double.valueOf(this.deliveryFee);
        double newCredit = currentCredit - totalPrice - deliveryFee;
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        back = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        placeOrder = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 580));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        back.setBackground(new java.awt.Color(247, 127, 102));
        back.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        back.setText("Back");
        back.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 808, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("jLabel1");
        jLabel1.setOpaque(true);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setText("No.");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setText("Food Name");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setText("Quantity");

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setText("Price");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setText("Total Price");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel7.setText("Total Price");

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("jLabel8");
        jLabel8.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(0, 51, 51));
        jLabel9.setText("jLabel8");
        jLabel9.setOpaque(true);

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel10.setText("RM 0.00");

        placeOrder.setBackground(new java.awt.Color(247, 127, 102));
        placeOrder.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        placeOrder.setText("Place Order");
        placeOrder.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        placeOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeOrderActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(51, 204, 0));
        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel11.setText("💲");
        jLabel11.setToolTipText("");
        jLabel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        jLabel11.setOpaque(true);

        jLabel12.setBackground(new java.awt.Color(204, 255, 204));
        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("0.00");
        jLabel12.setToolTipText("");
        jLabel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(247, 127, 102), null, null));
        jLabel12.setOpaque(true);

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel13.setText("Delivery Fee (included)");

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel14.setText("RM 0.00");

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("jLabel15");
        jLabel15.setOpaque(true);

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel16.setText("Street Address:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setEnabled(false);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel17.setText("Postcode:");

        jTextField1.setEnabled(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setEnabled(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel18.setText("State:");

        jRadioButton1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jRadioButton1.setText("Dine In");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jRadioButton2.setText("Take Away");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jRadioButton3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jRadioButton3.setText("Delivery");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 0, 51));
        jLabel22.setText("Message");

        jLabel23.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel23.setText("Total Credit After Purchase");

        jLabel24.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel24.setText("RM 0.00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(back)
                        .addGap(296, 296, 296)
                        .addComponent(placeOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(34, 34, 34))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel24)
                                            .addComponent(jLabel14))
                                        .addGap(31, 31, 31))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(31, 31, 31)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel4)
                                                .addGap(178, 178, 178)
                                                .addComponent(jLabel5)
                                                .addGap(140, 140, 140)
                                                .addComponent(jLabel6))
                                            .addComponent(jLabel23))
                                        .addGap(18, 18, 18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(15, 15, 15)))
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel18)
                            .addComponent(jLabel22)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRadioButton2)
                        .addGap(44, 44, 44)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButton3)
                        .addGap(92, 92, 92))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4)))
                                .addGap(5, 5, 5)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(placeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        CustomerMenu cm = new CustomerMenu(this.username);
        cm.setVisible(true);
        dispose();
    }//GEN-LAST:event_backActionPerformed

    private void placeOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeOrderActionPerformed
        String OrderMethod = "";

        if (!jRadioButton1.isSelected() && !jRadioButton2.isSelected() && !jRadioButton3.isSelected()) {
            jLabel22.setVisible(true);
            jLabel22.setText("Please Select an Order Method.");
        }

        if (jRadioButton2.isSelected()) {
            this.selectedOrderMethod = "Take_Away";
            jLabel22.setVisible(false);
            jTextArea1.setText("");
            jTextField1.setText("");
            jTextField2.setText("");
        }

        if (jRadioButton1.isSelected()) {
            this.selectedOrderMethod = "Dine_In";
            jLabel22.setVisible(false);
            jTextArea1.setText("");
            jTextField1.setText("");
            jTextField2.setText("");
        }

        if (jRadioButton3.isSelected()) {
            this.selectedOrderMethod = "Delivery";
            jLabel22.setVisible(false);
            jTextArea1.setText(this.customerStreet);
            jTextField1.setText(this.customerPostCode);
            jTextField2.setText(this.customerState);
        }

        if (jRadioButton1.isSelected() || jRadioButton2.isSelected() || jRadioButton3.isSelected()) {
            int result = JOptionPane.showConfirmDialog(
                    null,
                    "Do you want to place the order?",
                    "Place Order",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                try (
                        BufferedReader reader = new BufferedReader(new FileReader(this.cartFileName)); BufferedWriter writer2 = new BufferedWriter(new FileWriter(this.orderHistoryFileName, true))) {
                    String line;
                    Map<String, List<String>> vendorOrders = new LinkedHashMap<>();
                    Map<String, Double> vendorTotals = new LinkedHashMap<>();
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(" \\| ");
                        if (parts[1].equals((this.username)) && parts[9].equals("No_Place_Order")) {
                            String vendor = parts[7];
                            String foodID = parts[2];
                            int quantity = Integer.parseInt(parts[4]);
                            double singleFoodPrice = Double.parseDouble(parts[5]);
                            String formattedOrder = String.format("%s:%d:%.2f", foodID, quantity, singleFoodPrice);
                            vendorOrders.computeIfAbsent(vendor, k -> new ArrayList<>()).add(formattedOrder);
                            vendorTotals.put(vendor, vendorTotals.getOrDefault(vendor, 0.0) + (singleFoodPrice * quantity));
                        }
                    }
                    double totalAmount2 = 0;
                    for (String vendor : vendorOrders.keySet()) {
                        List<String> orders = vendorOrders.get(vendor);
                        double totalAmount = vendorTotals.get(vendor);
                        totalAmount2 += totalAmount;
                    }

                    if (Double.valueOf(this.customerCredit) >= totalAmount2 + Double.valueOf(this.deliveryFee)) {
                    for (String vendor : vendorOrders.keySet()) {
                        List<String> orders = vendorOrders.get(vendor);
                        double totalAmount = vendorTotals.get(vendor);

                        String currentDateTime = new SimpleDateFormat("dd-MM-yyyy hh:mm:ssa").format(new Date());
                        // Write to order history
                        String orderHistoryEntry = String.format("%s | %s | %s | %s | [%s] | %s | %.1f | %.2f | %s | %s | %s",
                                this.lastOrderHistoryID, this.username, vendor, this.selectedOrderMethod, String.join(", ", orders),
                                currentDateTime, this.deliveryFee, totalAmount, "Pending", "True", "none");
                        writer2.write(orderHistoryEntry);
                        writer2.newLine();
                        int intLastID = Integer.parseInt(this.lastOrderHistoryID.substring(2)); // Get "002" -> 2
                        intLastID++; // Increment the ID
                        this.lastOrderHistoryID = String.format("OR%03d", intLastID); // Format as "OR003", "OR004", etc.
                    }
                    reader.close();
                    writer2.close();
                    updateOrderedItems();
                    updateCustomerCredit(Double.valueOf(this.customerCredit), Double.valueOf(totalAmount2));
                    JOptionPane.showMessageDialog(null, "Ordered successfully, please check your order status."
                            + "\n\n - FoodHub",
                            "Info", JOptionPane.INFORMATION_MESSAGE);
                    CustomerMenu cm = new CustomerMenu(this.username);
                    cm.setVisible(true);
                    dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient credit, please topup."
                            + "\n\n - FoodHub",
                            "Info", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }//GEN-LAST:event_placeOrderActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        if (jRadioButton3.isSelected()) {
            this.deliveryFee = 5.00;
            jLabel14.setText("RM " + this.deliveryFee);
            jLabel10.setText(String.valueOf(totalPrice + this.deliveryFee));
            double newCredit = Double.valueOf(this.customerNewCredit) - Double.valueOf(this.deliveryFee);
            String newCredit2 = String.format("%.2f", newCredit);
            jLabel24.setText(newCredit2);
        }
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        if (jRadioButton1.isSelected()) {
            this.deliveryFee = 0.00;
            jLabel14.setText("RM " + this.deliveryFee);
            jLabel10.setText(String.valueOf(totalPrice));
            jLabel24.setText(this.customerNewCredit);
        }
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        if (jRadioButton2.isSelected()) {
            this.deliveryFee = 0.00;
            jLabel14.setText("RM " + this.deliveryFee);
            jLabel10.setText(String.valueOf(totalPrice));
            jLabel24.setText(this.customerNewCredit);
        }
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
            java.util.logging.Logger.getLogger(Customer_Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Customer_Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Customer_Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Customer_Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Customer_Order("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JButton placeOrder;
    // End of variables declaration//GEN-END:variables
}
