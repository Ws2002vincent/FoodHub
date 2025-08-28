package foodhub.runner;

import foodhub.runner.MyTask;
import foodhub.runner.MyTaskBox;
import foodhub.runner.MyTaskLoader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RunnerMyTask extends javax.swing.JPanel {

    private String runnerID;
    private List<MyTask> myTaskList;
    private List<MyTaskBox> myTasksList = new ArrayList<>();
    private MyTaskBox selectedTask = null;

    public RunnerMyTask(String runnerID) {
        initComponents();
        this.runnerID = runnerID;

        myTaskList = MyTaskLoader.loadTask("C:\\FoodHub\\src\\foodhub\\Database\\Tasks.txt", runnerID);
        newOrderPnl.setLayout(new BoxLayout(newOrderPnl, BoxLayout.Y_AXIS));
        preparingOrderPnl.setLayout(new BoxLayout(preparingOrderPnl, BoxLayout.Y_AXIS));

        for (MyTask myTask : myTaskList) {
            MyTaskBox myTaskBox = new MyTaskBox(
                    myTask.getTaskID(),
                    myTask.getCustomerName(),
                    myTask.getAddress(),
                    myTask.getOrderedItems(),
                    myTask.getOrderID()
            );

            myTaskBox.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (selectedTask != null) {
                        selectedTask.setBorder(BorderFactory.createEmptyBorder());
                    }
                    selectedTask = myTaskBox;
                    selectedTask.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
            });

            String status = myTask.getStatus().trim();

            switch (status) {
                case "accepted" -> {
                    myTasksList.add(myTaskBox);
                    newOrderPnl.add(myTaskBox);
                    newOrderPnl.add(Box.createVerticalStrut(15));
                }
                case "picked-up" -> {
                    preparingOrderPnl.add(myTaskBox);
                    preparingOrderPnl.add(Box.createVerticalStrut(15));
                }
            }

            newOrderPnl.revalidate();
            newOrderPnl.repaint();
            preparingOrderPnl.revalidate();
            preparingOrderPnl.repaint();
        }
    }

    // Moves an OrderBox from one panel to another, including its spacing
    private void moveOrderBox(JPanel sourcePanel, JPanel targetPanel, MyTaskBox myTaskBox) {
        if (myTaskBox == null) {
            return;
        }
        // Remove from source panel
        removeOrderFromPanel(sourcePanel, myTaskBox);

        // Add to target panel
        targetPanel.add(myTaskBox);
        targetPanel.add(Box.createVerticalStrut(15));

        sourcePanel.revalidate();
        sourcePanel.repaint();
        targetPanel.revalidate();
        targetPanel.repaint();
    }

    private void removeOrderFromPanel(JPanel panel, MyTaskBox myTaskBox) {
        int index = panel.getComponentZOrder(myTaskBox);
        if (index != -1) {
            panel.remove(myTaskBox);

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

    private void updateTaskStatusInFile(String taskID, String newStatus) {
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Tasks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].trim().equals(taskID)) {
                    data[6] = newStatus;
                }
                // Join with exactly one space on each side of the pipe
                StringBuilder formattedLine = new StringBuilder();
                formattedLine.append(data[0].trim());
                for (int i = 1; i < data.length; i++) {
                    formattedLine.append(" | ").append(data[i].trim());
                }
                fileContent.add(formattedLine.toString());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\FoodHub\\src\\foodhub\\Database\\Tasks.txt"))) {
            for (String fileLine : fileContent) {
                writer.write(fileLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    private void updateTaskStatusInFile(String taskID, String newDecision, String newStatus) {
        List<String> fileContent = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String currentDate = dateTime.format(format);

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\FoodHub\\src\\foodhub\\Database\\Tasks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].trim().equals(taskID)) {
                    data[5] = newDecision;
                    data[6] = newStatus;

                    // Format with consistent single spaces
                    StringBuilder formattedLine = new StringBuilder();
                    formattedLine.append(data[0].trim());
                    for (int i = 1; i < data.length; i++) {
                        formattedLine.append(" | ").append(data[i].trim());
                    }

                    // Add date if needed
                    if (data.length < 10) {
                        formattedLine.append(" | ").append(currentDate);
                    }
                    fileContent.add(formattedLine.toString());
                } else {
                    // Format existing lines consistently
                    StringBuilder formattedLine = new StringBuilder();
                    formattedLine.append(data[0].trim());
                    for (int i = 1; i < data.length; i++) {
                        formattedLine.append(" | ").append(data[i].trim());
                    }
                    fileContent.add(formattedLine.toString());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\FoodHub\\src\\foodhub\\Database\\Tasks.txt"))) {
            for (String fileLine : fileContent) {
                writer.write(fileLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    private void updateFee(String orderID) {
        String tasksFilePath = "C:\\FoodHub\\src\\foodhub\\Database\\Tasks.txt";
        String runnerFeeFilePath = "C:\\FoodHub\\src\\foodhub\\Database\\Runnerfee.txt";
        String orderHistoryPath = "C:\\FoodHub\\src\\foodhub\\Database\\Orderhistory.txt";

        // Use LocalDateTime instead of LocalDate
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now(); // Now includes both date and time
        String currentDate = dateTime.format(format);

        Map<String, String> deliveryFeeMap = loadDeliveryFee(orderHistoryPath);

        try (BufferedReader br = new BufferedReader(new FileReader(tasksFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length < 3) {
                    continue; // Ensure it has enough columns
                }

                String runnerID = data[1].trim();
                String OrderID = data[2].trim();

                if (OrderID.equals(orderID)) {
                    String deliveryFee = deliveryFeeMap.getOrDefault(orderID, "5.0");

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(runnerFeeFilePath, true))) {
                        writer.write(runnerID + " | " + deliveryFee + " | " + currentDate);
                        writer.newLine();
                    } catch (IOException e) {
                        System.err.println("Error writing to Runnerfee file: " + e.getMessage());
                    }
                    return; // Exit loop once task is found and updated
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Tasks file: " + e.getMessage());
        }
    }

    private static Map<String, String> loadDeliveryFee(String orderHistoryPath) {
        Map<String, String> deliveryFee = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(orderHistoryPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length >= 6) {
                    String orderID = data[1].trim();
                    String DeliveryFee = data[6].trim();
                    deliveryFee.put(orderID, DeliveryFee);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading customer info file: " + e.getMessage());
        }
        return deliveryFee;
    }

    private String generateNextNotificationID(String filePath) {
        String lastID = "RN0";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                lastID = data[0].trim(); // Get the first column (ID)
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Extract the numeric part of the ID and increment it
        int nextIDNumber = Integer.parseInt(lastID.substring(2)) + 1;
        return "RN" + nextIDNumber;
    }

    private void addNotification(String customerName, String orderID, String status) {
        String notificationFile = "C:\\FoodHub\\src\\foodhub\\Database\\RunnerNotification.txt";
        String notificationId = generateNextNotificationID(notificationFile);
        String runnerID = this.runnerID;
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mma"));

        // Display message based on status
        String customMessage = switch (status) {
            case "Cancelled" ->
                String.format("We're sorry! Your delivery order %s has been cancelled. Please contact our customer service for more information.", orderID);
            case "Picked-up" ->
                String.format("Your delivery order %s has being picked-up. Thank you for your patience!", orderID);
            case "Completed" ->
                String.format("Thank you! Your delivery order %s has been successfully delivered.", orderID);
            default ->
                String.format("The status of your order %s has changed to %s.", orderID, status);
        };

        String notification = String.format("%s | %s | %s | %s | %s",
                notificationId, runnerID, customerName, customMessage, currentDateTime);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(notificationFile, true))) {
            writer.write(notification);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing notification: " + e.getMessage());
        }
    }

    // Move the order box to the Ready panel
    public void markTaskPicked(MyTaskBox myTaskBox) {
        myTaskBox.setBorder(null);
        moveOrderBox(newOrderPnl, preparingOrderPnl, myTaskBox);
        updateTaskStatusInFile(myTaskBox.getTaskID(), "picked-up");
        addNotification(myTaskBox.getCustomerName(), myTaskBox.getOrderID(), "Picked-up");

        selectedTask = null;
    }

    // Remove the order box from Ready panel
    public void markTaskComplete(MyTaskBox myTaskBox) {
        myTaskBox.setBorder(null);
        removeOrderFromPanel(preparingOrderPnl, myTaskBox);
        updateTaskStatusInFile(myTaskBox.getTaskID(), "true", "completed");
        updateFee(myTaskBox.getOrderID());
        addNotification(myTaskBox.getCustomerName(), myTaskBox.getOrderID(), "Completed");

        selectedTask = null;
    }

    public void cancelTask(MyTaskBox myTaskBox) {
        myTaskBox.setBorder(null);
        removeOrderFromPanel(preparingOrderPnl, myTaskBox);
        removeOrderFromPanel(newOrderPnl, myTaskBox);
        updateTaskStatusInFile(myTaskBox.getTaskID(), "false", "Cancelled");
        addNotification(myTaskBox.getCustomerName(), myTaskBox.getOrderID(), "Cancelled");

        selectedTask = null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        orderPnl = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        newOrderPnl = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        preparingOrderPnl = new javax.swing.JPanel();
        buttonPnl = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pickedBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        completedBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        statusPnl = new javax.swing.JPanel();
        pickupLbl = new javax.swing.JLabel();
        deliveringLbl = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        orderPnl.setBackground(new java.awt.Color(255, 255, 255));
        orderPnl.setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        newOrderPnl.setBackground(new java.awt.Color(255, 255, 255));
        newOrderPnl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout newOrderPnlLayout = new javax.swing.GroupLayout(newOrderPnl);
        newOrderPnl.setLayout(newOrderPnlLayout);
        newOrderPnlLayout.setHorizontalGroup(
            newOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );
        newOrderPnlLayout.setVerticalGroup(
            newOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(newOrderPnl);

        orderPnl.add(jScrollPane1);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        preparingOrderPnl.setBackground(new java.awt.Color(255, 255, 255));
        preparingOrderPnl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout preparingOrderPnlLayout = new javax.swing.GroupLayout(preparingOrderPnl);
        preparingOrderPnl.setLayout(preparingOrderPnlLayout);
        preparingOrderPnlLayout.setHorizontalGroup(
            preparingOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );
        preparingOrderPnlLayout.setVerticalGroup(
            preparingOrderPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(preparingOrderPnl);

        orderPnl.add(jScrollPane2);

        buttonPnl.setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.CardLayout());

        pickedBtn.setBackground(new java.awt.Color(187, 222, 251));
        pickedBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pickedBtn.setText("Order Picked");
        pickedBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pickedBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pickedBtnActionPerformed(evt);
            }
        });
        jPanel1.add(pickedBtn, "card2");

        buttonPnl.add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.CardLayout());

        completedBtn.setBackground(new java.awt.Color(255, 204, 128));
        completedBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        completedBtn.setText("Delivering Completed");
        completedBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        completedBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completedBtnActionPerformed(evt);
            }
        });
        jPanel2.add(completedBtn, "card2");

        buttonPnl.add(jPanel2);

        jPanel3.setLayout(new java.awt.CardLayout());

        cancelBtn.setBackground(new java.awt.Color(255, 102, 102));
        cancelBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelBtn.setText("Cancel Task");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        jPanel3.add(cancelBtn, "card2");

        statusPnl.setBackground(new java.awt.Color(255, 255, 255));
        statusPnl.setLayout(new java.awt.GridLayout(1, 0));

        pickupLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        pickupLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pickupLbl.setText("On-Pickup");
        statusPnl.add(pickupLbl);

        deliveringLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        deliveringLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deliveringLbl.setText("Delivering");
        statusPnl.add(deliveringLbl);

        jButton1.setBackground(new java.awt.Color(204, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(buttonPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(orderPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 937, Short.MAX_VALUE)
            .addComponent(statusPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statusPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orderPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pickedBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pickedBtnActionPerformed
        if (selectedTask != null) {
            if (newOrderPnl.isAncestorOf(selectedTask)) {
                markTaskPicked(selectedTask);
                JOptionPane.showMessageDialog(this, "Task has been picked!");
            } else {
                JOptionPane.showMessageDialog(this, "Selected task is not in the pick-up section.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to pick-up.");
        }
    }//GEN-LAST:event_pickedBtnActionPerformed

    private void completedBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedBtnActionPerformed
        if (selectedTask != null) {
            if (preparingOrderPnl.isAncestorOf(selectedTask)) {
                markTaskComplete(selectedTask);
                JOptionPane.showMessageDialog(this, "Task marked as Completed!");
            } else {
                JOptionPane.showMessageDialog(this, "Selected task is not in the delivering section.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as Completed.");
        }
    }//GEN-LAST:event_completedBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        if (selectedTask != null) {
            if (newOrderPnl.isAncestorOf(selectedTask) || preparingOrderPnl.isAncestorOf(selectedTask)) {
                cancelTask(selectedTask);
                JOptionPane.showMessageDialog(this, "Task has been cancelled!");
            } else {
                JOptionPane.showMessageDialog(this, "Selected task is not in the pick-up section.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to cancel.");
        }
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        refreshTaskListUI();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void refreshTaskListUI() {
        newOrderPnl.removeAll();
        preparingOrderPnl.removeAll();

        myTaskList = MyTaskLoader.loadTask("C:\\FoodHub\\src\\foodhub\\Database\\Tasks.txt", this.runnerID);

        for (MyTask myTask : myTaskList) {
            MyTaskBox myTaskBox = new MyTaskBox(
                    myTask.getTaskID(),
                    myTask.getCustomerName(),
                    myTask.getAddress(),
                    myTask.getOrderedItems(),
                    myTask.getOrderID()
            );

            myTaskBox.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (selectedTask != null) {
                        selectedTask.setBorder(BorderFactory.createEmptyBorder());
                    }
                    selectedTask = myTaskBox;
                    selectedTask.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
            });

            String status = myTask.getStatus().trim();

            switch (status) {
                case "accepted" -> {
                    myTasksList.add(myTaskBox);
                    newOrderPnl.add(myTaskBox);
                    newOrderPnl.add(Box.createVerticalStrut(15));
                }
                case "picked-up" -> {
                    preparingOrderPnl.add(myTaskBox);
                    preparingOrderPnl.add(Box.createVerticalStrut(15));
                }
            }
        }

        newOrderPnl.revalidate();
        newOrderPnl.repaint();
        preparingOrderPnl.revalidate();
        preparingOrderPnl.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPnl;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton completedBtn;
    private javax.swing.JLabel deliveringLbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel newOrderPnl;
    private javax.swing.JPanel orderPnl;
    private javax.swing.JButton pickedBtn;
    private javax.swing.JLabel pickupLbl;
    private javax.swing.JPanel preparingOrderPnl;
    private javax.swing.JPanel statusPnl;
    // End of variables declaration//GEN-END:variables
}
