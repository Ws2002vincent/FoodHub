package foodhub.vendor;

import java.io.*;
import java.util.*;

public class NotificationMessage extends javax.swing.JPanel {

    private String notificationID;
    private String title;
    private String message;
    private String dateTime;
    private String readStatus;

    public NotificationMessage(String notificationID, String title, String message, String dateTime, String readStatus) {
        this.notificationID = notificationID;
        this.title = title;
        this.message = message;
        this.dateTime = dateTime;
        this.readStatus = readStatus;
        initComponents();

        notificationMessageLbl.setText("<html>" + message + "</html>");
        notificationTitleLbl.setText(title);
        dateTimeLbl.setText(dateTime);

        if (readStatus.equalsIgnoreCase("Read")) {
            statusLbl.setVisible(false);
            notificationTitleLbl.setForeground(new java.awt.Color(204, 204, 204));
            notificationMessageLbl.setForeground(new java.awt.Color(204, 204, 204));
            dateTimeLbl.setForeground(new java.awt.Color(204, 204, 204));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusLbl = new javax.swing.JPanel();
        notificationMessageLbl = new javax.swing.JLabel();
        notificationTitleLbl = new javax.swing.JLabel();
        dateTimeLbl = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 245, 245));
        setMaximumSize(new java.awt.Dimension(1281, 100));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        statusLbl.setBackground(new java.awt.Color(255, 204, 186));
        statusLbl.setPreferredSize(new java.awt.Dimension(5, 0));

        javax.swing.GroupLayout statusLblLayout = new javax.swing.GroupLayout(statusLbl);
        statusLbl.setLayout(statusLblLayout);
        statusLblLayout.setHorizontalGroup(
            statusLblLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        statusLblLayout.setVerticalGroup(
            statusLblLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        notificationMessageLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        notificationMessageLbl.setText("Message");
        notificationMessageLbl.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        notificationTitleLbl.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        notificationTitleLbl.setForeground(new java.awt.Color(255, 102, 102));
        notificationTitleLbl.setText("Title");

        dateTimeLbl.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        dateTimeLbl.setForeground(new java.awt.Color(153, 153, 153));
        dateTimeLbl.setText("Date & Time");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(statusLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(notificationMessageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(notificationTitleLbl)
                            .addComponent(dateTimeLbl))
                        .addGap(0, 290, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(notificationMessageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateTimeLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(notificationTitleLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        updateReadStatus();
        notificationTitleLbl.setForeground(new java.awt.Color(204, 204, 204));
        notificationMessageLbl.setForeground(new java.awt.Color(204, 204, 204));
        dateTimeLbl.setForeground(new java.awt.Color(204, 204, 204));
        statusLbl.setVisible(false);
    }//GEN-LAST:event_formMouseClicked

    private void updateReadStatus() {
        File inputFile = new File("C:\\FoodHub\\src\\foodhub\\Database\\Notification.txt");

        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String line;

                while ((line = br.readLine()) != null) {
                    String[] data = line.split("\\|");

                    if (data[0].trim().equals(this.notificationID)) {
                        data[6] = " Read"; // Update status to "Read"
                    }

                    lines.add(String.join("|", data));
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile))) {
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            System.err.println("Error updating notification status: " + e.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dateTimeLbl;
    private javax.swing.JLabel notificationMessageLbl;
    private javax.swing.JLabel notificationTitleLbl;
    private javax.swing.JPanel statusLbl;
    // End of variables declaration//GEN-END:variables
}
