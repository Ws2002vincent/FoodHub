package foodhub.runner;

public class RunnerReviewBox extends javax.swing.JPanel {

    public RunnerReviewBox(String runnerID, String customerName, String comment, int rating) {
        initComponents();
    }

    public void updateReview(Review review) {
        nameLbl.setText(review.getCustomerName());
        feedbackLbl.setText(review.getComment());
        starLbl.setText(getStars(review.getRating()));
    }

    private String getStars(int rating) {
        StringBuilder stars = new StringBuilder("<html>");

        for (int i = 1; i <= 5; i++) {
            if (i <= rating) {
                stars.append("<span style='font-size:15px; color:orange;'>★</span>");
            } else {
                stars.append("<span style='font-size:15px; color:gray;'>☆</span>");
            }
        }

        stars.append("</html>");
        return stars.toString();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customernameLbl = new javax.swing.JLabel();
        feedbackLbl = new javax.swing.JLabel();
        nameLbl = new javax.swing.JLabel();
        starLbl = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 245, 245));

        customernameLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        customernameLbl.setText("Customer Name:");

        feedbackLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        feedbackLbl.setText("Excellent service and delicious food!");

        nameLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nameLbl.setText("xxx");

        starLbl.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        starLbl.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(feedbackLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(starLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(customernameLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nameLbl)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customernameLbl)
                    .addComponent(nameLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(starLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(feedbackLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel customernameLbl;
    private javax.swing.JLabel feedbackLbl;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JLabel starLbl;
    // End of variables declaration//GEN-END:variables
}
