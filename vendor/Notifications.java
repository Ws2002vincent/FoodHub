package foodhub.vendor;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class Notifications extends javax.swing.JPanel {

    private String vendorID;

    public Notifications(String vendorID) {
        this.vendorID = vendorID;
        initComponents();
        loadNotifications();
    }

    private void loadNotifications() {
        List<String[]> notificationList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mma");

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Notification.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[2].trim().equals(vendorID)) {
                    notificationList.add(data);
                }
            }

            // Sort the dateTime in desc order
            notificationList.sort((n1, n2) -> {
                try {
                    Date date1 = sdf.parse(n1[5].trim());
                    Date date2 = sdf.parse(n2[5].trim());
                    return date2.compareTo(date1);
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0; // If parsing fails, consider them as equal
                }
            });

            pnlItems.removeAll();
            pnlItems.setLayout(new javax.swing.BoxLayout(pnlItems, javax.swing.BoxLayout.Y_AXIS));

            for (String[] data : notificationList) {
                String notificationID = data[0].trim();
                String title = data[3].trim();
                String message = data[4].trim();
                String dateTime = data[5].trim();
                String readStatus = data[6].trim();

                NotificationMessage notification = new NotificationMessage(notificationID, title, message, dateTime, readStatus);
                pnlItems.add(notification);
                pnlItems.add(Box.createVerticalStrut(15));
            }

            pnlItems.revalidate();
            pnlItems.repaint();

        } catch (IOException e) {
            System.err.println("Failed to load notifications: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        pnlItems = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(new java.awt.Dimension(1, 1));
        scrollPane.setMinimumSize(new java.awt.Dimension(10, 6));

        pnlItems.setBackground(new java.awt.Color(255, 255, 255));
        pnlItems.setAutoscrolls(true);
        pnlItems.setLayout(new java.awt.GridLayout(1, 0));
        scrollPane.setViewportView(pnlItems);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Notifications");

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
                        .addGap(96, 206, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnlItems;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
