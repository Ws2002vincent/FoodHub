package foodhub.runner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class RunnerReview extends javax.swing.JPanel {
    private String runnerID;

    public RunnerReview(String runnerID) {
        this.runnerID = runnerID;
        initComponents();

        List<Review> reviewList = loadReview("C:\\FoodHub\\src\\foodhub\\Database\\Deliveries.txt", runnerID);

        pnlReviews.setLayout(new BoxLayout(pnlReviews, BoxLayout.Y_AXIS));

        //Load review into UI
        for (Review review : reviewList) {
            RunnerReviewBox runnerReviewBox = new RunnerReviewBox(review.getRunnerID(), review.getCustomerName(), review.getComment(), review.getRating());
            runnerReviewBox.updateReview(review);
            pnlReviews.add(runnerReviewBox);
            pnlReviews.add(Box.createVerticalStrut(15));
            pnlReviews.revalidate();
            pnlReviews.repaint();
        }
    }

    private List<Review> loadReview(String filePath, String runnerID) {
        List<Review> reviewList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data[1].trim().equals(runnerID)) {
                    // Convert data[3] to int
                    int rating = Integer.parseInt(data[3].trim());

                    Review review = new Review(
                            data[1].trim(), // runnerID
                            data[2].trim(), // customerName
                            data[4].trim(), // comment
                            rating
                    );
                    reviewList.add(review);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading feedbacks: " + e.getMessage());
        }

        return reviewList;
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
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(titleLbl)
                            .addGap(96, 178, Short.MAX_VALUE)))
                    .addGap(15, 15, 15)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(titleLbl)
                    .addGap(18, 18, 18)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                    .addGap(9, 9, 9)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlReviews;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JLabel titleLbl;
    // End of variables declaration//GEN-END:variables
}
