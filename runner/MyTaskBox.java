package foodhub.runner;

public class MyTaskBox extends javax.swing.JPanel {

    private String taskID;
    private String customerName;
    private String address;
    private String orderID;
    
    public MyTaskBox(String taskID, String customerName, String address, String orderedItems, String orderID) {
        initComponents();
        this.taskID = taskID;
        this.customerName = customerName;
        this.address = address;
        this.orderID = orderID;
        orderNoLbl.setText(taskID);
        customerLbl.setText(customerName);
        itemLbl.setText(orderedItems);
        addressLbl.setText(address);
    }

    public String getTaskID() {
        return taskID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getOrderID() {
        return orderID;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        orderNoLbl = new javax.swing.JLabel();
        itemLbl = new javax.swing.JLabel();
        addressLbl = new javax.swing.JLabel();
        customerLbl = new javax.swing.JLabel();
        customer = new javax.swing.JLabel();
        addressLbl1 = new javax.swing.JLabel();

        orderNoLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        orderNoLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        orderNoLbl.setText("TaskID");

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

        addressLbl1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addressLbl1.setText("Address:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(itemLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                        .addGap(199, 199, 199))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(orderNoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(customer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(customerLbl))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addressLbl1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addressLbl)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(orderNoLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addressLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLbl;
    private javax.swing.JLabel addressLbl1;
    private javax.swing.JLabel customer;
    private javax.swing.JLabel customerLbl;
    private javax.swing.JLabel itemLbl;
    private javax.swing.JLabel orderNoLbl;
    // End of variables declaration//GEN-END:variables
}
