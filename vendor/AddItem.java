package foodhub.vendor;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.logging.*;
import javax.swing.*;

public class AddItem extends javax.swing.JPanel {

    private JDialog parentDialog;
    private String imagePath;
    private ItemListingPage itemListingPage;
    private String vendorID;

    public AddItem(JDialog parentDialog, ItemListingPage itemListingPage, String vendorID) {
        this.parentDialog = parentDialog;
        this.itemListingPage = itemListingPage;
        this.vendorID = vendorID;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameLbl = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        priceLbl = new javax.swing.JLabel();
        priceTxt = new javax.swing.JTextField();
        availabilityLbl = new javax.swing.JLabel();
        desLbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        desTxt = new javax.swing.JTextArea();
        availabilityBox = new javax.swing.JComboBox<>();
        cancelBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        imageLbl = new javax.swing.JLabel();
        uploadLbl = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Create New Item");

        nameLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nameLbl.setText("Name");
        nameLbl.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        nameTxt.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        priceLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        priceLbl.setText("Price (RM)");
        priceLbl.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        priceTxt.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        availabilityLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        availabilityLbl.setText("Availability");
        availabilityLbl.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        desLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        desLbl.setText("Description");

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        desTxt.setColumns(20);
        desTxt.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        desTxt.setRows(5);
        jScrollPane1.setViewportView(desTxt);

        availabilityBox.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        availabilityBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available", "Not Available" }));

        cancelBtn.setBackground(new java.awt.Color(255, 255, 255));
        cancelBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelBtn.setForeground(new java.awt.Color(0, 0, 0));
        cancelBtn.setText("Cancel");
        cancelBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cancelBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        saveBtn.setBackground(new java.awt.Color(0, 0, 0));
        saveBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        saveBtn.setForeground(new java.awt.Color(255, 255, 255));
        saveBtn.setText("Save Changes");
        saveBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        imageLbl.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        imageLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLbl.setText("+");
        imageLbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        imageLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageLblMouseClicked(evt);
            }
        });

        uploadLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        uploadLbl.setText("Upload Image");
        uploadLbl.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saveBtn))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(desLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(priceLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(priceTxt)
                                    .addComponent(availabilityBox, 0, 258, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(nameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(availabilityLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                        .addComponent(nameTxt)))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(imageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                                    .addComponent(uploadLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(14, 14, 14))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLbl)
                    .addComponent(uploadLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(priceLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(availabilityLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(availabilityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(imageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addComponent(desLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void imageLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageLblMouseClicked
        uploadImage();
    }//GEN-LAST:event_imageLblMouseClicked

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        saveChanges();

    }//GEN-LAST:event_saveBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        if (parentDialog != null) {
            parentDialog.dispose(); // Close the dialog
        }

    }//GEN-LAST:event_cancelBtnActionPerformed

    private void saveChanges() {
        boolean validName = true;
        boolean validPrice = true;
        boolean validDescription = true;

        String newName = nameTxt.getText().trim();
        String newDescription = desTxt.getText().trim();
        String availability = (String) availabilityBox.getSelectedItem();
        double newPrice = 0.0;

        if (newName.isEmpty()) {
            validName = false;
            setCustomFontForDialogs();
            JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Invalid Name", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String priceText = priceTxt.getText().trim();
        if (priceText.isEmpty()) {
            validPrice = false;
            setCustomFontForDialogs();
            JOptionPane.showMessageDialog(this, "Price cannot be empty.", "Invalid Price", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            try {
                newPrice = Double.parseDouble(priceText);
                if (newPrice < 0 || Double.doubleToRawLongBits(newPrice) == Double.doubleToRawLongBits(-0.0)) {
                    validPrice = false;
                    setCustomFontForDialogs();
                    JOptionPane.showMessageDialog(this, "Price cannot be negative.", "Invalid Price", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                validPrice = false;
                setCustomFontForDialogs();
                JOptionPane.showMessageDialog(this, "Price must be a valid number.", "Invalid Price", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        if (newDescription.isEmpty()) {
            validDescription = false;
            setCustomFontForDialogs();
            JOptionPane.showMessageDialog(this, "Description cannot be empty.", "Invalid Description", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (imagePath == null || imagePath.isEmpty()) {
            setCustomFontForDialogs();
            JOptionPane.showMessageDialog(null, "Please upload an image for the item.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (BufferedReader foodRead = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Menuinfo.txt"))) {
            String line;
            int lastID = 0;

            while ((line = foodRead.readLine()) != null) {
                String[] lines = line.split("\\|");
                if (lines[1].trim().equalsIgnoreCase(newName)) { // Check for duplicate name
                    validName = false;
                    break;
                }

                // Extract the ID and update the lastID
                String idPart = lines[0].trim().substring(1); // Remove the "F" prefix
                try {
                    int currentID = Integer.parseInt(idPart);
                    if (currentID > lastID) {
                        lastID = currentID;
                    }
                } catch (NumberFormatException e) {
                    Logger.getLogger(ItemListingPage.class.getName()).log(Level.WARNING, "Invalid ID format in file: " + lines[0], e);
                }
            }

            if (!validName) {
                setCustomFontForDialogs();
                JOptionPane.showMessageDialog(null, "The name already exists. Please choose a different name.", "Invalid Name", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String newID = "F" + (lastID + 1);

            // Save the uploaded image to the images folder
            String imagesFolderPath = "C:\\FoodHub\\src\\foodhub\\images";
            File imagesFolder = new File(imagesFolderPath);
            if (!imagesFolder.exists()) {
                imagesFolder.mkdirs();
            }

            File imageFile = new File(imagePath);
            String imageFileName = imageFile.getName();
            File destinationFile = new File(imagesFolder, imageFileName);

            // Copy the image to the images folder
            try {
                Files.copy(imageFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException copyEx) {
                Logger.getLogger(ItemListingPage.class.getName()).log(Level.SEVERE, "Failed to copy the image file.", copyEx);
                setCustomFontForDialogs();
                JOptionPane.showMessageDialog(null, "Failed to save the image file.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (FileWriter foodWriter = new FileWriter("C:\\FoodHub\\src\\foodhub\\Database\\Menuinfo.txt", true)) {
                String newFoodMenu = newID + " | " + newName + " | " + newDescription + " | " + newPrice + " | "
                        + availability + " | " + imageFileName + " | " + vendorID + System.lineSeparator();
                System.out.println("Image file name is: " + imageFileName);
                foodWriter.write(newFoodMenu);
            }

            setCustomFontForDialogs();
            JOptionPane.showMessageDialog(null, "<html><b>" + newName + " </b>is added successfully</html>", "Action Success", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Saving the new menu item details...");

            if (itemListingPage != null) {
                itemListingPage.refreshItemList();
            }

            // Close the parent dialog if it exists
            if (parentDialog != null) {
                parentDialog.dispose();
            }

        } catch (IOException ex) {
            Logger.getLogger(ItemListingPage.class.getName()).log(Level.SEVERE, "Error while processing menu info file", ex);
            setCustomFontForDialogs();
            JOptionPane.showMessageDialog(null, "Failed to process the menu data. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "PNG", "JPEG", "JPG Images", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imagePath = selectedFile.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(selectedFile.getAbsolutePath())
                    .getImage().getScaledInstance(imageLbl.getWidth(), imageLbl.getHeight(), Image.SCALE_SMOOTH));
            imageLbl.setText("");
            imageLbl.setIcon(imageIcon);
        }
    }

    private void setCustomFontForDialogs() {
        Font customFont = new Font("Segoe UI", Font.PLAIN, 16);
        UIManager.put("OptionPane.messageFont", customFont);
        UIManager.put("OptionPane.buttonFont", customFont);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> availabilityBox;
    private javax.swing.JLabel availabilityLbl;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel desLbl;
    private javax.swing.JTextArea desTxt;
    private javax.swing.JLabel imageLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JLabel priceLbl;
    private javax.swing.JTextField priceTxt;
    private javax.swing.JButton saveBtn;
    private javax.swing.JLabel uploadLbl;
    // End of variables declaration//GEN-END:variables
}
