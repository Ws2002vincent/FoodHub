package foodhub.runner;

import java.awt.Color;

public class TaskHistoryBox extends javax.swing.JPanel {

    private String taskID;
    private String customerName;
    private String deliveredDate;
    private String address;
    private String orderedItem;
    private String status;
    private String deliveryFee;

    public TaskHistoryBox(String taskID, String customerName, String deliveredDate, String address, String orderedItem, String status, String deliveryFee) {
        initComponents();
        this.taskID = taskID;
        this.customerName = customerName;
        this.deliveredDate = deliveredDate;
        this.address = address;
        this.orderedItem = orderedItem;
        this.status = status;
        this.deliveryFee = deliveryFee;
        orderNoLbl.setText(taskID);
        dateTimeLbl.setText(deliveredDate);
        customerNameLbl.setText(customerName);
        serviceTypeLbl.setText(address);
        itemLbl.setText(orderedItem);
        statusType.setText(status);
        amountLbl.setText(deliveryFee);
    }

    public String getTaskID() {
        return taskID;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public String getOrderedItem() {
        return orderedItem;
    }

    public String getStatus() {
        return status;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public String getAddress() {
        return address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setStatusTextColor(Color color) {
        if (statusType != null) {
            statusType.setForeground(color);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createdLbl = new javax.swing.JLabel();
        orderNoLbl = new javax.swing.JLabel();
        dateTimeLbl = new javax.swing.JLabel();
        customerLbl = new javax.swing.JLabel();
        customerNameLbl = new javax.swing.JLabel();
        amountLbl = new javax.swing.JLabel();
        orderedItemLbl = new javax.swing.JLabel();
        itemLbl = new javax.swing.JLabel();
        serviceLbl = new javax.swing.JLabel();
        serviceTypeLbl = new javax.swing.JLabel();
        statusLbl = new javax.swing.JLabel();
        statusType = new javax.swing.JLabel();

        createdLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        createdLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        createdLbl.setText("Delivered at");

        orderNoLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        orderNoLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        orderNoLbl.setText("TaskID");

        dateTimeLbl.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        dateTimeLbl.setText("Date & Time");

        customerLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        customerLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        customerLbl.setText("Customer:");

        customerNameLbl.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        customerNameLbl.setText("Name");

        amountLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        amountLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        amountLbl.setText("Delivery Fee");

        orderedItemLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        orderedItemLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        orderedItemLbl.setText("Ordered Items:");

        itemLbl.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        itemLbl.setText("Item");
        itemLbl.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        serviceLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        serviceLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        serviceLbl.setText("Address:");

        serviceTypeLbl.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        serviceTypeLbl.setText("Address");

        statusLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        statusLbl.setText("Status:");

        statusType.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        statusType.setText("Completed");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(customerLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customerNameLbl))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(serviceLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(serviceTypeLbl)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(orderedItemLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(itemLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(createdLbl)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dateTimeLbl))
                                    .addComponent(orderNoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(statusLbl)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(statusType))
                                    .addComponent(amountLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(15, 15, 15))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(orderNoLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(createdLbl)
                            .addComponent(dateTimeLbl)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusLbl)
                            .addComponent(statusType))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(amountLbl)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerLbl)
                    .addComponent(customerNameLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serviceLbl)
                    .addComponent(serviceTypeLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(orderedItemLbl)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(itemLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amountLbl;
    private javax.swing.JLabel createdLbl;
    private javax.swing.JLabel customerLbl;
    private javax.swing.JLabel customerNameLbl;
    private javax.swing.JLabel dateTimeLbl;
    private javax.swing.JLabel itemLbl;
    private javax.swing.JLabel orderNoLbl;
    private javax.swing.JLabel orderedItemLbl;
    private javax.swing.JLabel serviceLbl;
    private javax.swing.JLabel serviceTypeLbl;
    private javax.swing.JLabel statusLbl;
    private javax.swing.JLabel statusType;
    // End of variables declaration//GEN-END:variables
}
