package foodhub.runner;

public class TaskBox extends javax.swing.JPanel {

    private String orderID;
    private String customerName;
    private String address;

    public TaskBox(String orderID, String customerName, String serviceType, String date, String orderedItems, String deliveryFee, String address) {
        initComponents();
        this.orderID = orderID;
        this.customerName = customerName;
        this.address = address;
        customerLbl.setText(customerName);
        orderNoLbl.setText("#" + orderID);
        dateTimeLbl.setText(date);
        serviceTypeLbl.setText(serviceType);
        itemLbl.setText(orderedItems);
        feeLbl.setText(deliveryFee);
        addressLbl.setText(address);
    }

    public void EmptyBox() {
        System.out.println("There is no new delivery order.");
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serviceLbl = new javax.swing.JLabel();
        serviceTypeLbl = new javax.swing.JLabel();
        orderNoLbl = new javax.swing.JLabel();
        dateTimeLbl = new javax.swing.JLabel();
        itemLbl = new javax.swing.JLabel();
        addressLbl = new javax.swing.JLabel();
        customerLbl = new javax.swing.JLabel();
        customer = new javax.swing.JLabel();
        delivery = new javax.swing.JLabel();
        feeLbl = new javax.swing.JLabel();
        addressLbl1 = new javax.swing.JLabel();

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

        addressLbl.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        addressLbl.setText("Address");

        customerLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerLbl.setText("Customer Name");

        customer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customer.setText("Customer:");

        delivery.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        delivery.setText("Delivery Fee: RM");

        feeLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        feeLbl.setText("Fee");

        addressLbl1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addressLbl1.setText("Address:");

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
                        .addComponent(itemLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(199, 199, 199))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(customer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(customerLbl)
                        .addContainerGap(195, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(serviceTypeLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(delivery)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(feeLbl)
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(orderNoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateTimeLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addressLbl1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addressLbl)
                        .addGap(14, 14, 14))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(orderNoLbl)
                    .addComponent(dateTimeLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addressLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addressLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(itemLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(serviceTypeLbl)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(feeLbl)
                        .addComponent(delivery)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceLbl))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLbl;
    private javax.swing.JLabel addressLbl1;
    private javax.swing.JLabel customer;
    private javax.swing.JLabel customerLbl;
    private javax.swing.JLabel dateTimeLbl;
    private javax.swing.JLabel delivery;
    private javax.swing.JLabel feeLbl;
    private javax.swing.JLabel itemLbl;
    private javax.swing.JLabel orderNoLbl;
    private javax.swing.JLabel serviceLbl;
    private javax.swing.JLabel serviceTypeLbl;
    // End of variables declaration//GEN-END:variables
}
