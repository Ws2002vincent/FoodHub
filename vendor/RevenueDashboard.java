package foodhub.vendor;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import static foodhub.FilePaths.ORDERHISTORY_FILE;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RevenueDashboard extends javax.swing.JPanel {
  
    
    String selectedVendorId = SessionVendor.getInstance().getSessionID();
    
     private OrderRevenueDataManager dataManager;
    public RevenueDashboard() {
        initComponents();
        setName("RevenueDashboard");
        if (selectedVendorId == null) {
            System.out.println("selectedVendorId is null!!!!!!");
        }
        
        loadData();
        loadVendorOrders(selectedVendorId, jTable1);
        
        
        
          jComboBox1.addActionListener(e -> {
                String revenueMonth = (String) jComboBox1.getSelectedItem();
                updateRevenueMonthly();
            });
          
          jComboBox2.addActionListener(e -> {
                String saleMonth = (String) jComboBox2.getSelectedItem();
                updateSalesMonthly();
            });
          
          jComboBox3.addActionListener(e -> {
                String tableMonth = (String) jComboBox3.getSelectedItem();
                int monthNumber = getMonthNumber(tableMonth);
                
                if (monthNumber == 0) {
                    loadVendorOrders(selectedVendorId, jTable1);
                } else {
                 loadVendorOrders2(selectedVendorId, monthNumber, jTable1);
                }
            });
        
    }
    
      private void loadData() {
        try {
            dataManager = new OrderRevenueDataManager();
            dataManager.loadOrdersFromFile(ORDERHISTORY_FILE); // Path to your file
            updateRevenueAndSales();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
      
      private void updateRevenueAndSales() {
          System.out.println("updateRevenueAndSales: " + selectedVendorId);
        double totalRevenue = dataManager.getTotalRevenueForVendor(selectedVendorId);
      double totalSales = dataManager.getOrdersByVendor(selectedVendorId).stream()
                               .filter(order -> true)  // Optionally, you can add any condition if needed
                               .count();  // Count the total number of orders
       
       total_revenue_variable.setText(String.format("%.2f", totalRevenue));
       total_sales_variable.setText(String.valueOf((int) totalSales));
    }
      
      private void updateRevenueMonthly() {
          System.out.println("updateRevenueAndSales: " + selectedVendorId);
        int selectedMonth = jComboBox1.getSelectedIndex() + 1; // Month is 1-based
        double totalRevenue = dataManager.getTotalRevenueForMonth(selectedVendorId, selectedMonth);
       total_revenue_variable3.setText(String.format("%.2f", totalRevenue));
    }
      
       private void updateSalesMonthly() {
          System.out.println("updateRevenueAndSales: " + selectedVendorId);
        int selectedMonth = jComboBox2.getSelectedIndex() + 1; // Month is 1-based
        double totalSales = dataManager.getOrdersByVendor(selectedVendorId).stream()
                                        .filter(order -> {
                                            Calendar cal = Calendar.getInstance();
                                            cal.setTime(order.getOrderDate());
                                            return cal.get(Calendar.MONTH) + 1 == selectedMonth;
                                        }).count();
       total_revenue_variable2.setText(String.valueOf((int) totalSales));
    }
       
       private void loadVendorOrders(String vendorId, JTable table) {
           DefaultTableModel model = (DefaultTableModel) table.getModel();
        // Clear existing rows in the table
        model.setRowCount(0);

        List<OrderRevenue> vendorOrders = dataManager.getOrdersByVendor(vendorId);
        
        for (OrderRevenue order : vendorOrders) {
            Object[] row = {
                order.getOrderId(),
                order.getCustomerName(),
                order.getOrderDate(),
                order.getTotalRevenue()
                
            };
            model.addRow(row); // Add row to the table model
        }
    }
       
       private void loadVendorOrders2(String selectedVendorId, int selectedMonth, JTable table) {
         DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        // Fetch orders for the selected vendor
        List<OrderRevenue> vendorOrders = dataManager.getOrdersByVendor(selectedVendorId);
        
        // Filter the orders based on the selected month
        for (OrderRevenue order : vendorOrders) {
            // Extract the month from the order date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getOrderDate());
            int orderMonth = calendar.get(Calendar.MONTH) + 1; // 0-indexed, so add 1
            
            // Only add orders from the selected month
            if (orderMonth == selectedMonth) {
                Object[] row = {
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getOrderDate(),
                    order.getFoodPrice(),
                };
                model.addRow(row); // Add the row to the table model
            }
        }
    }
       
       private int getMonthNumber(String monthName) {
        switch (monthName) {
            case "January": return 1;
            case "February": return 2;
            case "March": return 3;
            case "April": return 4;
            case "May": return 5;
            case "June": return 6;
            case "July": return 7;
            case "August": return 8;
            case "September": return 9;
            case "October": return 10;
            case "November": return 11;
            case "December": return 12;
            default: return 0; // In case the month name is invalid (shouldn't happen)
        }
    }

      


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        total_revenue_title = new javax.swing.JLabel();
        total_revenue_variable = new javax.swing.JLabel();
        total_revenue_month_title = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        total_revenue_title1 = new javax.swing.JLabel();
        total_sales_variable = new javax.swing.JLabel();
        total_revenue_variable2 = new javax.swing.JLabel();
        total_revenue_month_title2 = new javax.swing.JLabel();
        total_revenue_month_title3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        total_revenue_month_title5 = new javax.swing.JLabel();
        total_revenue_variable3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Revenue Performance Measure Dashboard");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        total_revenue_title.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        total_revenue_title.setText("Total Revenue : ");
        jPanel1.add(total_revenue_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 22, 168, 40));

        total_revenue_variable.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        total_revenue_variable.setText("total revenue to be review");
        jPanel1.add(total_revenue_variable, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 224, -1));

        total_revenue_month_title.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        total_revenue_month_title.setText("Revenue by Month : ");
        jPanel1.add(total_revenue_month_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 220, -1));

        jComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jComboBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox3MouseClicked(evt);
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 224, 33));

        total_revenue_title1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        total_revenue_title1.setText("Total Sales : ");
        jPanel1.add(total_revenue_title1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 137, 40));

        total_sales_variable.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        total_sales_variable.setText("total sales to be review");
        jPanel1.add(total_sales_variable, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 30, 240, -1));

        total_revenue_variable2.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        total_revenue_variable2.setText("Monthly revenue to be review");
        jPanel1.add(total_revenue_variable2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 140, 250, 20));

        total_revenue_month_title2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        total_revenue_month_title2.setText("Monthly sales : ");
        jPanel1.add(total_revenue_month_title2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 130, 168, 40));

        total_revenue_month_title3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        total_revenue_month_title3.setText("Sales by Month : ");
        jPanel1.add(total_revenue_month_title3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, 180, -1));

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 80, 224, 33));

        total_revenue_month_title5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        total_revenue_month_title5.setText("Monthly revenue : ");
        jPanel1.add(total_revenue_month_title5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 200, 40));

        total_revenue_variable3.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        total_revenue_variable3.setText("Monthly revenue to be review");
        jPanel1.add(total_revenue_variable3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 260, -1));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "OrderID", "Customer name", "Date", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 940, 490));

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 224, 33));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(388, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseClicked
        // Revenue
      
        
    }//GEN-LAST:event_jComboBox3MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel total_revenue_month_title;
    private javax.swing.JLabel total_revenue_month_title2;
    private javax.swing.JLabel total_revenue_month_title3;
    private javax.swing.JLabel total_revenue_month_title5;
    private javax.swing.JLabel total_revenue_title;
    private javax.swing.JLabel total_revenue_title1;
    private javax.swing.JLabel total_revenue_variable;
    private javax.swing.JLabel total_revenue_variable2;
    private javax.swing.JLabel total_revenue_variable3;
    private javax.swing.JLabel total_sales_variable;
    // End of variables declaration//GEN-END:variables
}
