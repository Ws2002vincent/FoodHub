package foodhub.runner;

import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RunnerViewTask extends javax.swing.JPanel {

    private String runnerID;
    private List<Task> taskList;
    private List<TaskBox> ordersList = new ArrayList<>();
    private TaskBox selectedOrder = null;
    private String orderHistoryFileName = "C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt";

    public RunnerViewTask(String runnerID) {
        initComponents();
        this.runnerID = runnerID;

        //Call the TaskLoader to load task from Orderhistory.txt
        taskList = TaskLoader.loadTask("C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt");
        newOrderPnl.setLayout(new BoxLayout(newOrderPnl, BoxLayout.Y_AXIS));

        // Get current date
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.now();
        String currentDate = date.format(format);

        try {
            //Get runner ID
            BufferedReader bf = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Runner.txt"));
            String line;
            String runnerName = "";

            while ((line = bf.readLine()) != null) {
                String[] dataline = line.split("\\|");

                if (dataline[0].trim().equals(runnerID)) {
                    runnerName = dataline[1].trim();
                    break;
                }
            }

            if (runnerName != null) {
                stallName.setText(runnerName + "'s Page");
            } else {
                stallName.setText("No name provided");
            }

        } catch (IOException ex) {
            Logger.getLogger(RunnerViewTask.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Set runner ID and current date to UI
        idLbl.setText(runnerID);
        dateLbl.setText(currentDate);

        try {
            BufferedReader bfr = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\RunnerAcceptance.txt"));
            String line;
            Set<String> rejectedOrders = new HashSet<>();

            // Read the file that store only rejected orders
            while ((line = bfr.readLine()) != null) {
                String[] acceptanceData = line.split("\\|");

                // Ensure valid data (avoid array out-of-bounds)
                if (acceptanceData.length < 4) {
                    continue;
                }

                String orderId = acceptanceData[1].trim(); // Order ID is in index 1
                String assignedRunner = acceptanceData[2].trim(); // Assigned runner is in index 2
                String status = acceptanceData[3].trim(); // Status is in index 3

                // Store only tasks rejected by this runner
                if (assignedRunner.equals(runnerID) && "Refuse".equalsIgnoreCase(status)) {
                    rejectedOrders.add(orderId);
                }
            }
            bfr.close();

            // Loop through taskList and add only non-rejected tasks
            for (Task task : taskList) {
                if (!rejectedOrders.contains(task.getOrderId())) {  // Load only if NOT rejected
                    TaskBox taskBox = new TaskBox(
                            task.getOrderId(),
                            task.getCustomer(),
                            task.getServiceType(),
                            task.getDateTime(),
                            task.getOrderedItems(),
                            task.getDeliveryFee(),
                            task.getAddress()
                    );

                    taskBox.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            if (selectedOrder != null) {
                                selectedOrder.setBorder(BorderFactory.createEmptyBorder());
                            }
                            selectedOrder = taskBox;
                            selectedOrder.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                        }
                    });

                    // Add to UI panel
                    ordersList.add(taskBox);
                    newOrderPnl.add(taskBox);
                    newOrderPnl.add(Box.createVerticalStrut(15));
                }
            }

            newOrderPnl.revalidate();
            newOrderPnl.repaint();

        } catch (IOException ex) {
            Logger.getLogger(RunnerViewTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Update task delivery acception
    private void updateDeliveryStatus(String orderID, boolean decision) {
        List<String> fileContent = new ArrayList<>();
        boolean orderFound = false;
        String booleanValue = String.valueOf(decision);

        try (BufferedReader br = new BufferedReader(new FileReader(this.orderHistoryFileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                // Trim spaces in each attribute
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim();
                }

                if (data[0].equals(orderID)) { // If orderID matches
                    orderFound = true;

                    // Check if the last index is "none"
                    if (data.length >= 11 && "none".equalsIgnoreCase(data[10].trim())) {
                        data[10] = booleanValue.trim(); // Update "none" to new decision
                    }

                    // Reconstruct the line
                    line = String.join(" | ", data);
                }

                fileContent.add(line);
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading file: " + e.getMessage());
        }

        // If orderID was not found, throw an exception
        if (!orderFound) {
            throw new IllegalArgumentException("Order ID not found: " + orderID);
        }

        // Update content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.orderHistoryFileName))) {
            for (String fileLine : fileContent) {
                writer.write(fileLine);
                writer.newLine();
            }
            System.out.println("✅ Order status updated successfully!");
        } catch (IOException e) {
            System.err.println("❌ Error writing file: " + e.getMessage());
        }
    }

    private static final String acceptanceFile = "C:\\FoodHub\\src\\foodhub\\Database\\RunnerAcceptance.txt";

    //Update the acceptance is runner refuse to accept the task
    private void updateAcceptance(String orderID, String runnerID) {
        String lastID = getLastAcceptanceID();
        String newID = generateNextID(lastID);

        String entry = newID + " | " + orderID + " | " + runnerID + " | Refuse";

        try (PrintWriter writer = new PrintWriter(new FileWriter(acceptanceFile, true))) {
            writer.flush(); // Forces immediate writing
            writer.write(entry + System.lineSeparator()); // Ensures the next entry starts on a new line
            System.out.println("Runner rejection recorded: " + entry);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    //Get the last id in the RunnerAcceptance.txt
    private String getLastAcceptanceID() {
        String lastLine = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(acceptanceFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return (lastLine != null) ? lastLine.split(" \\| ")[0] : "RA00"; // Default if file is empty
    }

    //Generate a new acceptance id to write into RunnerAcceptance.txt
    private String generateNextID(String lastID) {
        int lastNum = Integer.parseInt(lastID.substring(2)); // Extract number from "RAxx"
        return "RA" + String.format("%02d", lastNum + 1); // Increment and format as "RAxx"
    }

    private static final String taskFile = "C:\\FoodHub\\src\\foodhub\\Database\\Tasks.txt";

    //Update task into Tasks.txt if the runner accept the task
    private void updateTask(String runnerID, TaskBox taskBox) {
        String lastID = getLastTaskID();
        String newID = generateNextTaskID(lastID);

        String entry = newID + " | " + runnerID + " | " + taskBox.getOrderID() + " | " + taskBox.getCustomerName() + " | " + taskBox.getAddress() + " | " + "false" + " | " + "accepted";

        try (PrintWriter writer = new PrintWriter(new FileWriter(taskFile, true))) {
            writer.flush(); // Forces immediate writing
            writer.write(entry + System.lineSeparator()); // Ensures the next entry starts on a new line
            System.out.println("Runner rejection recorded: " + entry);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    //Get the last id in the Tasks.txt
    private String getLastTaskID() {
        String lastLine = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return (lastLine != null) ? lastLine.split(" \\| ")[0] : "RA00"; // Default if file is empty
    }

    //Generate a new acceptance id to write into RunnerAcceptance.txt
    private String generateNextTaskID(String lastID) {
        int lastNum = Integer.parseInt(lastID.substring(2)); // Extract number from "RAxx"
        return "T" + String.format("%02d", lastNum + 1); // Increment and format as "RAxx"
    }

    //Remove the task from the UI panel
    private void removeOrderFromPanel(JPanel panel, TaskBox taskBox) {
        int index = panel.getComponentZOrder(taskBox);
        if (index != -1) {
            panel.remove(taskBox);

            // Remove the associated vertical strut if it exists
            if (index < panel.getComponentCount()) {
                Component nextComponent = panel.getComponent(index);
                if (nextComponent instanceof Box.Filler) {
                    panel.remove(nextComponent);
                }
            }

            panel.revalidate();
            panel.repaint();
        }
    }

    //Decline Customer Delivery Task
    public void declineTask(TaskBox taskBox) {
        taskBox.setBorder(null);
        removeOrderFromPanel(newOrderPnl, taskBox);
        updateDeliveryStatus(taskBox.getOrderID(), false);
        updateAcceptance(taskBox.getOrderID(), runnerID);

        selectedOrder = null; // Reset selection
    }

    //Accept Customer Delivery Task
    public void acceptTask(TaskBox taskBox) {
        taskBox.setBorder(null);
        removeOrderFromPanel(newOrderPnl, taskBox);
        updateDeliveryStatus(taskBox.getOrderID(), true);
        updateTask(runnerID, taskBox);

        selectedOrder = null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        headerPnl = new javax.swing.JPanel();
        dateLbl = new javax.swing.JLabel();
        stallLbl = new javax.swing.JLabel();
        idLbl = new javax.swing.JLabel();
        stallName = new javax.swing.JLabel();
        orderLbl = new javax.swing.JLabel();
        orderPnl = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        newOrderPnl = new javax.swing.JPanel();
        buttonPnl = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        declineBtn = new javax.swing.JButton();
        acceptBtn = new javax.swing.JButton();
        statusPnl = new javax.swing.JPanel();
        newLbl = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        headerPnl.setBackground(new java.awt.Color(255, 255, 255));

        dateLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        dateLbl.setText("dd-MM-yyyy");

        stallLbl.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        stallLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        stallLbl.setText("Runner ID:");

        idLbl.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        idLbl.setText("0");

        stallName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        stallName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        stallName.setText("Runner");

        orderLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        orderLbl.setText("Tasks for");

        javax.swing.GroupLayout headerPnlLayout = new javax.swing.GroupLayout(headerPnl);
        headerPnl.setLayout(headerPnlLayout);
        headerPnlLayout.setHorizontalGroup(
            headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPnlLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerPnlLayout.createSequentialGroup()
                        .addComponent(stallLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(stallName, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 380, Short.MAX_VALUE)
                .addComponent(orderLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateLbl)
                .addGap(25, 25, 25))
        );
        headerPnlLayout.setVerticalGroup(
            headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stallName)
                    .addComponent(orderLbl)
                    .addComponent(dateLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(headerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stallLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(idLbl))
                .addContainerGap())
        );

        orderPnl.setBackground(new java.awt.Color(255, 255, 255));
        orderPnl.setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        newOrderPnl.setBackground(new java.awt.Color(255, 255, 255));
        newOrderPnl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout newOrderPnlLayout = new javax.swing.GroupLayout(newOrderPnl);
        newOrderPnl.setLayout(newOrderPnlLayout);
        newOrderPnlLayout.setHorizontalGroup(
            newOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 951, Short.MAX_VALUE)
        );
        newOrderPnlLayout.setVerticalGroup(
            newOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(newOrderPnl);
        taskList = TaskLoader.loadTask("C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt");

        orderPnl.add(jScrollPane1);

        buttonPnl.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        declineBtn.setBackground(new java.awt.Color(246, 214, 214));
        declineBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        declineBtn.setText("Decline");
        declineBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        declineBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                declineBtnActionPerformed(evt);
            }
        });
        jPanel2.add(declineBtn);

        acceptBtn.setBackground(new java.awt.Color(223, 246, 221));
        acceptBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        acceptBtn.setText("Accept");
        acceptBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        acceptBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptBtnActionPerformed(evt);
            }
        });
        jPanel2.add(acceptBtn);

        buttonPnl.add(jPanel2);

        statusPnl.setBackground(new java.awt.Color(255, 255, 255));
        statusPnl.setLayout(new java.awt.GridLayout(1, 0));

        newLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        newLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newLbl.setText("All Available Task");
        statusPnl.add(newLbl);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(orderPnl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(statusPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 955, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 461, Short.MAX_VALUE)
                .addComponent(buttonPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(157, Short.MAX_VALUE)
                    .addComponent(orderPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(99, 99, 99)
                    .addComponent(statusPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(431, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void acceptBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptBtnActionPerformed
        //Check if an task has been selected
        if (selectedOrder != null) {
            // Check if the selected task is in the panel
            if (newOrderPnl.isAncestorOf(selectedOrder)) {
                acceptTask(selectedOrder);
                JOptionPane.showMessageDialog(this, "Task Accepted!");
            } else {
                JOptionPane.showMessageDialog(this, "Selected task is not in the New Tasks section.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to accept.");
        }
    }//GEN-LAST:event_acceptBtnActionPerformed

    private void declineBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_declineBtnActionPerformed
        //Check if an task has been selected
        if (selectedOrder != null) {
            //Check if the selected task is in the panel
            if (newOrderPnl.isAncestorOf(selectedOrder)) {
                declineTask(selectedOrder);
                JOptionPane.showMessageDialog(this, "Task Declined!");
            } else {
                JOptionPane.showMessageDialog(this, "Selected task is not in the New Tasks section.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to decline.");
        }
    }//GEN-LAST:event_declineBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptBtn;
    private javax.swing.JPanel buttonPnl;
    private javax.swing.JLabel dateLbl;
    private javax.swing.JButton declineBtn;
    private javax.swing.JPanel headerPnl;
    private javax.swing.JLabel idLbl;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel newLbl;
    private javax.swing.JPanel newOrderPnl;
    private javax.swing.JLabel orderLbl;
    private javax.swing.JPanel orderPnl;
    private javax.swing.JLabel stallLbl;
    private javax.swing.JLabel stallName;
    private javax.swing.JPanel statusPnl;
    // End of variables declaration//GEN-END:variables
}
