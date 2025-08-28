package foodhub.vendor;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class CustomerReview extends javax.swing.JPanel {

    private String vendorID;

    public CustomerReview(String vendorID) {
        this.vendorID = vendorID;
        initComponents();

        List<Feedback> feedbackList = loadFeedbacks("C:\\FoodHub\\src\\foodhub\\Database\\Feedbacks.txt", vendorID);

        pnlReviews.setLayout(new BoxLayout(pnlReviews, BoxLayout.Y_AXIS));

        for (Feedback feedback : feedbackList) {
            ReviewBox reviewBox = new ReviewBox(feedback.getFeedbackID(), feedback.getOrderID(), feedback.getVendorID(), feedback.getCustomerName(), feedback.getRating(), feedback.getComment());
            reviewBox.updateReview(feedback);
            pnlReviews.add(reviewBox);
            pnlReviews.add(Box.createVerticalStrut(15));
            pnlReviews.revalidate();
            pnlReviews.repaint();
        }
    }

    private List<Feedback> loadFeedbacks(String filePath, String vendorID) {
        List<Feedback> feedbackList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length == 6 && data[2].trim().equals(vendorID)) {
                    Feedback feedback = new Feedback(
                            data[0].trim(), // feedbackID
                            data[1].trim(), // orderID
                            data[2].trim(), // vendorID
                            data[3].trim(), // customerName
                            Integer.parseInt(data[4].trim()), // rating
                            data[5].trim() // comment
                    );
                    feedbackList.add(feedback);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading feedbacks: " + e.getMessage());
        }

        return feedbackList;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLbl = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        pnlReviews = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        titleLbl.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        titleLbl.setText("Customer Review");

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(new java.awt.Dimension(1, 1));
        scrollPane.setMinimumSize(new java.awt.Dimension(10, 6));

        pnlReviews.setBackground(new java.awt.Color(255, 255, 255));
        pnlReviews.setAutoscrolls(true);
        pnlReviews.setLayout(new java.awt.GridLayout(1, 0));
        scrollPane.setViewportView(pnlReviews);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLbl)
                        .addGap(96, 178, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(titleLbl)
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlReviews;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JLabel titleLbl;
    // End of variables declaration//GEN-END:variables
}
