package foodhub.vendor;

import java.awt.*;
import java.time.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class CheckOrderHistory extends javax.swing.JPanel {

    private String vendorID;
    private List<Order> orderList;
    private List<OrderHistoryBox> ordersList = new ArrayList<>();

    public CheckOrderHistory(String vendorID) {
        this.vendorID = vendorID;
        initComponents();

        // "Daily" radio button by default
        dailyRadioButton.setSelected(true);
        dailyRadioButtonActionPerformed(null);

        // Group radiobutton - Daily, Monthly, Yearly, Quarterly
        ButtonGroup filterGroup = new ButtonGroup();
        filterGroup.add(dailyRadioButton);
        filterGroup.add(monthlyRadioButton);
        filterGroup.add(yearlyRadioButton);
        filterGroup.add(quarterlyRadioButton);

        orderList = OrderLoader.loadOrders("C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt", vendorID);

        orderPnl.setLayout(new BoxLayout(orderPnl, BoxLayout.Y_AXIS));

        for (Order order : orderList) {
            OrderHistoryBox orderBox = new OrderHistoryBox(
                    order.getOrderId(),
                    order.getCustomer(),
                    order.getVendorID(),
                    order.getServiceType(),
                    order.getDateTime(),
                    order.getAmount(),
                    order.getOrderStatus(),
                    order.getOrderedItems()
            );

            switch (order.getOrderStatus()) {
                case "Completed" ->
                    orderBox.setStatusTextColor(Color.GREEN);
                case "Cancelled" ->
                    orderBox.setStatusTextColor(Color.RED);
                case "Preparing" ->
                    orderBox.setStatusTextColor(Color.ORANGE);
                case "Ready" ->
                    orderBox.setStatusTextColor(Color.BLUE);
                default ->
                    orderBox.setStatusTextColor(Color.GRAY); // Status Pending
            }

            ordersList.add(orderBox);
            orderPnl.add(orderBox);
            orderPnl.add(Box.createVerticalStrut(15));
        }

        orderPnl.revalidate();
        orderPnl.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filterGroup = new javax.swing.ButtonGroup();
        titleLbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderPnl = new javax.swing.JPanel();
        filterPanel = new javax.swing.JPanel();
        jMonthChooser = new com.toedter.calendar.JMonthChooser();
        jYearChooser = new com.toedter.calendar.JYearChooser();
        jComboBox = new javax.swing.JComboBox<>();
        filterBtn = new javax.swing.JButton();
        resetBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser = new com.toedter.calendar.JDateChooser();
        dailyRadioButton = new javax.swing.JRadioButton();
        monthlyRadioButton = new javax.swing.JRadioButton();
        yearlyRadioButton = new javax.swing.JRadioButton();
        quarterlyRadioButton = new javax.swing.JRadioButton();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jLabel4 = new javax.swing.JLabel();
        jYearChooser2 = new com.toedter.calendar.JYearChooser();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        titleLbl.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        titleLbl.setText("Order History");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        orderPnl.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout orderPnlLayout = new javax.swing.GroupLayout(orderPnl);
        orderPnl.setLayout(orderPnlLayout);
        orderPnlLayout.setHorizontalGroup(
            orderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 853, Short.MAX_VALUE)
        );
        orderPnlLayout.setVerticalGroup(
            orderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 594, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(orderPnl);

        filterPanel.setBackground(new java.awt.Color(255, 255, 255));
        filterPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jMonthChooser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Q1 (Jan - Mar)", "Q2 (Apr - June)", "Q3 (July - Sep)", "Q4 (Oct - Dec)" }));

        filterBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        filterBtn.setText("Apply Filter");
        filterBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterBtnActionPerformed(evt);
            }
        });

        resetBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        resetBtn.setText("Reset");
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Quarterly Filter");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Daily Filter");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Monthly Filter");

        jDateChooser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        dailyRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        dailyRadioButton.setText("Daily");
        dailyRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dailyRadioButtonActionPerformed(evt);
            }
        });

        monthlyRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        monthlyRadioButton.setText("Monthly");
        monthlyRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthlyRadioButtonActionPerformed(evt);
            }
        });

        yearlyRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        yearlyRadioButton.setText("Yearly");
        yearlyRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearlyRadioButtonActionPerformed(evt);
            }
        });

        quarterlyRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        quarterlyRadioButton.setText("Quarterly");
        quarterlyRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quarterlyRadioButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setText("Yearly Filter");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Filter By");

        javax.swing.GroupLayout filterPanelLayout = new javax.swing.GroupLayout(filterPanel);
        filterPanel.setLayout(filterPanelLayout);
        filterPanelLayout.setHorizontalGroup(
            filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, filterPanelLayout.createSequentialGroup()
                        .addComponent(filterBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(filterPanelLayout.createSequentialGroup()
                        .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jYearChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, filterPanelLayout.createSequentialGroup()
                                    .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(monthlyRadioButton)
                                        .addComponent(dailyRadioButton))
                                    .addGap(18, 18, 18)
                                    .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(yearlyRadioButton)
                                        .addComponent(quarterlyRadioButton))))
                            .addComponent(jLabel5)
                            .addGroup(filterPanelLayout.createSequentialGroup()
                                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jMonthChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jYearChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jYearChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        filterPanelLayout.setVerticalGroup(
            filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(16, 16, 16)
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dailyRadioButton)
                    .addComponent(yearlyRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthlyRadioButton)
                    .addComponent(quarterlyRadioButton))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jYearChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jMonthChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jYearChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jYearChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetBtn)
                    .addComponent(filterBtn))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(filterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLbl)
                        .addGap(221, 562, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(titleLbl)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                    .addComponent(filterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void filterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterBtnActionPerformed
        if (dailyRadioButton.isSelected()) {
            System.out.println("Daily Filter: " + jDateChooser.getDate());
            dailyFilter();

        } else if (monthlyRadioButton.isSelected()) {
            System.out.println("Monthly Filter: " + (jMonthChooser.getMonth() + 1) + ", Year: " + jYearChooser.getYear());
            monthlyFilter();

        } else if (quarterlyRadioButton.isSelected()) {
            System.out.println("Quarterly Filter: " + jComboBox.getSelectedItem() + ", Year: " + jYearChooser1.getYear());
            quarterlyFilter();

        } else if (yearlyRadioButton.isSelected()) {
            System.out.println("Yearly Filter: " + jYearChooser2.getYear());
            yearlyFilter();
        }
    }//GEN-LAST:event_filterBtnActionPerformed

    private void dailyFilter() {
        Date selectedDate = jDateChooser.getDate();

        if (selectedDate == null) {
            System.out.println("No date selected. Please choose a date.");
            return; // Stop execution to prevent errors
        }

        Instant instant = selectedDate.toInstant();
        LocalDate selectedLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        orderPnl.removeAll();
        orderPnl.setLayout(new BoxLayout(orderPnl, BoxLayout.Y_AXIS));

        boolean orderFound = false;
        for (OrderHistoryBox orderBox : ordersList) {
            LocalDateTime orderDate = orderBox.getOrderDate();

            if (orderDate != null && orderDate.toLocalDate().isEqual(selectedLocalDate)) {
                orderPnl.add(orderBox);
                orderPnl.add(Box.createVerticalStrut(15));
                orderFound = true;
            }

        }
        // No orders found for the selected date
        if (!orderFound) {
            JLabel noOrdersLabel = new JLabel("No orders found for this date!");
            noOrdersLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
            noOrdersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            orderPnl.add(Box.createVerticalGlue());
            orderPnl.add(noOrdersLabel);
            orderPnl.add(Box.createVerticalGlue());
        }
        orderPnl.revalidate();
        orderPnl.repaint();
    }

    private void monthlyFilter() {
        int selectedMonth = jMonthChooser.getMonth() + 1;
        int selectedYear = jYearChooser.getYear();

        orderPnl.removeAll();
        orderPnl.setLayout(new BoxLayout(orderPnl, BoxLayout.Y_AXIS));

        boolean orderFound = false;
        for (OrderHistoryBox orderBox : ordersList) {
            LocalDateTime orderDate = orderBox.getOrderDate();

            if (orderDate != null) {
                if (orderDate.getMonthValue() == selectedMonth && orderDate.getYear() == selectedYear) {
                    orderPnl.add(orderBox);
                    orderPnl.add(Box.createVerticalStrut(15));
                    orderFound = true;
                }
            }
        }

        if (!orderFound) {
            JLabel noOrdersLabel = new JLabel("No orders found for this month!");
            noOrdersLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
            noOrdersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            orderPnl.add(Box.createVerticalGlue());
            orderPnl.add(noOrdersLabel);
            orderPnl.add(Box.createVerticalGlue());
        }
        orderPnl.revalidate();
        orderPnl.repaint();
    }

    private void yearlyFilter() {
        int selectedYear = jYearChooser2.getYear();

        orderPnl.removeAll();
        orderPnl.setLayout(new BoxLayout(orderPnl, BoxLayout.Y_AXIS));

        boolean orderFound = false;
        for (OrderHistoryBox orderBox : ordersList) {
            LocalDateTime orderDate = orderBox.getOrderDate();

            if (orderDate != null) {
                if (orderDate.getYear() == selectedYear) {
                    orderPnl.add(orderBox);
                    orderPnl.add(Box.createVerticalStrut(15));
                    orderFound = true;
                }
            }
        }
        if (!orderFound) {
            JLabel noOrdersLabel = new JLabel("No orders found for this year!");
            noOrdersLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
            noOrdersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            orderPnl.add(Box.createVerticalGlue());
            orderPnl.add(noOrdersLabel);
            orderPnl.add(Box.createVerticalGlue());
        }
        orderPnl.revalidate();
        orderPnl.repaint();
    }

    private void quarterlyFilter() {
        String selectedQuarter = (String) jComboBox.getSelectedItem();
        int selectedYear = jYearChooser1.getYear();

        int startMonth = 0;
        int endMonth = 0;

        switch (selectedQuarter) {
            case "Q1 (Jan - Mar)":
                startMonth = 1; // January
                endMonth = 3; // March
                break;
            case "Q2 (Apr - Jun)":
                startMonth = 4; // April
                endMonth = 6; // June
                break;
            case "Q3 (Jul - Sep)":
                startMonth = 7; // July
                endMonth = 9; // September
                break;
            case "Q4 (Oct - Dec)":
                startMonth = 10; // October
                endMonth = 12; // December
                break;
        }
        orderPnl.removeAll();
        orderPnl.setLayout(new BoxLayout(orderPnl, BoxLayout.Y_AXIS));

        boolean orderFound = false;

        for (OrderHistoryBox orderBox : ordersList) {
            LocalDateTime orderDate = orderBox.getOrderDate();

            if (orderDate != null) {
                int orderYear = orderDate.getYear();
                int orderMonth = orderDate.getMonthValue();
                System.out.println("Order Date: " + orderDate);

                if (orderYear == selectedYear && orderMonth >= startMonth && orderMonth <= endMonth) {
                    orderPnl.add(orderBox);
                    orderPnl.add(Box.createVerticalStrut(15));
                    orderFound = true;
                }
            }
        }
        if (!orderFound) {
            JLabel noOrdersLabel = new JLabel("No orders found for this quarter.");
            noOrdersLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
            noOrdersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            orderPnl.add(Box.createVerticalGlue());
            orderPnl.add(noOrdersLabel);
            orderPnl.add(Box.createVerticalGlue());
        }
        orderPnl.revalidate();
        orderPnl.repaint();
    }


    private void monthlyRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthlyRadioButtonActionPerformed
        setFilterState(false, true, false, true, false, false);
    }//GEN-LAST:event_monthlyRadioButtonActionPerformed

    private void quarterlyRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quarterlyRadioButtonActionPerformed
        setFilterState(false, false, true, false, true, false);
    }//GEN-LAST:event_quarterlyRadioButtonActionPerformed

    private void yearlyRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearlyRadioButtonActionPerformed
        setFilterState(false, false, false, false, false, true);
    }//GEN-LAST:event_yearlyRadioButtonActionPerformed

    private void dailyRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dailyRadioButtonActionPerformed
        setFilterState(true, false, false, false, false, false);
    }//GEN-LAST:event_dailyRadioButtonActionPerformed

    private void setFilterState(
            boolean enableDateChooser, boolean enableMonthChooser, boolean enableComboBox, boolean enableYearChooser, boolean enableYearChooser1, boolean enableYearChooser2
    ) {
        // Enable/disable components based on the filter
        jDateChooser.setEnabled(enableDateChooser);
        jMonthChooser.setEnabled(enableMonthChooser);
        jComboBox.setEnabled(enableComboBox);
        jYearChooser.setEnabled(enableYearChooser);
        jYearChooser1.setEnabled(enableYearChooser1);
        jYearChooser2.setEnabled(enableYearChooser2);
    }

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        dailyRadioButton.setSelected(true);
        dailyRadioButtonActionPerformed(null);

        // Reset values for all components
        jDateChooser.setDate(null);
        jMonthChooser.setMonth(0);
        jComboBox.setSelectedIndex(0);
        jYearChooser.setYear(2025);
        jYearChooser1.setYear(2025);
        jYearChooser2.setYear(2025);

        // Clear all filters from the panel and show all orders
        orderPnl.removeAll();
        for (OrderHistoryBox orderBox : ordersList) {
            orderPnl.add(orderBox);
            orderPnl.add(Box.createVerticalStrut(15));
        }
        orderPnl.revalidate();
        orderPnl.repaint();
    }//GEN-LAST:event_resetBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton dailyRadioButton;
    private javax.swing.JButton filterBtn;
    private javax.swing.ButtonGroup filterGroup;
    private javax.swing.JPanel filterPanel;
    private javax.swing.JComboBox<String> jComboBox;
    private com.toedter.calendar.JDateChooser jDateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private com.toedter.calendar.JMonthChooser jMonthChooser;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JYearChooser jYearChooser;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    private com.toedter.calendar.JYearChooser jYearChooser2;
    private javax.swing.JRadioButton monthlyRadioButton;
    private javax.swing.JPanel orderPnl;
    private javax.swing.JRadioButton quarterlyRadioButton;
    private javax.swing.JButton resetBtn;
    private javax.swing.JLabel titleLbl;
    private javax.swing.JRadioButton yearlyRadioButton;
    // End of variables declaration//GEN-END:variables
}
