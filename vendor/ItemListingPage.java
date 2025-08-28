package foodhub.vendor;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class ItemListingPage extends javax.swing.JPanel {

    private String vendorID;

    public ItemListingPage(String vendorID) {
        this.vendorID = vendorID;
        initComponents();

        loadItemsFromFile("C:\\FoodHub\\src\\foodhub\\Database\\Menuinfo.txt");
    }

    private void loadItemsFromFile(String filename) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] details = line.split("\\|");

                String foodID = details[0].trim();
                String name = details[1].trim();
                String description = details[2].trim();
                double price = Double.parseDouble(details[3].trim());
                String imagePath = details[5].trim();
                String vendorIDInFile = details[6].trim();

                if (vendorIDInFile.equals(vendorID)) {
                    menuItems.add(new MenuItem(foodID, name, description, price, imagePath));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

        pnlItems.setLayout(new GridLayout(0, 3, 10, 10));

        for (MenuItem item : menuItems) {
            MenuInfoBox infoBox = new MenuInfoBox(item.getFoodID(), item.getName(), item.getDescription(), item.getPrice(), item.getImagePath());
            pnlItems.add(infoBox);
        }

        pnlItems.revalidate();
        pnlItems.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        addItemBtn = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        pnlItems = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Item Listing");

        addItemBtn.setBackground(new java.awt.Color(255, 204, 186));
        addItemBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        addItemBtn.setForeground(new java.awt.Color(0, 0, 0));
        addItemBtn.setText("+ Add New Item");
        addItemBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addItemBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemBtnActionPerformed(evt);
            }
        });

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(new java.awt.Dimension(1, 1));
        scrollPane.setMinimumSize(new java.awt.Dimension(10, 6));
        scrollPane.setViewportView(pnlItems);

        pnlItems.setBackground(new java.awt.Color(255, 255, 255));
        pnlItems.setAutoscrolls(true);
        pnlItems.setLayout(new java.awt.GridLayout(1, 0));
        scrollPane.setViewportView(pnlItems);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 275, Short.MAX_VALUE)
                        .addComponent(addItemBtn)
                        .addGap(96, 96, 96))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(addItemBtn))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void refreshItemList() {
        // Clear the current items
        pnlItems.removeAll();

        loadItemsFromFile("C:\\FoodHub\\src\\foodhub\\Database\\Menuinfo.txt");

        pnlItems.revalidate();
        pnlItems.repaint();
    }


    private void addItemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemBtnActionPerformed
        JDialog addItemDialog = new JDialog((Frame) null, "Add New Item", true);

        AddItem item = new AddItem(addItemDialog, this, vendorID);

        // Display AddItem panel
        addItemDialog.setResizable(false);
        addItemDialog.setSize(640, 550);
        addItemDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addItemDialog.add(item);
        addItemDialog.setLocationRelativeTo(null);
        addItemDialog.setVisible(true);

        refreshItemList();

    }//GEN-LAST:event_addItemBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addItemBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnlItems;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
