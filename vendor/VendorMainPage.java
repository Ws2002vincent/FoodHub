package foodhub.vendor;

import java.awt.Color;
import java.awt.Component;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;

public class VendorMainPage extends javax.swing.JPanel {

    private String vendorID;
    private List<Order> orderList;
    private List<OrderBox> ordersList = new ArrayList<>();
    private OrderBox selectedOrder = null;

    public VendorMainPage(String vendorID) {
        initComponents();
        this.vendorID = vendorID;
        loadVendorStatus();

        orderList = OrderLoader.loadOrders("C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt", vendorID);

        newOrderPnl.setLayout(new BoxLayout(newOrderPnl, BoxLayout.Y_AXIS));
        preparingOrderPnl.setLayout(new BoxLayout(preparingOrderPnl, BoxLayout.Y_AXIS));
        readyOrderPnl.setLayout(new BoxLayout(readyOrderPnl, BoxLayout.Y_AXIS));

        // Get current date
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.now();
        String currentDate = date.format(format);

        try {
            BufferedReader bf = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Vendor.txt"));
            String line;
            String foodStall = "";

            while ((line = bf.readLine()) != null) {
                String[] dataline = line.split("\\|");

                if (dataline[0].trim().equals(vendorID)) {
                    foodStall = dataline[2].trim();
                    break;
                }
            }
            stallName.setText(foodStall);

        } catch (IOException ex) {
            Logger.getLogger(VendorMainPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        idLbl.setText(vendorID);
        dateLbl.setText(currentDate);

        // Load orders into new orders panel
        for (Order order : orderList) {
            LocalDate orderDate = LocalDate.parse(order.getDateTime().substring(0, 10), format);
            if (orderDate.format(format).equals(currentDate)) {
                OrderBox orderBox = new OrderBox(
                        order.getOrderId(),
                        order.getCustomer(),
                        order.getVendorID(),
                        order.getServiceType(),
                        order.getDateTime(),
                        order.getOrderedItems()
                );
                orderBox.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        if (selectedOrder != null) {
                            selectedOrder.setBorder(BorderFactory.createEmptyBorder());
                        }
                        selectedOrder = orderBox;
                        selectedOrder.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    }
                });

                // Display each status in respective panel - New, Preparing, Ready
                String status = order.getOrderStatus().trim();

                switch (status) {
                    case "Pending" -> {
                        ordersList.add(orderBox);
                        newOrderPnl.add(orderBox);
                        newOrderPnl.add(Box.createVerticalStrut(15));
                    }
                    case "Preparing" -> {
                        preparingOrderPnl.add(orderBox);
                        preparingOrderPnl.add(Box.createVerticalStrut(15));
                    }
                    case "Ready" -> {
                        readyOrderPnl.add(orderBox);
                        readyOrderPnl.add(Box.createVerticalStrut(15));
                    }
                }

                newOrderPnl.revalidate();
                newOrderPnl.repaint();
                preparingOrderPnl.revalidate();
                preparingOrderPnl.repaint();
                readyOrderPnl.revalidate();
                readyOrderPnl.repaint();
            }
        }
    }

    // Display vendor operating status
    private void loadVendorStatus() {
        String filePath = "C:\\FoodHub\\src\\foodhub\\Database\\Vendor.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] vendorData = line.split("\\|");

                // Trim each field to ensure no extra spaces
                for (int i = 0; i < vendorData.length; i++) {
                    vendorData[i] = vendorData[i].trim();
                }

                // Check if the vendor ID matches
                if (vendorData[0].equals(vendorID)) {
                    // Read the current status (true for Open, false for Closed)
                    boolean isOpen = vendorData[4].equalsIgnoreCase("true");

                    // Set the toggle button's state
                    jToggleButton1.setSelected(isOpen);
                    updateVendorStatus(isOpen); // Call the update function to update the button and file
                    break; // Exit the loop once the vendor is found
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading vendor status: " + ex.getMessage());
        }
    }

    private void updateVendorStatus(boolean isOpen) {
        // Update the button's text and background color
        if (isOpen) {
            jToggleButton1.setText("Open Now");
            jToggleButton1.setBackground(new Color(175, 226, 166)); // Darker green
        } else {
            jToggleButton1.setText("Closed");
            jToggleButton1.setBackground(new Color(226, 168, 168)); // Darker red
        }

        // Update the vendor status in the file
        String filePath = "C:\\FoodHub\\src\\foodhub\\Database\\Vendor.txt";
        StringBuilder newContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] vendorData = line.split("\\|");
                for (int i = 0; i < vendorData.length; i++) {
                    vendorData[i] = vendorData[i].trim();
                }

                if (vendorData[0].equals(vendorID)) {
                    // Update the vendor's status
                    vendorData[4] = isOpen ? " true" : " false";  // "true" for open, "false" for closed
                    line = String.join(" | ", vendorData).trim();
                }
                newContent.append(line).append("\n");
            }

            // Write the updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(newContent.toString());
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error updating vendor status: " + ex.getMessage());
        }
    }

    private void updateOrderStatusInFile(String orderId, String newStatus, String newDecision) {
        List<String> fileContent = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                // Update order status and decision
                if (data[0].trim().equals(orderId)) {
                    data[8] = newStatus.trim();
                    data[9] = newDecision.trim();
                }

                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim();
                }

                line = String.join(" | ", data);
                fileContent.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Update content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt"))) {
            for (String fileLine : fileContent) {
                writer.write(fileLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    // Moves an OrderBox from one panel to another, including its spacing
    private void moveOrderBox(JPanel sourcePanel, JPanel targetPanel, OrderBox orderBox) {
        if (orderBox == null) {
            return;
        }
        // Remove from source panel
        removeOrderFromPanel(sourcePanel, orderBox);

        // Add to target panel
        targetPanel.add(orderBox);
        targetPanel.add(Box.createVerticalStrut(15));

        sourcePanel.revalidate();
        sourcePanel.repaint();
        targetPanel.revalidate();
        targetPanel.repaint();
    }

    private void removeOrderFromPanel(JPanel panel, OrderBox orderBox) {
        int index = panel.getComponentZOrder(orderBox);
        if (index != -1) {
            panel.remove(orderBox);

            // Remove the associated vertical strut if it exists
            if (index < panel.getComponentCount()) {
                Component nextComponent = panel.getComponent(index);
                if (nextComponent instanceof Box.Filler) {
                    panel.remove(nextComponent);
                }
            }

            panel.revalidate();
            panel.repaint();
        }
    }

    // Remove the order box from New panel
    public void declineOrder(OrderBox orderBox) {
        orderBox.setBorder(null);
        removeOrderFromPanel(newOrderPnl, orderBox);
        updateOrderStatusInFile(orderBox.getOrderID(), "Cancelled", "False");
        addNotification(orderBox.getCustomerID(), orderBox.getOrderID(), "Cancelled");

        selectedOrder = null; // Reset selection
    }

    // Move the order box to the Preparing panel
    public void acceptOrder(OrderBox orderBox) {
        orderBox.setBorder(null);
        moveOrderBox(newOrderPnl, preparingOrderPnl, orderBox);
        updateOrderStatusInFile(orderBox.getOrderID(), "Preparing", "True");
        addNotification(orderBox.getCustomerID(), orderBox.getOrderID(), "Preparing");

        selectedOrder = null;
    }

    // Move the order box to the Ready panel
    public void markOrderReady(OrderBox orderBox) {
        orderBox.setBorder(null);
        moveOrderBox(preparingOrderPnl, readyOrderPnl, orderBox);
        updateOrderStatusInFile(orderBox.getOrderID(), "Ready", "True");
        addNotification(orderBox.getCustomerID(), orderBox.getOrderID(), "Ready");

        selectedOrder = null;
    }

    // Remove the order box from Ready panel
    public void markOrderComplete(OrderBox orderBox) {
        orderBox.setBorder(null);
        removeOrderFromPanel(readyOrderPnl, orderBox);
        updateOrderStatusInFile(orderBox.getOrderID(), "Completed", "True");
        addNotification(orderBox.getCustomerID(), orderBox.getOrderID(), "Completed");

        selectedOrder = null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPnl = new javax.swing.JPanel();
        dateLbl = new javax.swing.JLabel();
        stallLbl = new javax.swing.JLabel();
        idLbl = new javax.swing.JLabel();
        stallName = new javax.swing.JLabel();
        orderLbl = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        operatingStatusLbl = new javax.swing.JLabel();
        buttonPnl = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        declineBtn = new javax.swing.JButton();
        acceptBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        readyBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        completedBtn = new javax.swing.JButton();
        statusPnl = new javax.swing.JPanel();
        newLbl = new javax.swing.JLabel();
        preparingLbl = new javax.swing.JLabel();
        readyLbl = new javax.swing.JLabel();
        orderPnl = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        newOrderPnl = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        preparingOrderPnl = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        readyOrderPnl = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        headerPnl.setBackground(new java.awt.Color(255, 255, 255));

        dateLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        dateLbl.setText("dd-MM-yyyy");

        stallLbl.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        stallLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        stallLbl.setText("Stall ID:");

        idLbl.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        idLbl.setText("0");

        stallName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        stallName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        stallName.setText("Stall");

        orderLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        orderLbl.setText("Orders for");

        jToggleButton1.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jToggleButton1.setText("N/A");
        jToggleButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        operatingStatusLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        operatingStatusLbl.setText("Operating Status:");

        javax.swing.GroupLayout headerPnlLayout = new javax.swing.GroupLayout(headerPnl);
        headerPnl.setLayout(headerPnlLayout);
        headerPnlLayout.setHorizontalGroup(
            headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPnlLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stallName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(headerPnlLayout.createSequentialGroup()
                        .addComponent(orderLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateLbl))
                    .addGroup(headerPnlLayout.createSequentialGroup()
                        .addComponent(stallLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(operatingStatusLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        headerPnlLayout.setVerticalGroup(
            headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPnlLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerPnlLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(operatingStatusLbl))
                        .addGap(63, 63, 63)
                        .addGroup(headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(orderLbl)
                            .addComponent(dateLbl))
                        .addContainerGap())
                    .addGroup(headerPnlLayout.createSequentialGroup()
                        .addComponent(stallName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stallLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(idLbl))
                        .addGap(40, 40, 40))))
        );

        buttonPnl.setLayout(new java.awt.GridLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout());

        declineBtn.setBackground(new java.awt.Color(246, 214, 214));
        declineBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        declineBtn.setForeground(new java.awt.Color(0, 0, 0));
        declineBtn.setText("Decline");
        declineBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        declineBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                declineBtnActionPerformed(evt);
            }
        });
        jPanel1.add(declineBtn);

        acceptBtn.setBackground(new java.awt.Color(223, 246, 221));
        acceptBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        acceptBtn.setForeground(new java.awt.Color(0, 0, 0));
        acceptBtn.setText("Accept");
        acceptBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        acceptBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptBtnActionPerformed(evt);
            }
        });
        jPanel1.add(acceptBtn);

        buttonPnl.add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.CardLayout());

        readyBtn.setBackground(new java.awt.Color(187, 222, 251));
        readyBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        readyBtn.setForeground(new java.awt.Color(0, 0, 0));
        readyBtn.setText("Mark Ready");
        readyBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        readyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readyBtnActionPerformed(evt);
            }
        });
        jPanel2.add(readyBtn, "card2");

        buttonPnl.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.CardLayout());

        completedBtn.setBackground(new java.awt.Color(255, 204, 128));
        completedBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        completedBtn.setForeground(new java.awt.Color(0, 0, 0));
        completedBtn.setText("Mark Completed");
        completedBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        completedBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completedBtnActionPerformed(evt);
            }
        });
        jPanel3.add(completedBtn, "card2");

        buttonPnl.add(jPanel3);

        statusPnl.setBackground(new java.awt.Color(255, 255, 255));
        statusPnl.setLayout(new java.awt.GridLayout());

        newLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        newLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newLbl.setText("New");
        statusPnl.add(newLbl);

        preparingLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        preparingLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        preparingLbl.setText("Preparing");
        statusPnl.add(preparingLbl);

        readyLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        readyLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        readyLbl.setText("Ready");
        statusPnl.add(readyLbl);

        orderPnl.setBackground(new java.awt.Color(255, 255, 255));
        orderPnl.setLayout(new java.awt.GridLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        newOrderPnl.setBackground(new java.awt.Color(255, 255, 255));
        newOrderPnl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout newOrderPnlLayout = new javax.swing.GroupLayout(newOrderPnl);
        newOrderPnl.setLayout(newOrderPnlLayout);
        newOrderPnlLayout.setHorizontalGroup(
            newOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 349, Short.MAX_VALUE)
        );
        newOrderPnlLayout.setVerticalGroup(
            newOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(newOrderPnl);

        orderPnl.add(jScrollPane1);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        preparingOrderPnl.setBackground(new java.awt.Color(255, 255, 255));
        preparingOrderPnl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout preparingOrderPnlLayout = new javax.swing.GroupLayout(preparingOrderPnl);
        preparingOrderPnl.setLayout(preparingOrderPnlLayout);
        preparingOrderPnlLayout.setHorizontalGroup(
            preparingOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 363, Short.MAX_VALUE)
        );
        preparingOrderPnlLayout.setVerticalGroup(
            preparingOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(preparingOrderPnl);

        orderPnl.add(jScrollPane2);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        readyOrderPnl.setBackground(new java.awt.Color(255, 255, 255));
        readyOrderPnl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout readyOrderPnlLayout = new javax.swing.GroupLayout(readyOrderPnl);
        readyOrderPnl.setLayout(readyOrderPnlLayout);
        readyOrderPnlLayout.setHorizontalGroup(
            readyOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 369, Short.MAX_VALUE)
        );
        readyOrderPnlLayout.setVerticalGroup(
            readyOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(readyOrderPnl);

        orderPnl.add(jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(orderPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
            .addComponent(buttonPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(statusPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orderPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        boolean isOpen = jToggleButton1.isSelected();
        updateVendorStatus(isOpen);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void declineBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_declineBtnActionPerformed
        if (selectedOrder != null) {
            if (newOrderPnl.isAncestorOf(selectedOrder)) {
                declineOrder(selectedOrder);
                JOptionPane.showMessageDialog(this, "Order Declined!");
            } else {
                JOptionPane.showMessageDialog(this, "Selected order is not in the New Orders section.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to decline.");
        }
    }//GEN-LAST:event_declineBtnActionPerformed

    private void acceptBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptBtnActionPerformed
        // Check if an order has been selected
        if (selectedOrder != null) {
            // Check if the selected order is in the newOrderPnl
            if (newOrderPnl.isAncestorOf(selectedOrder)) {
                acceptOrder(selectedOrder);
                JOptionPane.showMessageDialog(this, "Order Accepted!");
            } else {
                JOptionPane.showMessageDialog(this, "Selected order is not in the New Orders section.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to accept.");
        }
    }//GEN-LAST:event_acceptBtnActionPerformed

    private void readyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readyBtnActionPerformed
        if (selectedOrder != null) {
            if (preparingOrderPnl.isAncestorOf(selectedOrder)) {
                markOrderReady(selectedOrder);
                JOptionPane.showMessageDialog(this, "Order marked as Ready!");
            } else {
                JOptionPane.showMessageDialog(this, "Selected order is not in the Preparing section.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to mark as Ready.");
        }
    }//GEN-LAST:event_readyBtnActionPerformed

    private void completedBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedBtnActionPerformed
        if (selectedOrder != null) {
            if (readyOrderPnl.isAncestorOf(selectedOrder)) {
                markOrderComplete(selectedOrder);
                JOptionPane.showMessageDialog(this, "Order marked as Completed!");
            } else {
                JOptionPane.showMessageDialog(this, "Selected order is not in the Ready Orders section.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to mark as Completed.");
        }
    }//GEN-LAST:event_completedBtnActionPerformed

    private String generateNextNotificationID(String filePath) {
        String lastID = "N0";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                lastID = data[0].trim(); // Get the first column (ID)
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Extract the numeric part of the ID and increment it
        int nextIDNumber = Integer.parseInt(lastID.substring(1)) + 1;
        return "N" + nextIDNumber;
    }

    private void addNotification(String userID, String orderID, String status) {
        String notificationFile = "C:\\FoodHub\\src\\foodhub\\Database\\Notification.txt";
        String notificationId = generateNextNotificationID(notificationFile);
        String vendorId = this.vendorID;
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mma"));

        // Display message based on status
        String customMessage = switch (status) {
            case "Cancelled" ->
                String.format("We're sorry! Your order %s has been cancelled. Please contact our customer service for more information.", orderID);
            case "Preparing" ->
                String.format("Your order %s is being prepared. Thank you for your patience!", orderID);
            case "Ready" ->
                String.format("Good news! Your order %s is ready for pickup/delivery.", orderID);
            case "Completed" ->
                String.format("Thank you! Your order %s has been successfully completed.", orderID);
            default ->
                String.format("The status of your order %s has changed to %s.", orderID, status);
        };

        String notification = String.format("%s | %s | %s | Order Status | %s | %s | Unread",
                notificationId, vendorId, userID, customMessage, currentDateTime);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(notificationFile, true))) {
            writer.write(notification);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing notification: " + e.getMessage());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptBtn;
    private javax.swing.JPanel buttonPnl;
    private javax.swing.JButton completedBtn;
    private javax.swing.JLabel dateLbl;
    private javax.swing.JButton declineBtn;
    private javax.swing.JPanel headerPnl;
    private javax.swing.JLabel idLbl;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel newLbl;
    private javax.swing.JPanel newOrderPnl;
    private javax.swing.JLabel operatingStatusLbl;
    private javax.swing.JLabel orderLbl;
    private javax.swing.JPanel orderPnl;
    private javax.swing.JLabel preparingLbl;
    private javax.swing.JPanel preparingOrderPnl;
    private javax.swing.JButton readyBtn;
    private javax.swing.JLabel readyLbl;
    private javax.swing.JPanel readyOrderPnl;
    private javax.swing.JLabel stallLbl;
    private javax.swing.JLabel stallName;
    private javax.swing.JPanel statusPnl;
    // End of variables declaration//GEN-END:variables
}
