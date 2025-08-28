package foodhub.vendor;

public class OrderBox extends javax.swing.JPanel {

    private String orderID;
    private String customerID;

    public OrderBox(String orderID, String customerID, String vendorID, String serviceType, String date, String orderedItems) {
        initComponents();
        this.orderID = orderID;
        this.customerID = customerID;
        orderNoLbl.setText("#" + orderID);
        dateTimeLbl.setText(date);
        serviceTypeLbl.setText(serviceType);
        itemLbl.setText("<html><p style='width:300px;'>" + orderedItems);
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serviceLbl = new javax.swing.JLabel();
        serviceTypeLbl = new javax.swing.JLabel();
        orderNoLbl = new javax.swing.JLabel();
        dateTimeLbl = new javax.swing.JLabel();
        itemLbl = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 245, 245));
        setMaximumSize(new java.awt.Dimension(458, 165));

        serviceLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        serviceLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        serviceTypeLbl.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        serviceTypeLbl.setText("Takeaway");

        orderNoLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        orderNoLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        orderNoLbl.setText("OrderID");

        dateTimeLbl.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        dateTimeLbl.setText("Date & Time");

        itemLbl.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        itemLbl.setText("Item");
        itemLbl.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        itemLbl.setMaximumSize(new java.awt.Dimension(27, 16));
        itemLbl.setPreferredSize(new java.awt.Dimension(27, 16));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(serviceLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(itemLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(serviceTypeLbl)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(orderNoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateTimeLbl)
                        .addContainerGap(193, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(orderNoLbl)
                    .addComponent(dateTimeLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceTypeLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dateTimeLbl;
    private javax.swing.JLabel itemLbl;
    private javax.swing.JLabel orderNoLbl;
    private javax.swing.JLabel serviceLbl;
    private javax.swing.JLabel serviceTypeLbl;
    // End of variables declaration//GEN-END:variables
}
