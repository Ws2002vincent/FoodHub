package foodhub.vendor;

import java.awt.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;

public class MenuInfoBox extends javax.swing.JPanel {

    private String foodID;

    public MenuInfoBox(String foodID, String itemName, String description, double price, String imagePath) {
        initComponents();
        this.foodID = foodID;
        lblName.setText("<html><p style='margin-left: 7px;'>" + itemName + "</p></html>");
        lblDescription.setText("<html><p style='width:300px; margin-left: 7px;'>" + description + "</p></html>");
        lblPrice.setText(String.format("<html><p style='margin-left: 7px;'>RM %.2f</p></html>", price));

        // Resize food image
        ImageIcon foodImage = new ImageIcon("src/foodhub/images/" + imagePath);
        Image resizedFoodImage = foodImage.getImage().getScaledInstance(415, 190, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(resizedFoodImage);

        lblImage.setIcon(resizedImageIcon);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImage = new javax.swing.JLabel();
        deleteBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        lblPrice = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 245, 245));
        setMaximumSize(new java.awt.Dimension(323, 382));

        lblImage.setPreferredSize(new java.awt.Dimension(290, 147));

        deleteBtn.setBackground(new java.awt.Color(246, 214, 214));
        deleteBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteBtn.setForeground(new java.awt.Color(0, 0, 0));
        deleteBtn.setText("Delete");
        deleteBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteBtn.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteBtnMouseExited(evt);
            }
        });
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        editBtn.setBackground(new java.awt.Color(223, 246, 221));
        editBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editBtn.setForeground(new java.awt.Color(0, 0, 0));
        editBtn.setText("Edit");
        editBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editBtn.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        editBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                editBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                editBtnMouseExited(evt);
            }
        });
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        lblPrice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPrice.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPrice.setText("Price");
        lblPrice.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblPrice.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblDescription.setBackground(new java.awt.Color(117, 117, 117));
        lblDescription.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        lblDescription.setText("Food description");
        lblDescription.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblDescription.setPreferredSize(new java.awt.Dimension(118, 90));

        lblName.setBackground(new java.awt.Color(66, 66, 66));
        lblName.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblName.setText("Food Name");
        lblName.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        lblName.setPreferredSize(new java.awt.Dimension(290, 27));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPrice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteBtn))
                    .addComponent(lblDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPrice, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editBtn)
                        .addComponent(deleteBtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        String itemName = lblName.getText().replaceAll("<html><p.*?>|</p></html>", "").trim();

        // Set JOptionPane font
        Font customFont = new Font("Segoe UI", Font.PLAIN, 16);
        UIManager.put("OptionPane.messageFont", customFont);
        UIManager.put("OptionPane.buttonFont", customFont);

        // Get the parent JFrame of the current JPanel
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        int confirmDelete = JOptionPane.showConfirmDialog(
                parentFrame,
                "<html>Are you sure you want to delete <b>" + itemName + "</b>?</html>",
                "Delete Item Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmDelete == JOptionPane.YES_OPTION) {
            try {
                deleteMenuItem(foodID);

                // Remove this panel from its parent
                Container parent = this.getParent();
                if (parent != null) {
                    parent.remove(this);
                    parent.revalidate();
                    parent.repaint();
                }

                System.out.println(itemName + " deleted!");
            } catch (IOException ex) {
                Logger.getLogger(MenuInfoBox.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void deleteMenuItem(String foodID) throws IOException {
        StringBuilder tempTxt = new StringBuilder();
        String line;
        boolean itemDeleted = false;

        try (BufferedReader foodRead = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Menuinfo.txt"))) {
            while ((line = foodRead.readLine()) != null) {
                String[] data = line.split("\\|");

                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim();
                }

                // Check if the current line matches the foodID to delete
                if (!data[0].equals(foodID)) {
                    tempTxt.append(String.join(" | ", data)).append(System.lineSeparator());
                } else {
                    itemDeleted = true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MenuInfoBox.class.getName()).log(Level.SEVERE, "Error reading file", ex);
            throw new IOException("Error reading the file", ex);
        }

        // Check if the item was deleted, then write back the file
        if (itemDeleted) {
            try (FileWriter foodWrite = new FileWriter("C:\\FoodHub\\src\\foodhub\\Database\\Menuinfo.txt", false)) {
                foodWrite.write(tempTxt.toString());
            } catch (IOException ex) {
                Logger.getLogger(MenuInfoBox.class.getName()).log(Level.SEVERE, "Error writing file", ex);
                throw new IOException("Error writing to the file", ex);
            }
        } else {
            throw new IOException("Item with ID " + foodID + " not found in the database.");
        }
    }

    private void editBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editBtnMouseEntered
        editBtn.setBackground(new Color(76, 175, 80));
        editBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_editBtnMouseEntered

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed

        String itemName = "";
        String description = "";
        double price = 0.;
        String imagePath = "";

        JDialog editItemDialog = new JDialog((Frame) null, "Edit Item", true);
        ItemListingPage itemListingPage = (ItemListingPage) SwingUtilities.getAncestorOfClass(ItemListingPage.class, this);

        EditItem editItemForm = new EditItem(foodID, itemName, description, price, imagePath, editItemDialog, itemListingPage);
        editItemForm.setVisible(true);

        editItemForm.revalidate();
        editItemForm.repaint();

        // Display EditItem panel
        editItemDialog.setResizable(false);
        editItemDialog.setSize(640, 550);
        editItemDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        editItemDialog.add(editItemForm);
        editItemDialog.setLocationRelativeTo(null);
        editItemDialog.setVisible(true);
    }//GEN-LAST:event_editBtnActionPerformed

    private void editBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editBtnMouseExited
        editBtn.setBackground(new Color(223, 246, 221));
        editBtn.setForeground(Color.BLACK);
    }//GEN-LAST:event_editBtnMouseExited

    private void deleteBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseEntered
        deleteBtn.setBackground(new Color(244, 67, 54));
        deleteBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_deleteBtnMouseEntered

    private void deleteBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseExited
        deleteBtn.setBackground(new Color(246, 214, 214));
        deleteBtn.setForeground(Color.BLACK);
    }//GEN-LAST:event_deleteBtnMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPrice;
    // End of variables declaration//GEN-END:variables
}
