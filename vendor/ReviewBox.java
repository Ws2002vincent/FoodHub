package foodhub.vendor;

public class ReviewBox extends javax.swing.JPanel {

    public ReviewBox(String feedbackID, String orderID, String vendorID, String customerName, int rating, String comment) {
        initComponents();
    }

    public void updateReview(Feedback feedback) {
        orderLbl.setText("#" + feedback.getOrderID());
        nameLbl.setText(feedback.getCustomerName());
        feedbackLbl.setText(feedback.getComment());
        starLbl.setText(getStars(feedback.getRating()));
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

        orderLbl = new javax.swing.JLabel();
        customerLbl = new javax.swing.JLabel();
        feedbackLbl = new javax.swing.JLabel();
        nameLbl = new javax.swing.JLabel();
        starLbl = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 245, 245));
        setMaximumSize(new java.awt.Dimension(1281, 160));

        orderLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        orderLbl.setText("#OR001");

        customerLbl.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        customerLbl.setText("Customer Name:");

        feedbackLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        feedbackLbl.setText("Excellent service and delicious food!");

        nameLbl.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        nameLbl.setText("xxx");

        starLbl.setText("starLabel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(feedbackLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(starLbl)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(customerLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameLbl))
                            .addComponent(orderLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(orderLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(starLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerLbl)
                    .addComponent(nameLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(feedbackLbl)
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel customerLbl;
    private javax.swing.JLabel feedbackLbl;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JLabel orderLbl;
    private javax.swing.JLabel starLbl;
    // End of variables declaration//GEN-END:variables
}
