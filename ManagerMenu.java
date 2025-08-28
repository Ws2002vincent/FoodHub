/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package foodhub;

import java.awt.HeadlessException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static foodhub.FilePaths.COMPLAINTS_FILE;
import static foodhub.FilePaths.MENUINFO_FILE;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;


/**
 *
 * @author SaponyCatty
 */
public class ManagerMenu extends javax.swing.JFrame {
DialogManager dialogManagerError = new DialogManager("An unexpected error occurred:");
DialogManager dialogManagerNotice = new DialogManager("Notice!");

    public ManagerMenu() {
        initComponents();
        setLocationRelativeTo(null);
        
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPanel = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        chartPanelForm1 = new foodhub.ChartPanelForm();
        jLabel2 = new javax.swing.JLabel();
        totalRevenue = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        vendorName = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        highestRevenue = new javax.swing.JLabel();
        recruitedVendors = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        allVendors = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        vendorTable = new javax.swing.JTable();
        ComboRevenue = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        chartPanelFormRunner1 = new foodhub.ChartPanelFormRunner();
        runnerDashboardTitle = new javax.swing.JLabel();
        runnerTotalRevenue = new javax.swing.JLabel();
        totalRevenueRunner = new javax.swing.JLabel();
        alltimeRunner = new javax.swing.JLabel();
        bestRatedtitle = new javax.swing.JLabel();
        bestRunnerName = new javax.swing.JLabel();
        averageRating = new javax.swing.JLabel();
        averageRatingOverall = new javax.swing.JLabel();
        averageRunnerRating = new javax.swing.JLabel();
        alltimeRunner1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        runnerTable = new javax.swing.JTable();
        totalRunners = new javax.swing.JLabel();
        ComboRun = new javax.swing.JComboBox<>();
        ComboRating = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        VendorCombo = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        FoodTable = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        DescArea = new javax.swing.JTextArea();
        FoodName = new javax.swing.JLabel();
        FoodPrice = new javax.swing.JLabel();
        deleteButton = new javax.swing.JButton();
        AvailabilityCombo = new javax.swing.JComboBox<>();
        VendorID = new javax.swing.JLabel();
        runnerDashboardTitle2 = new javax.swing.JLabel();
        FoodPrice1 = new javax.swing.JLabel();
        labelsetImageFood = new javax.swing.JLabel();
        FoodName1 = new javax.swing.JLabel();
        VendorID1 = new javax.swing.JLabel();
        FoodPrice2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        comboComplaints = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        complaintsArea = new javax.swing.JTextArea();
        runnerDashboardTitle1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        FeedbackTable = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        answerArea = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        resolveButton = new javax.swing.JButton();
        comboActive = new javax.swing.JComboBox<>();
        clearButton = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        itemsButton = new javax.swing.JButton();
        revenueButton = new javax.swing.JButton();
        deliveryButton = new javax.swing.JButton();
        feedbackButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 582));
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("FoodHub");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(69, 23, 90, 25);

        logoutButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(16, 55, 86));
        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-log-out-24.png"))); // NOI18N
        logoutButton.setText("Sign Out");
        logoutButton.setBorder(null);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        getContentPane().add(logoutButton);
        logoutButton.setBounds(20, 510, 90, 20);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-burger-48.png"))); // NOI18N
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(15, 14, 48, 48);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Welcome to FoodHub Manager System!");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 380, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/images/1920x1080-white.jpg"))); // NOI18N
        jLabel18.setMaximumSize(new java.awt.Dimension(640, 550));
        jLabel18.setMinimumSize(new java.awt.Dimension(640, 550));
        jLabel18.setName(""); // NOI18N
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 1080, 620));

        jTabbedPanel.addTab("Dashboard", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 840, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 575, Short.MAX_VALUE)
        );

        jTabbedPanel.addTab("Registration", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 840, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 575, Short.MAX_VALUE)
        );

        jTabbedPanel.addTab("Transaction", jPanel3);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(chartPanelForm1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 580, 280));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Revenue performance");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 15, 199, -1));

        totalRevenue.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        totalRevenue.setText("MYR");
        jPanel4.add(totalRevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 92, 175, -1));

        jLabel5.setText("Vendor Total Revenue");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 58, 188, -1));

        jLabel6.setText("all time");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 130, 127, -1));

        vendorName.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        vendorName.setText("MYR");
        jPanel4.add(vendorName, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 205, 164, -1));

        jLabel7.setText("Top selling vendor");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 173, 176, -1));

        highestRevenue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        highestRevenue.setText("all time");
        jPanel4.add(highestRevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 243, 145, -1));

        recruitedVendors.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        recruitedVendors.setText("~~~");
        jPanel4.add(recruitedVendors, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 333, 164, -1));

        jLabel8.setText("Recruited Vendors");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 301, 176, -1));

        allVendors.setText("all vendors ");
        jPanel4.add(allVendors, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 1468, 118, -1));

        jLabel9.setText("List of all user in the table");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 484, 162, -1));

        vendorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vendor ID", "Username", "Revenue", "Active Status"
            }
        ));
        jScrollPane2.setViewportView(vendorTable);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 307, 579, 193));

        ComboRevenue.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Active", "Inactive" }));
        ComboRevenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboRevenueActionPerformed(evt);
            }
        });
        jPanel4.add(ComboRevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 431, 141, 35));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/images/1920x1080-white.jpg"))); // NOI18N
        jLabel16.setMaximumSize(new java.awt.Dimension(640, 550));
        jLabel16.setMinimumSize(new java.awt.Dimension(640, 550));
        jLabel16.setName(""); // NOI18N
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -10, -1, -1));

        jTabbedPanel.addTab("Revenue", jPanel4);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(chartPanelFormRunner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 600, 250));

        runnerDashboardTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        runnerDashboardTitle.setText("Runner performance");
        jPanel5.add(runnerDashboardTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 14, 194, -1));

        runnerTotalRevenue.setText(" Runners Total revenue");
        jPanel5.add(runnerTotalRevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 57, 194, -1));

        totalRevenueRunner.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        totalRevenueRunner.setText("MYR");
        jPanel5.add(totalRevenueRunner, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 91, 194, -1));

        alltimeRunner.setText("all time");
        jPanel5.add(alltimeRunner, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 135, 72, -1));

        bestRatedtitle.setText("Best Rated Runner");
        jPanel5.add(bestRatedtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 169, 194, -1));

        bestRunnerName.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        bestRunnerName.setText("Name");
        jPanel5.add(bestRunnerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 203, 138, -1));

        averageRating.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        averageRating.setText("average");
        jPanel5.add(averageRating, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 241, 104, -1));

        averageRatingOverall.setText("Average Runner Rating");
        jPanel5.add(averageRatingOverall, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 304, 172, -1));

        averageRunnerRating.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        averageRunnerRating.setText("Name");
        jPanel5.add(averageRunnerRating, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 338, 99, -1));

        alltimeRunner1.setText("all time");
        jPanel5.add(alltimeRunner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(73, 419, 56, -1));

        runnerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User ID", "Username", "Rating", "Revenue"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(runnerTable);
        if (runnerTable.getColumnModel().getColumnCount() > 0) {
            runnerTable.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(214, 325, 603, 174));

        totalRunners.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        totalRunners.setText("total runners");
        jPanel5.add(totalRunners, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 382, 184, -1));

        ComboRun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "0-250", "250-500", "500-750" }));
        jPanel5.add(ComboRun, new org.netbeans.lib.awtextra.AbsoluteConstraints(214, 284, 131, 36));

        ComboRating.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "0-1", "1-2", "2-3", "3-4", "4-5" }));
        ComboRating.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboRatingActionPerformed(evt);
            }
        });
        jPanel5.add(ComboRating, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 284, 131, 36));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/images/1920x1080-white.jpg"))); // NOI18N
        jLabel17.setMaximumSize(new java.awt.Dimension(640, 550));
        jLabel17.setMinimumSize(new java.awt.Dimension(640, 550));
        jLabel17.setName(""); // NOI18N
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPanel.addTab("Runner", jPanel5);

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        VendorCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(VendorCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 42, 126, -1));

        FoodTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Food ID", "Food Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        FoodTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FoodTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(FoodTable);

        jPanel6.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 76, 263, 288));

        DescArea.setEditable(false);
        DescArea.setColumns(20);
        DescArea.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DescArea.setLineWrap(true);
        DescArea.setRows(5);
        DescArea.setToolTipText("");
        DescArea.setWrapStyleWord(true);
        DescArea.setMaximumSize(new java.awt.Dimension(2147483647, 480));
        DescArea.setName(""); // NOI18N
        jScrollPane7.setViewportView(DescArea);

        jPanel6.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 227, 478, 142));

        FoodName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FoodName.setText("~~~");
        jPanel6.add(FoodName, new org.netbeans.lib.awtextra.AbsoluteConstraints(573, 56, 501, -1));

        FoodPrice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FoodPrice.setText("~~~");
        jPanel6.add(FoodPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(573, 168, 501, -1));

        deleteButton.setBackground(new java.awt.Color(255, 102, 102));
        deleteButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        jPanel6.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 387, 263, 42));

        AvailabilityCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Available", "Not Available" }));
        AvailabilityCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AvailabilityComboActionPerformed(evt);
            }
        });
        jPanel6.add(AvailabilityCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 42, 126, -1));

        VendorID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        VendorID.setText("~~~");
        jPanel6.add(VendorID, new org.netbeans.lib.awtextra.AbsoluteConstraints(573, 94, 257, -1));

        runnerDashboardTitle2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        runnerDashboardTitle2.setText("Food Management");
        jPanel6.add(runnerDashboardTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 17, 454, -1));

        FoodPrice1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FoodPrice1.setText("Description");
        jPanel6.add(FoodPrice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 201, 779, -1));
        jPanel6.add(labelsetImageFood, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 45, 169, 139));

        FoodName1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FoodName1.setText("Food name :");
        jPanel6.add(FoodName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(476, 56, 91, -1));

        VendorID1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        VendorID1.setText("Vendor ID :");
        jPanel6.add(VendorID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(476, 94, 85, -1));

        FoodPrice2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FoodPrice2.setText("Price (MYR) : ");
        jPanel6.add(FoodPrice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(476, 168, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/images/1920x1080-white.jpg"))); // NOI18N
        jLabel14.setMaximumSize(new java.awt.Dimension(640, 550));
        jLabel14.setMinimumSize(new java.awt.Dimension(640, 550));
        jLabel14.setName(""); // NOI18N
        jPanel6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 620));

        jTabbedPanel.addTab("Items", jPanel6);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        comboComplaints.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "booking issues", "product services", "wait time", "delivery", "vendors", "runners", "system issues", "login issues", "sign up issues", "account problems" }));
        comboComplaints.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboComplaintsMouseClicked(evt);
            }
        });
        comboComplaints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboComplaintsActionPerformed(evt);
            }
        });
        jPanel7.add(comboComplaints, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 58, 151, -1));

        complaintsArea.setEditable(false);
        complaintsArea.setColumns(20);
        complaintsArea.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        complaintsArea.setLineWrap(true);
        complaintsArea.setRows(5);
        jScrollPane3.setViewportView(complaintsArea);

        jPanel7.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 420, 189));

        runnerDashboardTitle1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        runnerDashboardTitle1.setText("Feedback Management");
        jPanel7.add(runnerDashboardTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 15, 218, -1));

        FeedbackTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Complaint ID", "Username"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(FeedbackTable);

        jPanel7.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 90, 350, 295));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Complaints");
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, 103, -1));

        answerArea.setColumns(20);
        answerArea.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        answerArea.setLineWrap(true);
        answerArea.setRows(5);
        jScrollPane5.setViewportView(answerArea);

        jPanel7.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 330, 420, 178));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Answer");
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 300, 103, -1));

        resolveButton.setBackground(new java.awt.Color(0, 153, 0));
        resolveButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        resolveButton.setForeground(new java.awt.Color(255, 255, 255));
        resolveButton.setText("RESOLVE");
        resolveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resolveButtonActionPerformed(evt);
            }
        });
        jPanel7.add(resolveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 350, 39));

        comboActive.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All","Unresolve", "Resolved"}));
        comboActive.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboActiveMouseClicked(evt);
            }
        });
        comboActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboActiveActionPerformed(evt);
            }
        });
        jPanel7.add(comboActive, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 58, 190, -1));

        clearButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clearButton.setText("Clear Answer");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        jPanel7.add(clearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 350, 39));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/images/1920x1080-white.jpg"))); // NOI18N
        jLabel13.setMaximumSize(new java.awt.Dimension(640, 550));
        jLabel13.setMinimumSize(new java.awt.Dimension(640, 550));
        jLabel13.setName(""); // NOI18N
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 590));

        jTabbedPanel.addTab("Feedback", jPanel7);

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/images/1920x1080-white.jpg"))); // NOI18N
        jLabel15.setMaximumSize(new java.awt.Dimension(640, 550));
        jLabel15.setMinimumSize(new java.awt.Dimension(640, 550));
        jLabel15.setName(""); // NOI18N
        jTabbedPanel.addTab("tab8", jLabel15);

        getContentPane().add(jTabbedPanel);
        jTabbedPanel.setBounds(160, -30, 840, 610);

        itemsButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        itemsButton.setForeground(new java.awt.Color(16, 55, 86));
        itemsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-open-box-24.png"))); // NOI18N
        itemsButton.setText("Items");
        itemsButton.setBorder(null);
        itemsButton.setContentAreaFilled(false);
        itemsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        itemsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemsButtonActionPerformed(evt);
            }
        });
        getContentPane().add(itemsButton);
        itemsButton.setBounds(10, 180, 100, 30);

        revenueButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        revenueButton.setForeground(new java.awt.Color(16, 55, 86));
        revenueButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-revenue-32.png"))); // NOI18N
        revenueButton.setText("Revenue");
        revenueButton.setBorder(null);
        revenueButton.setContentAreaFilled(false);
        revenueButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        revenueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revenueButtonActionPerformed(evt);
            }
        });
        getContentPane().add(revenueButton);
        revenueButton.setBounds(20, 80, 110, 30);

        deliveryButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        deliveryButton.setForeground(new java.awt.Color(16, 55, 86));
        deliveryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-delivery-30.png"))); // NOI18N
        deliveryButton.setText("Runner");
        deliveryButton.setBorder(null);
        deliveryButton.setContentAreaFilled(false);
        deliveryButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deliveryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deliveryButtonActionPerformed(evt);
            }
        });
        getContentPane().add(deliveryButton);
        deliveryButton.setBounds(20, 130, 100, 30);

        feedbackButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        feedbackButton.setForeground(new java.awt.Color(16, 55, 86));
        feedbackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-feedback-24.png"))); // NOI18N
        feedbackButton.setText("Feedback");
        feedbackButton.setBorder(null);
        feedbackButton.setContentAreaFilled(false);
        feedbackButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        feedbackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedbackButtonActionPerformed(evt);
            }
        });
        getContentPane().add(feedbackButton);
        feedbackButton.setBounds(20, 230, 110, 30);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/images/1920x1080-white.jpg"))); // NOI18N
        jLabel12.setMaximumSize(new java.awt.Dimension(640, 550));
        jLabel12.setMinimumSize(new java.awt.Dimension(640, 550));
        jLabel12.setName(""); // NOI18N
        getContentPane().add(jLabel12);
        jLabel12.setBounds(-10, -10, 1080, 620);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
       
        this.dispose();
        Login login = new Login();
        login.setVisible(true);
        
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void itemsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemsButtonActionPerformed
       
        jTabbedPanel.setSelectedIndex(5);
        
        try {
            foodManager foodie = new foodManager(MENUINFO_FILE);
            JTable table = FoodTable;
            JComboBox<String> categoryComboBox = VendorCombo;
            JComboBox<String> answerComboBox = AvailabilityCombo;
            DefaultTableModel tableModel = (DefaultTableModel) FoodTable.getModel();
            
            foodController controller = new foodController(foodie, tableModel, FoodTable, DescArea, VendorID, FoodName, FoodPrice, labelsetImageFood);
           
            foodie.setVendorComboBox(VendorCombo);
            foodie.loadVendorIds();
            
              categoryComboBox.addActionListener(e -> {
                String category = (String) categoryComboBox.getSelectedItem();
                String answerStatus = (String) answerComboBox.getSelectedItem();
                controller.updateTable(category, answerStatus);
                foodie.updateTableData(tableModel, category, answerStatus);
                
            });

            answerComboBox.addActionListener(e -> {
                String category = (String) categoryComboBox.getSelectedItem();
                String answerStatus = (String) answerComboBox.getSelectedItem();
                controller.updateTable(category, answerStatus);
                foodie.updateTableData(tableModel, category, answerStatus);
            });
            table.getSelectionModel().addListSelectionListener(e -> controller.ShowFoodDesc());
            controller.updateTable("All", "All");
            deleteButton.addActionListener(e -> controller.deleteFoodItem());
        }
        
         catch (HeadlessException e) {
             System.out.println("Operation fail");
        }
        
        
       
    }//GEN-LAST:event_itemsButtonActionPerformed

    private void revenueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revenueButtonActionPerformed
         jTabbedPanel.setSelectedIndex(3);
          revenueManager activeRevenue = new revenueManager();
         formatManager useFormat = new formatManager();
         try {
         activeRevenue.getRevenueActive();
         String stringedrevenue = useFormat.formatNumber(activeRevenue.getTotalrevenue());
         totalRevenue.setText("MYR " + stringedrevenue);
         
         activeRevenue.getBestSeller();
         String stringedhighestrevenue = useFormat.formatNumber(activeRevenue.getHighestrevenue());
         vendorName.setText(activeRevenue.getUsername());
         highestRevenue.setText("MYR " + stringedhighestrevenue);
         
         activeRevenue.getVendorCount();
         String stringedvendercount = Integer.toString(activeRevenue.getVendorcount());
         recruitedVendors.setText("Total of " + stringedvendercount);

            try {
                
              DefaultTableModel tblModel = (DefaultTableModel) vendorTable.getModel();

                if (vendorTable.getRowCount() == 0) {
                tblModel.setColumnIdentifiers(new Object[]{"User ID", "Username", "Revenue", "Active Status"});
                activeRevenue.loadVendorsToTable(tblModel, null); 
                }
                else {
                    System.out.println("Table is listed!");
                }

            } catch (HeadlessException e) {
                  dialogManagerError.showErrorMessage("Error! Can't load the table, text file may be empty or corrupted.");
              }
          
         }
         
         catch (HeadlessException e) {
                dialogManagerError.showErrorMessage("Error! No vendors in text file, the text file may be corrupted");
         }
         
         ComboRevenue.addActionListener(e -> {
               DefaultTableModel tblModel = (DefaultTableModel) vendorTable.getModel();
                String vendoractive = (String) ComboRevenue.getSelectedItem();
                System.out.println("ComboRevenue function: " + vendoractive);
                
                if (vendoractive.equals("All")) {
                    activeRevenue.loadVendorsToTable(tblModel, null);
                }
                else if (vendoractive.equals("Active")) {
                    activeRevenue.loadVendorsToTable(tblModel, vendoractive);
                }
                else if (vendoractive.equals("Inactive")) {
                    activeRevenue.loadVendorsToTable(tblModel,  vendoractive);
                }
                else {
                     System.out.println("Something is wrong? please contact the IT guy");
                }
            });
         
         
          
         
         
         
    }//GEN-LAST:event_revenueButtonActionPerformed

    private void deliveryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deliveryButtonActionPerformed
         jTabbedPanel.setSelectedIndex(4);
         runnerManager activeRunner = new runnerManager();
         formatManager useFormat = new formatManager();
         runnerRating analyzer = new runnerRating();
         
         try {
            activeRunner .getRevenueActive();
            String stringedrevenue = useFormat.formatNumber(activeRunner.getTotalrevenue());
            totalRevenueRunner.setText("MYR " + stringedrevenue);

            analyzer.getShowBestRunner();
            analyzer.getUsername();
            bestRunnerName.setText(analyzer.getName());
            String goatrunner = useFormat.formatNumber(analyzer.getBestrated());
            averageRating.setText(goatrunner);
            averageRunnerRating.setText(analyzer.getAveragerating());
            totalRunners.setText("Total Runners: " +analyzer.getRowCount());
            
                try {
                    DefaultTableModel tblModel = (DefaultTableModel) runnerTable.getModel();

                    if (runnerTable.getRowCount() == 0) {
                        tblModel.setColumnIdentifiers(new Object[]{"User ID", "Username", "Rating", "Revenue"});
                        activeRunner.loadRunnersToTable(tblModel);
                    }
                    else {
                        System.out.println("Table is already listed");
                    }
                } catch (HeadlessException e) {
                    dialogManagerError.showErrorMessage("Error! Can't load the table, text file may be empty or corrupted.");
             }
         
         } 
         
         catch (HeadlessException e) {
                dialogManagerError.showErrorMessage("Error! No Runners in text file, the text file may be corrupted");
         }
         
          
         ComboRun.addActionListener(e -> {
               DefaultTableModel tblModel = (DefaultTableModel) runnerTable.getModel();
                String Runnersorting = (String) ComboRun.getSelectedItem();
                System.out.println("ComboRunner function: " + Runnersorting);
                
                if (Runnersorting.equals("All")) {
                     activeRunner.loadRunnersToTable(tblModel);
                }
                else {
                activeRunner.loadRunnersToTable2(tblModel, Runnersorting);
                }
            });
         
         ComboRating.addActionListener(e -> {
               DefaultTableModel tblModel = (DefaultTableModel) runnerTable.getModel();
                String Runnerrating = (String) ComboRating.getSelectedItem();
                System.out.println("ComboRating function: " + Runnerrating);
                
                if (Runnerrating.equals("All")) {
                     activeRunner.loadRunnersToTable(tblModel);
                }
                else {
                activeRunner.loadRunnersToTable3(tblModel, Runnerrating);
                }
            });
    }//GEN-LAST:event_deliveryButtonActionPerformed

    private void feedbackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedbackButtonActionPerformed
         jTabbedPanel.setSelectedIndex(6);

        try {
            complaintManager complaint = new complaintManager(COMPLAINTS_FILE);
            JTable table = FeedbackTable;
            JComboBox<String> categoryComboBox = comboComplaints;
            JComboBox<String> answerComboBox = comboActive;
            JTextArea complaintContentArea = complaintsArea;
            JTextArea replyArea = answerArea;
            DefaultTableModel tableModel = (DefaultTableModel) FeedbackTable.getModel();

            complaintController controller = new complaintController(complaint, tableModel, table, complaintContentArea, replyArea);

            categoryComboBox.addActionListener(e -> {
                String category = (String) categoryComboBox.getSelectedItem();
                String answerStatus = (String) answerComboBox.getSelectedItem();
                controller.updateTable(category, answerStatus);
            });

            answerComboBox.addActionListener(e -> {
                String category = (String) categoryComboBox.getSelectedItem();
                String answerStatus = (String) answerComboBox.getSelectedItem();
                controller.updateTable(category, answerStatus);
            });

            table.getSelectionModel().addListSelectionListener(e -> controller.showComplaintDetails());
            resolveButton.addActionListener(e -> controller.saveReply());

            controller.updateTable("All", "All"); }
        
        catch (HeadlessException e) {
                dialogManagerError.showErrorMessage("Error! No Complaints in text file, the text file may be corrupted");
         }
         
    }//GEN-LAST:event_feedbackButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        answerArea.setText(" null");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void comboActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboActiveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboActiveActionPerformed

    private void comboActiveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboActiveMouseClicked

    }//GEN-LAST:event_comboActiveMouseClicked

    private void resolveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resolveButtonActionPerformed

    }//GEN-LAST:event_resolveButtonActionPerformed

    private void comboComplaintsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboComplaintsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboComplaintsActionPerformed

    private void comboComplaintsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboComplaintsMouseClicked

    }//GEN-LAST:event_comboComplaintsMouseClicked

    private void AvailabilityComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AvailabilityComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AvailabilityComboActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed

    }//GEN-LAST:event_deleteButtonActionPerformed

    private void FoodTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FoodTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FoodTableMouseClicked

    private void ComboRatingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboRatingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboRatingActionPerformed

    private void ComboRevenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboRevenueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboRevenueActionPerformed

      
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
      
               /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new ManagerMenu().setVisible(true);
                    }
                });
          
        
    }
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AvailabilityCombo;
    private javax.swing.JComboBox<String> ComboRating;
    private javax.swing.JComboBox<String> ComboRevenue;
    private javax.swing.JComboBox<String> ComboRun;
    private javax.swing.JTextArea DescArea;
    private javax.swing.JTable FeedbackTable;
    private javax.swing.JLabel FoodName;
    private javax.swing.JLabel FoodName1;
    private javax.swing.JLabel FoodPrice;
    private javax.swing.JLabel FoodPrice1;
    private javax.swing.JLabel FoodPrice2;
    private javax.swing.JTable FoodTable;
    private javax.swing.JComboBox<String> VendorCombo;
    private javax.swing.JLabel VendorID;
    private javax.swing.JLabel VendorID1;
    private javax.swing.JLabel allVendors;
    private javax.swing.JLabel alltimeRunner;
    private javax.swing.JLabel alltimeRunner1;
    private javax.swing.JTextArea answerArea;
    private javax.swing.JLabel averageRating;
    private javax.swing.JLabel averageRatingOverall;
    private javax.swing.JLabel averageRunnerRating;
    private javax.swing.JLabel bestRatedtitle;
    private javax.swing.JLabel bestRunnerName;
    private foodhub.ChartPanelForm chartPanelForm1;
    private foodhub.ChartPanelFormRunner chartPanelFormRunner1;
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox<String> comboActive;
    private javax.swing.JComboBox<String> comboComplaints;
    private javax.swing.JTextArea complaintsArea;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton deliveryButton;
    private javax.swing.JButton feedbackButton;
    private javax.swing.JLabel highestRevenue;
    private javax.swing.JButton itemsButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPanel;
    private javax.swing.JLabel labelsetImageFood;
    private javax.swing.JButton logoutButton;
    private javax.swing.JLabel recruitedVendors;
    private javax.swing.JButton resolveButton;
    private javax.swing.JButton revenueButton;
    private javax.swing.JLabel runnerDashboardTitle;
    private javax.swing.JLabel runnerDashboardTitle1;
    private javax.swing.JLabel runnerDashboardTitle2;
    private javax.swing.JTable runnerTable;
    private javax.swing.JLabel runnerTotalRevenue;
    private javax.swing.JLabel totalRevenue;
    private javax.swing.JLabel totalRevenueRunner;
    private javax.swing.JLabel totalRunners;
    private javax.swing.JLabel vendorName;
    private javax.swing.JTable vendorTable;
    // End of variables declaration//GEN-END:variables
}
