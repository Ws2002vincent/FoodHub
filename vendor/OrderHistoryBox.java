package foodhub.vendor;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderHistoryBox extends javax.swing.JPanel {

    private String orderStatus;
    private String customer;
    private LocalDateTime orderDate;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ssa");

    public OrderHistoryBox(String orderID, String customer, String vendorID, String serviceType, String date, double totalAmount, String orderStatus, String orderedItems) {
        initComponents();
        this.orderStatus = orderStatus;
        this.orderDate = LocalDateTime.parse(date, FORMATTER);

        orderNoLbl.setText("#" + orderID);
        dateTimeLbl.setText(date);
        customerNameLbl.setText(customer);
        serviceTypeLbl.setText(serviceType);
        amountLbl.setText(String.format("RM %.2f", totalAmount));
        statusType.setText(orderStatus);
        itemLbl.setText("<html><p style='width:650px;'>" + orderedItems + "</p></html>");
    }

    public void setStatusTextColor(Color color) {
        if (statusType != null) {
            statusType.setForeground(color);
        }
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public String getCustomerName() {
        return this.customer;
    }

    public LocalDateTime getOrderDate() {
        return this.orderDate;
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

        setBackground(new java.awt.Color(245, 245, 245));
        setMaximumSize(new java.awt.Dimension(32767, 196));

        createdLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        createdLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        createdLbl.setText("Created at:");

        orderNoLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        orderNoLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        orderNoLbl.setText("OrderID");

        dateTimeLbl.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        dateTimeLbl.setText("Date & Time");

        customerLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        customerLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        customerLbl.setText("Customer:");

        customerNameLbl.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        customerNameLbl.setText("Name");

        amountLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        amountLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        amountLbl.setText("Price");

        orderedItemLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        orderedItemLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        orderedItemLbl.setText("Ordered Items:");

        itemLbl.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        itemLbl.setText("Item");
        itemLbl.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        serviceLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        serviceLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        serviceLbl.setText("Service Type:");

        serviceTypeLbl.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        serviceTypeLbl.setText("Takeaway");

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
                        .addContainerGap(316, Short.MAX_VALUE))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(statusLbl)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(statusType))
                                    .addComponent(amountLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                        .addGap(0, 42, Short.MAX_VALUE))
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
