/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package foodhub;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartPanel;


/**
 *
 * @author SaponyCatty
 */
public class AdminMenu extends javax.swing.JFrame {

   
    public AdminMenu() {
        initComponents();
        setLocationRelativeTo(null);
        
        // set window picture      
        java.net.URL imageURL = getClass().getResource("/foodhub/images/Foodhub.png");
        ImageIcon image = new ImageIcon(imageURL);
        setIconImage(image.getImage());
    }

    UserManager UserManager = new UserManager(FilePaths.USER_FILE);
    UserManager CustomerManager = new UserManager(FilePaths.CUSTOMER_FILE);
    UserManager VendorManager = new UserManager(FilePaths.VENDOR_FILE);
    UserManager RunnerManager = new UserManager(FilePaths.RUNNER_FILE);

    private static final String IMAGES_FOLDER = "C:\\FoodHub\\src\\foodhub\\images\\";


    private javax.swing.Icon loadProfileIcon(String picFilename) {
        // 1) Handle null or blank filename
        if (picFilename == null || picFilename.isBlank()) {
            picFilename = "default.png";
        }

        // 2) Build full path
        String fullPath = IMAGES_FOLDER + picFilename.trim();
        java.io.File imgFile = new java.io.File(fullPath);

        // 3) Fallback if file does not exist
        if (!imgFile.exists()) {
            System.out.println("Image not found: " + fullPath + ". Using default.png.");
            fullPath = IMAGES_FOLDER + "default.png";
        }

        // 4) Load and scale
        java.awt.Image img = new javax.swing.ImageIcon(fullPath).getImage();
        java.awt.Image scaledImg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        return new javax.swing.ImageIcon(scaledImg);
    }

    private void loadUsersIntoTable(UserManager UserManager, javax.swing.JTable targetTable) {
        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) targetTable.getModel();
        model.setRowCount(0);  // Clear existing rows

        List<User> userList = UserManager.getAllUsers(); // This should return pure 'User' objects

        for (User user : userList) {
            // Load the profile icon via the helper
            javax.swing.Icon profileIcon = loadProfileIcon(user.getProfilePic());

            // Construct the row data specific to 'User'
            Object[] rowData = new Object[]{
                user.getUserID(),
                user.getRoleID(),
                user.getUsername(),
                user.getPhone(),
                user.getRole(),
                profileIcon,
                null,  // Edit placeholder
                null   // Delete placeholder
            };

            model.addRow(rowData);
        }
    }

    private void loadCustomersIntoTable(UserManager CustomerManager, javax.swing.JTable targetTable) {
        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) targetTable.getModel();
        model.setRowCount(0);

        // This should ideally return only Customer objects
        List<CustomerModel> customerList = UserManager.getAllCustomers();

        for (CustomerModel c : customerList) {
            javax.swing.Icon profileIcon = loadProfileIcon(c.getProfilePic());

            // The columns for Customers might differ (ID, username, credit, street, etc.)
            Object[] rowData = new Object[]{
                c.getCustomerID(),
                c.getUsername(),
                c.getCredit(),
                c.getStreet(),
                c.getPostcode(),
                c.getState(),
                profileIcon,
                null, // Edit
                null  // Delete
            };
            model.addRow(rowData);
        }
    }

    private void loadVendorsIntoTable(UserManager vendorManager, javax.swing.JTable targetTable) {
        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) targetTable.getModel();
        model.setRowCount(0);

        // This should return only Vendor objects
        List<Vendor> vendorList = vendorManager.getAllVendors();

        for (Vendor v : vendorList) {
            javax.swing.Icon profileIcon = loadProfileIcon(v.getProfilePic());

            Object[] rowData = new Object[]{
                v.getVendorID(),
                v.getUsername(),
                v.getStallName(),
                v.getRevenue(),
                v.getAvailable(),
                profileIcon,
                null, // Edit
                null  // Delete
            };
            model.addRow(rowData);
        }
    }

    
    private void loadRunnersIntoTable(UserManager runnerManager, javax.swing.JTable targetTable) {
        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) targetTable.getModel();
        model.setRowCount(0);

        // Return only Runner objects
        List<Runner> runnerList = runnerManager.getAllRunners();

        for (Runner r : runnerList) {
            javax.swing.Icon profileIcon = loadProfileIcon(r.getProfilePic());

            Object[] rowData = new Object[]{
                r.getRunnerID(),
                r.getUsername(),
                r.getRevenue(),
                profileIcon,
                null, // Edit
                null  // Delete
            };
            model.addRow(rowData);
        }
    }
    
    private void loadPaymentsIntoTable(javax.swing.JTable targetTable) {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) targetTable.getModel();
        model.setRowCount(0); // Clear table before adding new data

        String filePath = FilePaths.PAYMENT_FILE; // Ensure FilePaths.PAYMENT_FILE points to Payment.txt

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length == 5) {
                    String paymentID = parts[0].trim();
                    String username = parts[1].trim();
                    String amount = parts[2].trim();
                    String dateTime = parts[3].trim(); // "19-02-2025 03:15:53am"
                    String status = parts[4].trim();

                    // Split Date and Time
                    String[] dateTimeParts = dateTime.trim().split("\\s+"); // Trim & split by any space(s)

                    String date = dateTimeParts[0]; // Always exists

                    String time;
                    if (dateTimeParts.length == 3) {
                        time = dateTimeParts[1] + " " + dateTimeParts[2]; // Includes AM/PM
                    } else if (dateTimeParts.length == 2) {
                        time = dateTimeParts[1]; // Just HH:MM:SS
                    } else {
                        time = "Invalid Time Format"; // Error handling
                    }


                    Object[] rowData = new Object[]{paymentID, username, amount, date, time, status, "Accept", "Decline"};
                    model.addRow(rowData);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Payment.txt");
            e.printStackTrace();
        }
    }

    public class PaymentButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private final JTable table;
        private String label;
        private int row;
        private final String adminID; // Admin ID from login

        public PaymentButtonEditor(JCheckBox checkBox, JTable table, String buttonLabel, String adminID) {
            super(checkBox);
            this.table = table;
            this.button = new JButton(buttonLabel);
            this.button.setOpaque(true);
            this.adminID = adminID; // Capture logged-in admin ID

            button.addActionListener(e -> {
                fireEditingStopped(); // Stop editing

                if ("Accept".equals(buttonLabel)) {
                    handleAccept();
                } else if ("Decline".equals(buttonLabel)) {
                    handleDecline();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            this.label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

            private void handleAccept() {
                disableButtons();    
                updatePaymentStatus("Accepted");
            }

            private void handleDecline() {
                disableButtons();
                updatePaymentStatus("Declined");
            }
            
            private void disableButtons() {
                Component[] components = table.getParent().getComponents();
                for (Component component : components) {
                    if (component instanceof JButton) {
                        component.setEnabled(false);
                        component.setBackground(new Color(211, 211, 211)); // Light gray background for disabled buttons
                        component.setForeground(Color.DARK_GRAY); // Dark gray text color for disabled buttons
                    }
                }
            }

            private void updatePaymentStatus(String newStatus) {
                String paymentID = table.getValueAt(row, 0).toString();
                String username = table.getValueAt(row, 1).toString();
                String currentStatus = table.getValueAt(row, 5).toString();

                if (newStatus.equals(currentStatus)) {
                    JOptionPane.showMessageDialog(null, "Payment is already marked as " + newStatus, "No Changes Made", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Update status in table
                table.setValueAt(newStatus, row, 5);

                // Update status in Payment.txt
                String filePath = FilePaths.PAYMENT_FILE;
                List<String> updatedLines = new ArrayList<>();

                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(" \\| ");
                        if (parts.length == 5 && parts[0].trim().equals(paymentID)) {
                            parts[4] = newStatus; // Update status
                            line = String.join(" | ", parts);
                        }
                        updatedLines.add(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Write back updated content
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                    for (String updatedLine : updatedLines) {
                        bw.write(updatedLine);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                sendNotification(newStatus, paymentID, username);

                JOptionPane.showMessageDialog(null, "Payment " + paymentID + " marked as " + newStatus, "Status Updated", JOptionPane.INFORMATION_MESSAGE);
            }

            private void sendNotification(String newStatus, String paymentID, String username) {
                String notificationFilePath = FilePaths.NOTIFICATION_FILE;
                List<String> updatedNotifications = new ArrayList<>();
                boolean notificationExists = false;

                // Read existing notifications to check for duplicates
                try (BufferedReader br = new BufferedReader(new FileReader(notificationFilePath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.contains("Payment Status") && line.contains(paymentID)) {
                            notificationExists = true;
                            String[] parts = line.split(" \\| ");
                            parts[4] = "Your payment has been " + newStatus.toLowerCase() + ".";
                            line = String.join(" | ", parts);
                        }
                        updatedNotifications.add(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // If no notification exists, create a new one
                if (!notificationExists) {
                    String notificationID = IdGenerator.getNextRoleID("N", FilePaths.NOTIFICATION_FILE);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mma");
                    String timestamp = LocalDateTime.now().format(formatter);
                    String notificationEntry = notificationID + " | " + adminID + " | " + username + " | Payment Status | " +
                            "Your payment has been " + newStatus.toLowerCase() + "." + " | " + timestamp + " | Unread";
                    updatedNotifications.add(notificationEntry);
                }

                // Write back notifications
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(notificationFilePath))) {
                    for (String updatedNotification : updatedNotifications) {
                        bw.write(updatedNotification);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();

            }
        }
    } 
        
    private List<String> getAllOrderIDs() {
        List<String> orderIDs = new ArrayList<>();
        String filePath = FilePaths.ORDERHISTORY_FILE; 

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length > 0) {
                    orderIDs.add(parts[0]); // Order ID is at index 0
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Orderhistory.txt");
            e.printStackTrace();
        }

        return orderIDs;
    }

    private void loadTransactionsIntoTable(javax.swing.JTable targetTable) {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) targetTable.getModel();
        model.setRowCount(0); // Clear table before adding new data

        // Get transactions from Orderhistory.txt using receiptManager
        List<String> orderIDs = getAllOrderIDs(); // Fetch all order IDs

        for (String orderID : orderIDs) {
            receiptManager receipt = new receiptManager(orderID); // Get receipt details

            Object[] rowData = new Object[]{
                receipt.getOrderID(),    // ID
                receipt.getCustomerName(), // Username (Customer Name)
                receipt.getPaidTo(),    // Vendor Name
                receipt.getOrderType(), // Order Type (Dine-in, Takeaway)
                receipt.getPaymentDate(), // Date
                receipt.getPaymentTime(), // Time
                receipt.getTotalAmount(), // Amount
                receipt.getPaymentStatus(), // Status
                "Generate Receipt"  // Button for generating a receipt
            };
            model.addRow(rowData);
        }
    }


        
        
    public class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer(String label) {
            setText(label);
            setOpaque(true);
            setBackground(Color.LIGHT_GRAY);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setForeground(Color.BLACK);
            if (isSelected) {
//                setBackground(new Color(255, 200, 150)); // Light orange (not nice)
            } else {
                setBackground(Color.LIGHT_GRAY);
            }
            return this;
        }
    }

        private String codeToRole(String code) {
            switch (code) {
                case "1": return "Admin";
                case "4": return "Customer";
                case "2": return "Vendor";
                case "3": return "Runner";
                case "0": return "Manager";
                default : return "Unknown";
            }
        }

        private String roleToCode(String text) {
            switch (text) {
                case "Admin":    return "1";
                case "Customer": return "4";
                case "Vendor":   return "2";
                case "Runner":   return "3";
                case "Manager":  return "0";  // <-- And here
                default:         return "Undefined Role";
            }
        }

        public class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private String label;
        private int row;
        private final JTable table;

        // Human-readable role labels
        private final String[] roleOptions = {"Manager", "Admin", "Customer", "Vendor", "Runner"};

        public ButtonEditor(JCheckBox checkBox, JTable table, String buttonLabel) {
            
            super(checkBox);
            this.table = table;
            this.button = new JButton(buttonLabel);
            this.button.setOpaque(true);

            button.addActionListener(e -> {
                fireEditingStopped(); // Stop editing mode

                if ("Edit".equals(buttonLabel)) {
                    // --- EDIT LOGIC ---
                    handleEdit();
                } else if ("Delete".equals(buttonLabel)) {
                    // --- DELETE LOGIC ---
                    handleDelete();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, 
                boolean isSelected, int row, int column) {

            this.row = row;
            this.label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

        private void handleEdit() {
            // Get Global ID and Role ID from the table
            String globalID = table.getValueAt(row, 0).toString().trim();
            String roleID = table.getValueAt(row, 1).toString().trim();

            if (roleID.isEmpty() || globalID.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid record. Role ID or Global ID missing.");
                return;
            }

            // Determine file path for the role
            String roleFile = UserManager.getRoleFilePath(roleID);
            if (roleFile == null) {
                JOptionPane.showMessageDialog(null, "Unknown role prefix in Role ID: " + roleID);
                return;
            }

            // Retrieve user details from User.txt
            User userToEdit = UserManager.getUserById(globalID);
            if (userToEdit == null) {
                JOptionPane.showMessageDialog(null, "No user found with ID: " + globalID, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Old values
            String oldUsername = userToEdit.getUsername();
            String oldPhone = userToEdit.getPhone();
            String oldRoleCode = userToEdit.getRole();

            // UI Panel Setup
            JPanel formPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);

            // Username Field
            gbc.gridx = 0; gbc.gridy = 0;
            formPanel.add(new JLabel("Username:"), gbc);
            gbc.gridx = 1;
            JTextField tfUsername = new JTextField(oldUsername, 20);
            formPanel.add(tfUsername, gbc);

            // Phone Field
            gbc.gridx = 0; gbc.gridy = 1;
            formPanel.add(new JLabel("Phone:"), gbc);
            gbc.gridx = 1;
            JTextField tfPhone = new JTextField(oldPhone, 20);
            formPanel.add(tfPhone, gbc);

            // Role Field
            gbc.gridx = 0; gbc.gridy = 2;
            formPanel.add(new JLabel("Role:"), gbc);
            gbc.gridx = 1;
            JComboBox<String> cbRole = new JComboBox<>(roleOptions);
            cbRole.setSelectedItem(codeToRole(oldRoleCode));
            formPanel.add(cbRole, gbc);

            // Show Dialog
            int result = JOptionPane.showConfirmDialog(
                    null, formPanel, "Edit User - " + globalID, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                boolean isUpdated = false;

                // Get new values
                String newUsername = ((JTextField) formPanel.getComponent(1)).getText().trim();
                String newPhone = ((JTextField) formPanel.getComponent(3)).getText().trim();
                String newRoleText = (String) ((JComboBox<?>) formPanel.getComponent(5)).getSelectedItem();
                String newRoleCode = roleToCode(newRoleText);

                ValidationManager validator = new ValidationManager();
                if (!validator.nullCheck(newUsername) || !validator.characterCheck(newUsername)) {
                    JOptionPane.showMessageDialog(null, "Invalid username format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!validator.phoneValidation(newPhone)) {
                    JOptionPane.showMessageDialog(null, "Invalid phone number format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check for changes
                if (!newUsername.equals(oldUsername)) {
                    userToEdit.setUsername(newUsername);
                    isUpdated = true;
                }
                if (!newPhone.equals(oldPhone)) {
                    userToEdit.setPhone(newPhone);
                    isUpdated = true;
                }
                if (!newRoleCode.equals(oldRoleCode)) {
                    userToEdit.setRole(newRoleCode);
                    isUpdated = true;
                }

                if (isUpdated) {
                    // Update in User.txt
                    boolean success = UserManager.updateUser(userToEdit);

                    if (!success) {
                        JOptionPane.showMessageDialog(null, "Failed to update user in User.txt", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update in Role-specific file
                    boolean roleSuccess = UserManager.updateRoleUser(roleFile, roleID, userToEdit);
                    if (!roleSuccess) {
                        JOptionPane.showMessageDialog(null, "Failed to update user in role file", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update table
                    table.setValueAt(newUsername, row, 1);
                    table.setValueAt(newPhone, row, 2);
                    table.setValueAt(newRoleCode, row, 3);

                    JOptionPane.showMessageDialog(null, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }


            private void handleDelete() {
                // Get Global ID and Role ID from the table
                String globalID = table.getValueAt(row, 0).toString().trim();
                String roleID = table.getValueAt(row, 1).toString().trim();

                if (roleID.isEmpty() || globalID.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Invalid record. Role ID or Global ID missing.");
                    return;
                }

                // Determine file path for the role
                String roleFile = UserManager.getRoleFilePath(roleID);
                if (roleFile == null) {
                    JOptionPane.showMessageDialog(null, "Unknown role prefix in Role ID: " + roleID);
                    return;
                }

                // Confirm deletion
                int confirm = JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to delete the record with Role ID: " + roleID + "?", 
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Remove from role-specific file
                    boolean roleSuccess = UserManager.removeUserFromRoleFile(roleFile, roleID);
                    // Remove from global file
                    boolean globalSuccess = UserManager.removeUserFromGlobalFile(globalID);

                    if (roleSuccess && globalSuccess) {
                        JOptionPane.showMessageDialog(null, "Record with Role ID: " + roleID + " deleted successfully.");
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.removeRow(row);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete record with Role ID: " + roleID);
                    }
                }
            }
        }


public class CustomerButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private String label;
    private int row;
    private final JTable table;
    
    // Folder for images
    private static final String IMAGES_FOLDER = "src/foodhub/images/";

    public CustomerButtonEditor(JCheckBox checkBox, JTable table, String buttonLabel) {
        super(checkBox);
        this.table = table;
        this.button = new JButton(buttonLabel);
        this.button.setOpaque(true);

        button.addActionListener(e -> {
            fireEditingStopped(); // Stop editing

            if ("Edit".equals(buttonLabel)) {
                handleCustomerEdit();
            } else if ("Delete".equals(buttonLabel)) {
                handleCustomerDelete();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.row = row;
        this.label = (value == null) ? "" : value.toString();
        button.setText(label);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    private void handleCustomerEdit() {
        // 1) Get Customer ID from the table
        String customerID = table.getValueAt(row, 0).toString();

        // 2) Retrieve the existing Customer
        CustomerModel customerToEdit = CustomerManager.getCustomerById(customerID);
        if (customerToEdit == null) {
            JOptionPane.showMessageDialog(
                table,
                "No customer found with ID: " + customerID,
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Old values
        String oldUsername = customerToEdit.getUsername();
        double oldCredit = customerToEdit.getCredit();
        String oldStreet = customerToEdit.getStreet();
        String oldPoscode = customerToEdit.getPostcode();
        String oldState = customerToEdit.getState();
        String picFilename = customerToEdit.getProfilePic();

        if (picFilename == null || picFilename.isBlank()) {
            picFilename = "default.png";
        }

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Photo panel
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        photoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Build full path for profile pic
        String profilePicPath = IMAGES_FOLDER + picFilename.trim();
        File imgFile = new File(profilePicPath);
        if (!imgFile.exists()) {
            profilePicPath = IMAGES_FOLDER + "default.png";
        }

        ImageIcon icon = new ImageIcon(profilePicPath);
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel photoLabel = new JLabel(new ImageIcon(scaledImage));
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
        photoPanel.add(photoLabel);
        mainPanel.add(photoPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField tfUsername = new JTextField(oldUsername, 20);
        formPanel.add(tfUsername, gbc);

        // Credit
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Credit:"), gbc);
        gbc.gridx = 1;
        JTextField tfCredit = new JTextField(String.valueOf(oldCredit), 20);
        formPanel.add(tfCredit, gbc);

        // Street
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Street:"), gbc);
        gbc.gridx = 1;
        JTextField tfStreet = new JTextField(oldStreet, 20);
        formPanel.add(tfStreet, gbc);

        // Poscode
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Poscode:"), gbc);
        gbc.gridx = 1;
        JTextField tfPoscode = new JTextField(oldPoscode, 20);
        formPanel.add(tfPoscode, gbc);

        // State
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("State:"), gbc);
        gbc.gridx = 1;
        JTextField tfState = new JTextField(oldState, 20);
        formPanel.add(tfState, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Show dialog
        int result = JOptionPane.showConfirmDialog(
                null,
                mainPanel,
                "Edit Customer - " + customerID,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            boolean isUpdated = false;

            // Check for changes
            String newUsername = tfUsername.getText().trim();
            if (!newUsername.equals(oldUsername)) {
                customerToEdit.setUsername(newUsername);
                isUpdated = true;
            }

            // Handle Credit field
            String newCreditStr = tfCredit.getText().trim();
            double newCredit = oldCredit; // Initialize with oldCredit as a fallback
            try {
                newCredit = Double.parseDouble(newCreditStr);
                if (newCredit != oldCredit) {
                    customerToEdit.setCredit(newCredit);
                    isUpdated = true;
                }
            } catch (NumberFormatException ignored) {
                // If parsing fails, newCredit remains as oldCredit
            }

            String newStreet = tfStreet.getText().trim();
            if (!newStreet.equals(oldStreet)) {
                customerToEdit.setStreet(newStreet);
                isUpdated = true;
            }

            String newPoscode = tfPoscode.getText().trim();
            if (!newPoscode.equals(oldPoscode)) {
                customerToEdit.setPostcode(newPoscode);
                isUpdated = true;
            }

            String newState = tfState.getText().trim();
            if (!newState.equals(oldState)) {
                customerToEdit.setState(newState);
                isUpdated = true;
            }

            if (isUpdated) {
                // Save changes
                boolean success = CustomerManager.updateCustomer(customerToEdit);

                if (!success) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to update customer with ID: " + customerID,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Update table row (assuming columns: 0=ID,1=Username,2=Credit,3=Street,4=Poscode,5=State,6=Pic,7=Edit,8=Delete)
                table.setValueAt(newUsername, row, 1);
                table.setValueAt(String.valueOf(newCredit), row, 2);
                table.setValueAt(newStreet, row, 3);
                table.setValueAt(newPoscode, row, 4);
                table.setValueAt(newState, row, 5);

                JOptionPane.showMessageDialog(
                        null,
                        "Customer updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    private void handleCustomerDelete() {
        // Get Customer ID
        String customerID = table.getValueAt(row, 0).toString();

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "<html>Are you sure you want to delete Customer: <b>" + customerID + "</b>?</html>",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Remove from data source
            boolean removed = CustomerManager.removeCustomer(customerID);
            if (!removed) {
                JOptionPane.showMessageDialog(
                        null,
                        "Failed to delete customer.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Remove row from table
            ((DefaultTableModel) table.getModel()).removeRow(row);

            JOptionPane.showMessageDialog(
                    table,
                    "<html>Customer <b>" + customerID + "</b> deleted successfully!</html>",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}





public class VendorButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private String label;
    private int row;
    private final JTable table;

    // Folder for images
    private static final String IMAGES_FOLDER = "src/foodhub/images/";

    public VendorButtonEditor(JCheckBox checkBox, JTable table, String buttonLabel) {
        super(checkBox);
        this.table = table;
        this.button = new JButton(buttonLabel);
        this.button.setOpaque(true);

        button.addActionListener(e -> {
            fireEditingStopped(); // Stop editing

            if ("Edit".equals(buttonLabel)) {
                handleVendorEdit();
            } else if ("Delete".equals(buttonLabel)) {
                handleVendorDelete();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.row = row;
        this.label = (value == null) ? "" : value.toString();
        button.setText(label);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    private void handleVendorEdit() {
        // 1) Get Vendor ID from the table
        String vendorID = table.getValueAt(row, 0).toString();

        // 2) Retrieve the existing Vendor (adjust method calls as needed)
        Vendor vendorToEdit = VendorManager.getVendorById(vendorID);
        if (vendorToEdit == null) {
            JOptionPane.showMessageDialog(
                table,
                "No vendor found with ID: " + vendorID,
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Old values
        String oldUsername = vendorToEdit.getUsername();
        String oldStallName = vendorToEdit.getStallName();
        String oldAvailable = vendorToEdit.getAvailable();
        String picFilename = vendorToEdit.getProfilePic();

        if (picFilename == null || picFilename.isBlank()) {
            picFilename = "default.png";
        }

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Photo panel
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        photoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Build full path for profile pic
        String profilePicPath = IMAGES_FOLDER + picFilename.trim();
        File imgFile = new File(profilePicPath);
        if (!imgFile.exists()) {
            profilePicPath = IMAGES_FOLDER + "default.png";
        }

        ImageIcon icon = new ImageIcon(profilePicPath);
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel photoLabel = new JLabel(new ImageIcon(scaledImage));
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
        photoPanel.add(photoLabel);
        mainPanel.add(photoPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; 
        JTextField tfUsername = new JTextField(oldUsername, 20);
        formPanel.add(tfUsername, gbc);

        // Stall Name
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Stall Name:"), gbc);
        gbc.gridx = 1;
        JTextField tfStallName = new JTextField(oldStallName, 20);
        formPanel.add(tfStallName, gbc);

        // Available
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Available:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> cbAvailable = new JComboBox<>(new String[]{"true", "false"});
        cbAvailable.setSelectedItem(oldAvailable);
        formPanel.add(cbAvailable, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Show dialog
        int result = JOptionPane.showConfirmDialog(
                null,
                mainPanel,
                "Edit Vendor - " + vendorID,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            boolean isUpdated = false;

            // If blank => keep old values
            String newUsername = tfUsername.getText().trim();
            if (!newUsername.equals(oldUsername)) {
                vendorToEdit.setUsername(newUsername);
                isUpdated = true;
            }

            String newStallName = tfStallName.getText().trim();
            if (!newStallName.equals(oldStallName)) {
                vendorToEdit.setStallName(newStallName);
                isUpdated = true;
            }

            String newAvailable = cbAvailable.getSelectedItem().toString();
            if (!newAvailable.equals(oldAvailable)) {
                vendorToEdit.setAvailable(newAvailable);
                isUpdated = true;
            }

            if (isUpdated) {
                // Save to file using the updateVendor method
                boolean success = VendorManager.updateVendor(vendorToEdit);

                if (!success) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to update vendor with ID: " + vendorID,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Update table row (assuming columns: 0=ID,1=Username,2=Stall Name,3=Revenue,4=Available,5=Pic,6=Edit,7=Delete)
                table.setValueAt(newUsername, row, 1);
                table.setValueAt(newStallName, row, 2);
                table.setValueAt(newAvailable, row, 4);

                JOptionPane.showMessageDialog(
                        null,
                        "Vendor updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    private void handleVendorDelete() {
        // Get Vendor ID
        String vendorID = table.getValueAt(row, 0).toString();

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "<html>Are you sure you want to delete Vendor: <b>" + vendorID + "</b>?</html>",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Remove from data source
            boolean removed = VendorManager.removeVendor(vendorID);
            if (!removed) {
                JOptionPane.showMessageDialog(
                        null,
                        "Failed to delete vendor.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Remove row from table
            ((DefaultTableModel) table.getModel()).removeRow(row);

            JOptionPane.showMessageDialog(
                    table,
                    "<html>Vendor <b>" + vendorID + "</b> deleted successfully!</html>",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}


public class RunnerButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private String label;
    private int row;
    private final JTable table;

    // Folder for images
    private static final String IMAGES_FOLDER = "src/foodhub/images/";

    public RunnerButtonEditor(JCheckBox checkBox, JTable table, String buttonLabel) {
        super(checkBox);
        this.table = table;
        this.button = new JButton(buttonLabel);
        this.button.setOpaque(true);

        button.addActionListener(e -> {
            fireEditingStopped(); // Stop editing

            if ("Edit".equals(buttonLabel)) {
                handleRunnerEdit();
            } else if ("Delete".equals(buttonLabel)) {
                handleRunnerDelete();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.row = row;
        this.label = (value == null) ? "" : value.toString();
        button.setText(label);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    private void handleRunnerEdit() {
        // 1) Get Runner ID from the table
        String runnerID = table.getValueAt(row, 0).toString();

        // 2) Retrieve the existing Runner (adjust method calls as needed)
        Runner runnerToEdit = RunnerManager.getRunnerById(runnerID);
        if (runnerToEdit == null) {
            JOptionPane.showMessageDialog(
                table,
                "No runner found with ID: " + runnerID,
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Old values
        String oldUsername = runnerToEdit.getUsername();
        String picFilename = runnerToEdit.getProfilePic();

        if (picFilename == null || picFilename.isBlank()) {
            picFilename = "default.png";
        }

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Photo panel
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        photoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Build full path for profile pic
        String profilePicPath = IMAGES_FOLDER + picFilename.trim();
        File imgFile = new File(profilePicPath);
        if (!imgFile.exists()) {
            profilePicPath = IMAGES_FOLDER + "default.png";
        }

        ImageIcon icon = new ImageIcon(profilePicPath);
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel photoLabel = new JLabel(new ImageIcon(scaledImage));
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
        photoPanel.add(photoLabel);
        mainPanel.add(photoPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; 
        JTextField tfUsername = new JTextField(oldUsername, 20);
        formPanel.add(tfUsername, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Show dialog
        int result = JOptionPane.showConfirmDialog(
                null,
                mainPanel,
                "Edit Runner - " + runnerID,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            // If blank => keep old values
            String newUsername = tfUsername.getText().trim();
            if (!newUsername.equals(oldUsername)) {
                // Update object
                runnerToEdit.setUsername(newUsername);

                // Save to file using the updateRunner method
                boolean success = RunnerManager.updateRunner(runnerToEdit);

                if (!success) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to update runner with ID: " + runnerID,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Update table row (assuming columns: 0=ID,1=Username,2=Revenue,3=Pic,4=Edit,5=Delete)
                table.setValueAt(newUsername, row, 1);

                JOptionPane.showMessageDialog(
                        null,
                        "Runner updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    private void handleRunnerDelete() {
        // Get Runner ID
        String runnerID = table.getValueAt(row, 0).toString();

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "<html>Are you sure you want to delete Runner: <b>" + runnerID + "</b>?</html>",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Remove from data source
            boolean removed = RunnerManager.removeRunner(runnerID);
            if (!removed) {
                JOptionPane.showMessageDialog(
                        null,
                        "Failed to delete runner.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Remove row from table
            ((DefaultTableModel) table.getModel()).removeRow(row);

            JOptionPane.showMessageDialog(
                    table,
                    "<html>Runner <b>" + runnerID + "</b> deleted successfully!</html>",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}

public class AdminCreateUserForm extends JFrame {

    private JTextField tfUsername, tfPhone, tfPassword, tfConfirmPassword, tfStreet, tfPostcode, tfState, tfStallName, tfCredit;
    private JComboBox<String> cbRole;
    private JLabel lblStreet, lblPostcode, lblState, lblStallName, lblCredit;
    private JButton btnSave, btnBack;
    private UserManager userManager;
    private ValidationManager validator;
    private DialogManager dialogManager;
    private AdminMenu adminMenu; // Reference to AdminMenu


    public AdminCreateUserForm(AdminMenu adminMenu) {
        this.adminMenu = adminMenu; // Store reference to AdminMenu
        setLocationRelativeTo(null);  // Center window on screen

        java.net.URL imageURL = getClass().getResource("/foodhub/images/Foodhub.png");
        ImageIcon image = new ImageIcon(imageURL);
        setIconImage(image.getImage());    // Set window logo

        userManager = new UserManager(FilePaths.USER_FILE);
        validator = new ValidationManager();  // **Initialize validation manager**
        dialogManager = new DialogManager("User Registration"); // **Initialize DialogManager with Title**

        setTitle("Create Account");
        setSize(500, 350); // Default size, dynamically adjusted later
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        tfUsername = new JTextField(20);
        add(tfUsername, gbc);

        // Phone
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        tfPhone = new JTextField(20);
        add(tfPhone, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        tfPassword = new JPasswordField(20);
        add(tfPassword, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        tfConfirmPassword = new JPasswordField(20);
        add(tfConfirmPassword, gbc);

        // Role Selection
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        cbRole = new JComboBox<>(new String[]{"Customer", "Vendor", "Runner", "Admin", "Manager"});
        cbRole.addActionListener(e -> toggleRoleFields());
        add(cbRole, gbc);

        // Address Fields (Customer)
        lblStreet = new JLabel("Street:");
        lblPostcode = new JLabel("Postcode:");
        lblState = new JLabel("State:");
        tfStreet = new JTextField(20);
        tfPostcode = new JTextField(20);
        tfState = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 5;
        add(lblStreet, gbc);
        gbc.gridx = 1;
        add(tfStreet, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        add(lblPostcode, gbc);
        gbc.gridx = 1;
        add(tfPostcode, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        add(lblState, gbc);
        gbc.gridx = 1;
        add(tfState, gbc);

        // Vendor Fields
        lblStallName = new JLabel("Stall Name:");
        tfStallName = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 8;
        add(lblStallName, gbc);
        gbc.gridx = 1;
        add(tfStallName, gbc);

        // Credit (For Customer)
        lblCredit = new JLabel("Credit:");
        tfCredit = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 9;
        add(lblCredit, gbc);
        gbc.gridx = 1;
        add(tfCredit, gbc);

        // Buttons
        btnSave = new JButton("Create Account");
        btnBack = new JButton("Back");

        gbc.gridx = 0; gbc.gridy = 10;
        add(btnBack, gbc);

        gbc.gridx = 1;
        add(btnSave, gbc);

        btnSave.addActionListener(e -> createUser());
        btnBack.addActionListener(e -> goBack());

        toggleRoleFields(); // Initialize field visibility
    }
    
    private void toggleRoleFields() {
        String selectedRole = (String) cbRole.getSelectedItem();

        boolean isCustomer = selectedRole.equals("Customer");
        boolean isVendor = selectedRole.equals("Vendor");

        lblStreet.setVisible(isCustomer);
        lblPostcode.setVisible(isCustomer);
        lblState.setVisible(isCustomer);
        tfStreet.setVisible(isCustomer);
        tfPostcode.setVisible(isCustomer);
        tfState.setVisible(isCustomer);
        lblCredit.setVisible(isCustomer);
        tfCredit.setVisible(isCustomer);

        lblStallName.setVisible(isVendor);
        tfStallName.setVisible(isVendor);

        // Adjust frame size dynamically
        if (isCustomer) {
            setSize(500, 550);
        } else if (isVendor) {
            setSize(500, 400);
        } else {
            setSize(500, 350);
        }

        // Keep the window centered
        setLocationRelativeTo(null);
    }

    private void createUser() {
        String username = tfUsername.getText().trim();
        String phone = tfPhone.getText().trim();
        String password = new String(((JPasswordField) tfPassword).getPassword());
        String confirmPassword = new String(((JPasswordField) tfConfirmPassword).getPassword());
        String roleText = (String) cbRole.getSelectedItem();

        int role = switch (roleText) {
            case "Customer" -> 4;
            case "Vendor" -> 2;
            case "Runner" -> 3;
            case "Admin" -> 1;
            case "Manager" -> 0;
            default -> -1;
        };

        // **VALIDATIONS USING ValidationManager + DialogManager**
        if (!validator.nullCheck(username) || !validator.characterCheck(username)) {
            dialogManager.showErrorMessage("Invalid username! Only letters and numbers allowed.");
            return;
        }

        if (!validator.phoneValidation(phone)) {
            dialogManager.showErrorMessage("Invalid phone number format!");
            return;
        }

        if (!validator.passwordValidation(password)) {
            dialogManager.showErrorMessage("Password must be at least 8 characters with 1 number and 1 special character!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            dialogManager.showErrorMessage("Passwords do not match!");
            return;
        }

        boolean success = false;

        if (role == 4) { // Customer
            String street = tfStreet.getText().trim();
            String postcode = tfPostcode.getText().trim();
            String state = tfState.getText().trim();
            String credit = tfCredit.getText().trim();

            // If credit is empty, default to 0
            if (credit.isEmpty()) {
                credit = "0";
            }

            // Validate credit field (must be numeric)
            if (!validator.isNumeric(credit)) {
                dialogManager.showErrorMessage("Credit must be a numeric value!");
                return;
            }


            success = userManager.registerUser(username, phone, role, password, null);
        } else if (role == 2) { // Vendor
            String stallName = tfStallName.getText().trim();

            if (!validator.nullCheck(stallName)) {
                dialogManager.showErrorMessage("Stall Name is required for Vendors!");
                return;
            }

            success = userManager.registerUser(username, phone, role, password, null);
        } else { // Other roles
            success = userManager.registerUser(username, phone, role, password, null);
        }

        if (success) {
            dialogManager.showInfoMessage("Account created successfully!");

            // REFRESH TABLES IN ADMIN MENU
            if (adminMenu != null) {
                adminMenu.refreshTables();
            }

            dispose();
        } else {
            dialogManager.showErrorMessage("Failed to create account.");
        }
    }

    private void goBack() {
        new AdminMenu().setVisible(true);
        dispose();
    }

}  
    
     public void refreshTables() {
        filterUsersTable("");
        filterCustomersTable("");
        filterVendorsTable("");
        filterRunnersTable("");
    }  
    
    private void filterTables(String searchTerm) {
        searchTerm = searchTerm.toLowerCase().trim();
        System.out.println("run1");

        DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
        model.setRowCount(0); // Clear previous search results

        // 🔹 Filter Users Table
        List<User> users = UserManager.getAllUsers();
        System.out.println("users list" + users.size());
        for (User u : users) {
            System.out.println("run for loop");
            System.out.println(u.getUserID());
            System.out.println(searchTerm);
            if (u.getUserID().toLowerCase().equals(searchTerm)) {
                System.out.println(u.getUserID() + " " + searchTerm);
                model.addRow(new Object[]{"User", u.getUserID(), u.getUsername(), "View"});
            }
        }

        // 🔹 Filter Customers Table
        List<CustomerModel> customers = CustomerManager.getAllCustomers();
        for (CustomerModel c : customers) {
            if (c.getCustomerID().toLowerCase().equals(searchTerm)) {
                model.addRow(new Object[]{"Customer", c.getCustomerID(), c.getUsername(), "View"});
            }
        }

        // 🔹 Filter Vendors Table
        List<Vendor> vendors = VendorManager.getAllVendors();
        for (Vendor v : vendors) {
            if (v.getVendorID().toLowerCase().equals(searchTerm)) {
                model.addRow(new Object[]{"Vendor", v.getVendorID(), v.getUsername(), "View"});
            }
        }

        // 🔹 Filter Runners Table
        List<Runner> runners = RunnerManager.getAllRunners();
        for (Runner r : runners) {
            if (r.getRunnerID().toLowerCase().equals(searchTerm)) {
                model.addRow(new Object[]{"Runner", r.getRunnerID(), r.getUsername(), "View"});
            }
        }

        // 🔹 Filter Order History Table
        List<String> orderIDs = getAllOrderIDs();
        for (String orderID : orderIDs) {
            receiptManager receipt = new receiptManager(orderID);
            if (receipt.getOrderID().toLowerCase().equals(searchTerm) || receipt.getOrderID().equals(searchTerm)) {
                model.addRow(new Object[]{"Order History", receipt.getOrderID(), receipt.getCustomerName(), "View"});
            }
        }

        // 🔹 Filter Transactions Table
        String filePath = FilePaths.PAYMENT_FILE;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length == 5) {
                    String paymentID = parts[0].trim();
                    String username = parts[1].trim();
                    String amount = parts[2].trim();
                    String dateTime = parts[3].trim();
                    String status = parts[4].trim();

                    if (paymentID.toLowerCase().equals(searchTerm) || paymentID.equals(searchTerm)) {
                        model.addRow(new Object[]{"Transaction", paymentID, username, "View"});
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleTable7Click() {
        int selectedRow = jTable7.getSelectedRow();
        if (selectedRow == -1) return; // No row selected

        String category = jTable7.getValueAt(selectedRow, 0).toString(); // Get category
        String searchTerm = jTable7.getValueAt(selectedRow, 1).toString(); // Get the searched term

        switch (category) {
            case "User":
                jTabbedPanel.setSelectedIndex(1); // Switch to Users tab
                filterUsersTable(searchTerm);
                break;
            case "Customers":
                jTabbedPanel.setSelectedIndex(2); // Switch to Customers tab
                filterCustomersTable(searchTerm);
                break;
            case "Vendors":
                jTabbedPanel.setSelectedIndex(3); // Switch to Vendors tab
                filterVendorsTable(searchTerm);
                break;
            case "Runners":
                jTabbedPanel.setSelectedIndex(4); // Switch to Runners tab
                filterRunnersTable(searchTerm);
                break;
            case "Order History":
                jTabbedPanel.setSelectedIndex(5); // Switch to Order History tab
                filterOrderHistoryTable(searchTerm);
                break;
            case "Transactions":
                jTabbedPanel.setSelectedIndex(6); // Switch to Transactions tab
                filterTransactionsTable(searchTerm);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid category", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterUsersTable(String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear table

        List<User> users = UserManager.getAllUsers();
        for (User u : users) {
            if (u.getUserID().toLowerCase().equals(searchTerm)) {
                model.addRow(new Object[]{
                    u.getUserID(), u.getRoleID(), u.getUsername(), u.getPhone(), u.getRole(), 
                    loadProfileIcon(u.getProfilePic()), "Edit", "Delete"
                });
            }
        }

        jTable1.revalidate();
        jTable1.repaint();
    }


    private void filterCustomersTable(String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0); // Clear table

        List<CustomerModel> customers = CustomerManager.getAllCustomers();
        for (CustomerModel c : customers) {
            if (c.getCustomerID().toLowerCase().equals(searchTerm)) {
                model.addRow(new Object[]{
                    c.getCustomerID(), c.getUsername(), c.getCredit(), c.getStreet(), 
                    c.getPostcode(), c.getState(), loadProfileIcon(c.getProfilePic()), "Edit", "Delete"
                });
            }
        }

        jTable2.revalidate();
        jTable2.repaint();
    }


    private void filterVendorsTable(String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0); // Clear table

        List<Vendor> vendors = VendorManager.getAllVendors();
        for (Vendor v : vendors) {
            if (v.getVendorID().toLowerCase().equals(searchTerm)) {
                model.addRow(new Object[]{
                    v.getVendorID(), v.getUsername(), v.getStallName(), v.getRevenue(), v.getAvailable(),
                    loadProfileIcon(v.getProfilePic()), "Edit", "Delete"
                });
            }
        }

        jTable3.revalidate();
        jTable3.repaint();
    }


    private void filterRunnersTable(String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        model.setRowCount(0); // Clear table

        List<Runner> runners = RunnerManager.getAllRunners();
        for (Runner r : runners) {
            if (r.getRunnerID().toLowerCase().equals(searchTerm)) {
                model.addRow(new Object[]{
                    r.getRunnerID(), r.getUsername(), r.getRevenue(), 
                    loadProfileIcon(r.getProfilePic()), "Edit", "Delete"
                });
            }
        }

        jTable4.revalidate();
        jTable4.repaint();
    }


    private void filterOrderHistoryTable(String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
        model.setRowCount(0); // Clear table

        List<String> orderIDs = getAllOrderIDs();
        for (String orderID : orderIDs) {
            receiptManager receipt = new receiptManager(orderID);
            if (receipt.getOrderID().toLowerCase().equals(searchTerm)) {
                model.addRow(new Object[]{
                    receipt.getOrderID(), receipt.getCustomerName(), receipt.getPaidTo(), 
                    receipt.getOrderType(), receipt.getPaymentDate(), receipt.getPaymentTime(),
                    receipt.getTotalAmount(), receipt.getPaymentStatus(), "Generate Receipt"
                });
            }
        }

        jTable5.revalidate();
        jTable5.repaint();
    }


    private void filterTransactionsTable(String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        model.setRowCount(0); // Clear table

        String filePath = FilePaths.PAYMENT_FILE;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length == 5) {
                    String paymentID = parts[0].trim();
                    String username = parts[1].trim();
                    String amount = parts[2].trim();
                    String dateTime = parts[3].trim();
                    String status = parts[4].trim();

                    if (paymentID.toLowerCase().equals(searchTerm) || paymentID.equals(searchTerm)) {
                        model.addRow(new Object[]{
                            paymentID, username, amount, dateTime.split(" ")[0], dateTime.split(" ")[1], status, 
                            "Accept", "Decline"
                        });
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        jTable6.revalidate();
        jTable6.repaint();
    }


    public class ViewButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private String label;
        private int row;
        private final JTable table;

        public ViewButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            this.button = new JButton("View");
            this.button.setOpaque(true);

            button.addActionListener(e -> {
                fireEditingStopped(); // Stop editing mode
                handleViewButtonClick();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            this.label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

        private void handleViewButtonClick() {
            int selectedRow = row; // Get clicked row
            if (selectedRow == -1) return; // No row selected

            String category = table.getValueAt(selectedRow, 0).toString(); // Get category
            String searchTerm = table.getValueAt(selectedRow, 1).toString().toLowerCase().trim(); // Get search term

            // Set the correct tab and apply the filter
            switch (category) {
                case "User":
                    jTabbedPanel.setSelectedIndex(1); // Switch to Users tab
                    SwingUtilities.invokeLater(() -> {
                        filterUsersTable(searchTerm);
                        jTable1.revalidate();
                        jTable1.repaint();
                    });
                    break;

                case "Customer":
                    jTabbedPanel.setSelectedIndex(2); // Switch to Customers tab
                    SwingUtilities.invokeLater(() -> {
                        filterCustomersTable(searchTerm);
                        jTable2.revalidate();
                        jTable2.repaint();
                    });
                    break;

                case "Vendor":
                    jTabbedPanel.setSelectedIndex(3); // Switch to Vendors tab
                    SwingUtilities.invokeLater(() -> {
                        filterVendorsTable(searchTerm);
                        jTable3.revalidate();
                        jTable3.repaint();
                    });
                    break;

                case "Runner":
                    jTabbedPanel.setSelectedIndex(4); // Switch to Runners tab
                    SwingUtilities.invokeLater(() -> {
                        filterRunnersTable(searchTerm);
                        jTable4.revalidate();
                        jTable4.repaint();
                    });
                    break;

                case "Order History":
                    jTabbedPanel.setSelectedIndex(5); // Switch to Order History tab
                    SwingUtilities.invokeLater(() -> {
                        filterOrderHistoryTable(searchTerm);
                        jTable5.revalidate();
                        jTable5.repaint();
                    });
                    break;

                case "Transaction":
                    jTabbedPanel.setSelectedIndex(6); // Switch to Transactions tab
                    SwingUtilities.invokeLater(() -> {
                        filterTransactionsTable(searchTerm);
                        jTable6.revalidate();
                        jTable6.repaint();
                    });
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid category", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }     
}
    
    private void filterJTable7() {
            String selectedCategory = jComboBox1.getSelectedItem().toString();
            String selectedCategory2 = jComboBox2.getSelectedItem().toString();
            String selectedNumOrder = jComboBox3.getSelectedItem().toString();

            DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
            model.setRowCount(0); // Clear previous search results

            List<Object[]> filteredData = new ArrayList<>();

        // 🔹 Clear previous filtered results
        filteredData.clear();

        // 🔹 Apply Category Filter
        if (selectedCategory.equals("User")) {
            List<User> users = UserManager.getAllUsers();
            for (User u : users) {
                filteredData.add(new Object[]{"User", u.getUserID(), u.getUsername(), "View"});
            }
        } else if (selectedCategory.equals("Customer")) {
            List<CustomerModel> customers = CustomerManager.getAllCustomers();
            for (CustomerModel c : customers) {
                filteredData.add(new Object[]{"Customer", c.getCustomerID(), c.getUsername(), "View"});
            }
        } else if (selectedCategory.equals("Vendor")) {
            List<Vendor> vendors = VendorManager.getAllVendors();
            for (Vendor v : vendors) {
                filteredData.add(new Object[]{"Vendor", v.getVendorID(), v.getUsername(), "View"});
            }
        } else if (selectedCategory.equals("Runner")) {
            List<Runner> runners = RunnerManager.getAllRunners();
            for (Runner r : runners) {
                filteredData.add(new Object[]{"Runner", r.getRunnerID(), r.getUsername(), "View"});
            }
        }

        // 🔹 Apply Category2 Filter (Only if the Category is related!)
        if (selectedCategory2.equals("Order History")) {
            List<String> orderIDs = getAllOrderIDs();
            for (String orderID : orderIDs) {
                receiptManager receipt = new receiptManager(orderID);
                filteredData.add(new Object[]{"Order History", receipt.getOrderID(), receipt.getCustomerName(), "View"});
            }
        } else if (selectedCategory2.equals("Transaction")) {
            String filePath = FilePaths.PAYMENT_FILE;
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" \\| ");
                    if (parts.length == 5) {
                        String paymentID = parts[0].trim();
                        String username = parts[1].trim();
                        filteredData.add(new Object[]{"Transaction", paymentID, username, "View"});
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



            // 🔹 Apply Numerical Sorting if applicable
            if (selectedNumOrder.equals("Lowest to Highest")) {
                filteredData.sort(Comparator.comparing(o -> Integer.parseInt(o[1].toString().replaceAll("[^0-9]", ""))));
            } else if (selectedNumOrder.equals("Highest to Lowest")) {
                filteredData.sort(Comparator.comparing(o -> Integer.parseInt(o[1].toString().replaceAll("[^0-9]", "")), Comparator.reverseOrder()));
            }

            // 🔹 Populate Table
            for (Object[] row : filteredData) {
                model.addRow(row);
            }
        }

    
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPanel = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox3 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        dashboardButton = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        registrationButton = new javax.swing.JButton();
        runnerButton = new javax.swing.JButton();
        vendorButton = new javax.swing.JButton();
        customerButton = new javax.swing.JButton();
        transactionButton1 = new javax.swing.JButton();
        transactionButton = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        logoutButton = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel11 = new javax.swing.JPanel();
        registrationButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 237));
        setMinimumSize(new java.awt.Dimension(1000, 582));
        setResizable(false);
        getContentPane().setLayout(null);

        jTabbedPanel.setBackground(new java.awt.Color(255, 255, 237));
        jTabbedPanel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jTabbedPanel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int index = sourceTabbedPane.getSelectedIndex();

                if (index == 0) {
                    // Load Users
                    loadUsersIntoTable(UserManager, jTable1);
                } else if (index == 1) {
                    // Load Customers
                    loadCustomersIntoTable(CustomerManager, jTable2);
                } else if (index == 2) {
                    // Load Vendors
                    loadVendorsIntoTable(UserManager, jTable3);
                } else if (index == 3) {
                    // Load Runners
                    loadRunnersIntoTable(UserManager, jTable4);
                } else if (index == 4) {
                    // Load Order History
                    loadTransactionsIntoTable(jTable5);
                } else if (index == 5) {
                    // Load Transactions
                    loadPaymentsIntoTable(jTable6);
                }

            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 237));
        jPanel1.setLayout(null);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator2);
        jSeparator2.setBounds(0, -40, 20, 570);

        jButton2.setBackground(new java.awt.Color(255, 219, 187));
        jButton2.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
        jButton2.setText("Create Accounts");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(20, 30, 150, 70);

        jTable7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTable7.setModel(new DefaultTableModel(
            new Object[][]{}, // Empty at the start
            new String[]{"Category", "ID", "Name", "View"} // Columns
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only "View" button is clickable
            }
        });
        jTable7.setRowHeight(30); // Set row height for better visibility
        jTable7.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)); // Set modern font
        jTable7.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)); // Bold headers

        jScrollPane7.setViewportView(jTable7);
        jTable7.getColumnModel().getColumn(0).setPreferredWidth(100);  // Category
        jTable7.getColumnModel().getColumn(1).setPreferredWidth(80);   // ID
        jTable7.getColumnModel().getColumn(2).setPreferredWidth(150);  // Name
        jTable7.getColumnModel().getColumn(3).setPreferredWidth(100);  // View Button
        jScrollPane7.setViewportView(jTable7);
        // Add "View" button
        // Add "View" button to last column
        jTable7.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer("View"));
        jTable7.getColumnModel().getColumn(3).setCellEditor(new ViewButtonEditor(new JCheckBox(), jTable7));

        jPanel1.add(jScrollPane7);
        jScrollPane7.setBounds(200, 120, 620, 400);

        jButton3.setBackground(new java.awt.Color(232, 252, 255));
        jButton3.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
        jButton3.setText("<html>Generate Ordered Items Frequency Report<html>");
        jButton3.setAlignmentY(0.0F);
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(200, 30, 300, 70);

        jButton4.setBackground(new java.awt.Color(232, 252, 255));
        jButton4.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
        jButton4.setText("<html>Transaction Amount Time Series Analysis<html>");
        jButton4.setAlignmentY(0.0F);
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(510, 30, 290, 70);

        jComboBox1.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Role", "User", "Customer", "Vendor", "Runner" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(20, 320, 160, 40);

        jComboBox2.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Type", "Order History", "Transaction" }));
        jComboBox2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(20, 380, 160, 40);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText(" Search Bar ");
        jTextArea1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTextArea1.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (jTextArea1.getText().trim().equalsIgnoreCase("Search Bar")) {
                    jTextArea1.setText(""); // Clear text when clicked
                    jTextArea1.setForeground(Color.BLACK); // Set text color to normal
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (jTextArea1.getText().trim().isEmpty()) {
                    jTextArea1.setForeground(Color.GRAY); // Set placeholder color
                    jTextArea1.setText("Search Bar"); // Restore placeholder text
                }
            }
        });
        jScrollPane8.setViewportView(jTextArea1);
        jTextArea1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterTables(jTextArea1.getText());
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterTables(jTextArea1.getText());
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterTables(jTextArea1.getText());
            }
        });

        jPanel1.add(jScrollPane8);
        jScrollPane8.setBounds(20, 150, 160, 150);

        jComboBox3.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Filter", "Lowest to Highest", "Highest to Lowest" }));
        jComboBox3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(20, 440, 160, 40);

        jTabbedPanel.addTab("Dashboard", jPanel1);

        jPanel2.setLayout(null);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{}, // Empty initial data
            new String[]{"UserID", "RoleID", "Username", "Phone", "Role", "Pic", "Edit", "Delete"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only Edit/Delete columns are editable
                return column == 6 || column == 7;
            }
        });
        jTable1.setRowHeight(40); // Increase row height
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)); // Set modern font
        jTable1.setGridColor(java.awt.Color.LIGHT_GRAY); // Light gray grid lines

        // Customize header appearance
        javax.swing.table.JTableHeader tableHeader = jTable1.getTableHeader();
        tableHeader.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)); // Bold font for header
        tableHeader.setBackground(java.awt.Color.DARK_GRAY); // Dark gray background
        tableHeader.setForeground(java.awt.Color.BLACK); // Black title text
        tableHeader.setOpaque(true); // Ensure custom styling is visible

        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);  // uid
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(40); //roleid
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100); // Username
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100); // phone
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(20);  // role
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(40); // pic
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);  // edit
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);  // delete

        // Set custom renderers for "Profile Picture", "Edit", and "Delete" columns
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Render profile picture as a label with an icon
                javax.swing.JLabel label = new javax.swing.JLabel();
                if (value instanceof javax.swing.Icon) {
                    label.setIcon((javax.swing.Icon) value);
                } else {
                    label.setText("No Image");
                }
                label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                return label;
            }
        });

        jTable1.getColumnModel().getColumn(6).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Render "Edit" as a button
                javax.swing.JButton editButton = new javax.swing.JButton("Edit");
                editButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
                return editButton;
            }
        });

        jTable1.getColumnModel().getColumn(7).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Render "Delete" as a button
                javax.swing.JButton deleteButton = new javax.swing.JButton("Delete");
                deleteButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
                deleteButton.setBackground(java.awt.Color.RED);
                deleteButton.setForeground(java.awt.Color.WHITE);
                return deleteButton;
            }
        });

        // For the "Edit" column at index 6:
        jTable1.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Edit"));
        jTable1.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new javax.swing.JCheckBox(), jTable1, "Edit"));

        // For the "Delete" column at index 6:
        jTable1.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Delete"));
        jTable1.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new javax.swing.JCheckBox(), jTable1, "Delete"));
        jScrollPane1.setViewportView(jTable1);
        UserManager UserManager = new UserManager(FilePaths.USER_FILE);    // Load data into the table
        loadUsersIntoTable(UserManager, jTable1);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 820, 530);

        jTabbedPanel.addTab("Users", jPanel2);

        jPanel3.setLayout(null);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{}, // Empty initial data
            new String[]{"ID", "Username", "Credit", "Street", "Poscode", "State", "Pic", "Edit", "Delete"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only Edit/Delete columns are editable
                return column == 7 || column == 8;
            }
        }
    );
    jTable2.setRowHeight(40); // Increase row height
    jTable2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)); // Set modern font
    jTable2.setGridColor(java.awt.Color.LIGHT_GRAY); // Light gray grid lines

    // Adjust column widths
    jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);  // CustomerID
    jTable2.getColumnModel().getColumn(1).setPreferredWidth(100); // Username
    jTable2.getColumnModel().getColumn(2).setPreferredWidth(60); // Credit
    jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);  // Street
    jTable2.getColumnModel().getColumn(4).setPreferredWidth(50); // Poscode
    jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);  // State
    jTable2.getColumnModel().getColumn(6).setPreferredWidth(40);  // Pic
    jTable2.getColumnModel().getColumn(7).setPreferredWidth(80);  // Edit
    jTable2.getColumnModel().getColumn(8).setPreferredWidth(80);  // Delete

    // Set custom renderers for "Profile Picture", "Edit", and "Delete" columns
    jTable2.getColumnModel().getColumn(6).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Render profile picture as a label with an icon
            javax.swing.JLabel label = new javax.swing.JLabel();
            if (value instanceof javax.swing.Icon) {
                label.setIcon((javax.swing.Icon) value);
            } else {
                label.setText("No Image");
            }
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            return label;
        }
    });

    jTable2.getColumnModel().getColumn(7).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Render "Edit" as a button
            javax.swing.JButton editButton = new javax.swing.JButton("Edit");
            editButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
            return editButton;
        }
    });

    jTable2.getColumnModel().getColumn(8).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Render "Delete" as a button
            javax.swing.JButton deleteButton = new javax.swing.JButton("Delete");
            deleteButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
            deleteButton.setBackground(java.awt.Color.RED);
            deleteButton.setForeground(java.awt.Color.WHITE);
            return deleteButton;
        }
    });

    // For the "Edit" column at index 7:
    jTable2.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Edit"));
    jTable2.getColumnModel().getColumn(7).setCellEditor(new CustomerButtonEditor(new javax.swing.JCheckBox(), jTable2, "Edit"));

    // For the "Delete" column at index 8:
    jTable2.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer("Delete"));
    jTable2.getColumnModel().getColumn(8).setCellEditor(new CustomerButtonEditor(new javax.swing.JCheckBox(), jTable2, "Delete"));
    jScrollPane2.setViewportView(jTable2);
    loadCustomersIntoTable(CustomerManager, jTable2);

    jPanel3.add(jScrollPane2);
    jScrollPane2.setBounds(0, 0, 830, 530);

    jTabbedPanel.addTab("Customer", jPanel3);

    jPanel4.setLayout(null);

    jTable3.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][]{}, // Empty initial data
        new String[]{"VendorID", "Username", "Stall Name", "Revenue", "Available", "Pic", "Edit", "Delete"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Only Edit/Delete columns are editable
            return column == 6 || column == 7;
        }
    });
    jTable3.setRowHeight(40); // Increase row height
    jTable3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)); // Set modern font
    jTable3.setGridColor(java.awt.Color.LIGHT_GRAY); // Light gray grid lines

    // Adjust column widths
    jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);  // vendorID
    jTable3.getColumnModel().getColumn(1).setPreferredWidth(100); // Username
    jTable3.getColumnModel().getColumn(2).setPreferredWidth(150); // Stall Name
    jTable3.getColumnModel().getColumn(3).setPreferredWidth(60); // revenue
    jTable3.getColumnModel().getColumn(4).setPreferredWidth(40);  // available
    jTable3.getColumnModel().getColumn(5).setPreferredWidth(40); // pic
    jTable3.getColumnModel().getColumn(6).setPreferredWidth(80); // Edit
    jTable3.getColumnModel().getColumn(7).setPreferredWidth(80);  // Delete

    jTable3.getColumnModel().getColumn(5).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Render profile picture as a label with an icon
            javax.swing.JLabel label = new javax.swing.JLabel();
            if (value instanceof javax.swing.Icon) {
                label.setIcon((javax.swing.Icon) value);
            } else {
                label.setText("No Image");
            }
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            return label;
        }
    });

    jTable3.getColumnModel().getColumn(6).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Render profile picture as a label with an icon
            javax.swing.JLabel label = new javax.swing.JLabel();
            if (value instanceof javax.swing.Icon) {
                label.setIcon((javax.swing.Icon) value);
            } else {
                label.setText("No Image");
            }
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            return label;
        }
    });

    jTable3.getColumnModel().getColumn(7).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Render "Edit" as a button
            javax.swing.JButton editButton = new javax.swing.JButton("Edit");
            editButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
            return editButton;
        }
    });

    jTable3.getColumnModel().getColumn(7).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Render "Delete" as a button
            javax.swing.JButton deleteButton = new javax.swing.JButton("Delete");
            deleteButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
            deleteButton.setBackground(java.awt.Color.RED);
            deleteButton.setForeground(java.awt.Color.WHITE);
            return deleteButton;
        }
    });

    // For the "Edit" column at index 6:
    jTable3.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Edit"));
    jTable3.getColumnModel().getColumn(6).setCellEditor(new VendorButtonEditor(new javax.swing.JCheckBox(), jTable3, "Edit"));

    // For the "Delete" column at index 7:
    jTable3.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Delete"));
    jTable3.getColumnModel().getColumn(7).setCellEditor(new VendorButtonEditor(new javax.swing.JCheckBox(), jTable3, "Delete"));
    jScrollPane3.setViewportView(jTable3);
    loadVendorsIntoTable(VendorManager, jTable3);

    jPanel4.add(jScrollPane3);
    jScrollPane3.setBounds(0, 0, 830, 530);

    jTabbedPanel.addTab("Vendor", jPanel4);

    jPanel5.setLayout(null);

    jTable4.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][]{}, // Empty initial data
        new String[]{"RunnerID", "Username", "Revenue", "pic", "Edit", "Delete"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Only Edit/Delete columns are editable
            return column == 4 || column == 5;
        }
    });
    jTable4.setRowHeight(40); // Increase row height
    jTable4.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)); // Set modern font
    jTable4.setGridColor(java.awt.Color.LIGHT_GRAY); // Light gray grid lines

    // Adjust column widths
    jTable4.getColumnModel().getColumn(0).setPreferredWidth(40);  // runnerID
    jTable4.getColumnModel().getColumn(1).setPreferredWidth(100); // Username
    jTable4.getColumnModel().getColumn(2).setPreferredWidth(80); // revenue
    jTable4.getColumnModel().getColumn(3).setPreferredWidth(40); // pic
    jTable4.getColumnModel().getColumn(4).setPreferredWidth(80);  // Edit
    jTable4.getColumnModel().getColumn(5).setPreferredWidth(80);  // Delete

    jTable4.getColumnModel().getColumn(3).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Render profile picture as a label with an icon
            javax.swing.JLabel label = new javax.swing.JLabel();
            if (value instanceof javax.swing.Icon) {
                label.setIcon((javax.swing.Icon) value);
            } else {
                label.setText("No Image");
            }
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            return label;
        }
    });

    jTable4.getColumnModel().getColumn(4).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Render "Edit" as a button
            javax.swing.JButton editButton = new javax.swing.JButton("Edit");
            editButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
            return editButton;
        }
    });

    jTable4.getColumnModel().getColumn(5).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Render "Delete" as a button
            javax.swing.JButton deleteButton = new javax.swing.JButton("Delete");
            deleteButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
            deleteButton.setBackground(java.awt.Color.RED);
            deleteButton.setForeground(java.awt.Color.WHITE);
            return deleteButton;
        }
    });

    // For the "Edit" column at index 4:
    jTable4.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("Edit"));
    jTable4.getColumnModel().getColumn(4).setCellEditor(new RunnerButtonEditor(new javax.swing.JCheckBox(), jTable4, "Edit"));

    // For the "Delete" column at index 5:
    jTable4.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("Delete"));
    jTable4.getColumnModel().getColumn(5).setCellEditor(new RunnerButtonEditor(new javax.swing.JCheckBox(), jTable4, "Delete"));
    jScrollPane4.setViewportView(jTable4);
    loadRunnersIntoTable(RunnerManager, jTable4);

    jPanel5.add(jScrollPane4);
    jScrollPane4.setBounds(0, 0, 830, 530);

    jTabbedPanel.addTab("Runner", jPanel5);

    jPanel6.setLayout(null);

    jTable5.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][]{}, // Empty initial data
        new String[]{"ID", "Username", "Vendor ID", "Order Type", "Date", "Time", "Amount", "Status", "Generate Receipt"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Only Edit/Delete columns are editable
            return column == 7 || column == 8;
        }
    });
    jTable5.setRowHeight(40); // Increase row height
    jTable5.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)); // Set modern font
    jTable5.setGridColor(java.awt.Color.LIGHT_GRAY); // Light gray grid lines

    // Adjust column widths
    jTable5.getColumnModel().getColumn(0).setPreferredWidth(60);  // ID
    jTable5.getColumnModel().getColumn(1).setPreferredWidth(100); // Username
    jTable5.getColumnModel().getColumn(2).setPreferredWidth(40);  // Vendor ID
    jTable5.getColumnModel().getColumn(3).setPreferredWidth(80); // Order Type
    jTable5.getColumnModel().getColumn(4).setPreferredWidth(100); // Date
    jTable5.getColumnModel().getColumn(5).setPreferredWidth(80);  // Time
    jTable5.getColumnModel().getColumn(6).setPreferredWidth(60);  // Amount
    jTable5.getColumnModel().getColumn(7).setPreferredWidth(100); // Status
    jTable5.getColumnModel().getColumn(8).setPreferredWidth(150); // Generate Receipt

    // Add a button for "Generate Receipt" at index 8
    jTable5.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer("Generate Receipt"));
    jTable5.getColumnModel().getColumn(8).setCellEditor(new RunnerButtonEditor(new javax.swing.JCheckBox(), jTable5, "Generate Receipt"));
    jScrollPane5.setViewportView(jTable5);
    loadTransactionsIntoTable(jTable5);

    // Add Mouse Listener to JTable
    jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = jTable5.getSelectedRow();
            if (row >= 0) {
                String selectedOrderID = jTable5.getValueAt(row, 0).toString();
                new Receipt(selectedOrderID).setVisible(true);
            }
        }
    });

    jPanel6.add(jScrollPane5);
    jScrollPane5.setBounds(0, 0, 830, 530);

    jTabbedPanel.addTab("Order History", jPanel6);

    jPanel7.setLayout(null);

    jTable6.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][]{}, // Empty initial data
        new String[]{"Payment ID", "Username", "Amount", "Date", "Time", "Status", "Accept", "Decline"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Only Accept/Decline buttons should be clickable
            return column == 6 || column == 7;
        }
    });
    // Set row height and font styling
    jTable6.setRowHeight(40);
    jTable6.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    jTable6.setGridColor(java.awt.Color.LIGHT_GRAY);

    // Adjust column widths
    jTable6.getColumnModel().getColumn(0).setPreferredWidth(40);  // Payment ID
    jTable6.getColumnModel().getColumn(1).setPreferredWidth(100); // Username
    jTable6.getColumnModel().getColumn(2).setPreferredWidth(80); // Amount
    jTable6.getColumnModel().getColumn(3).setPreferredWidth(120); // Date
    jTable6.getColumnModel().getColumn(4).setPreferredWidth(80);  // Time
    jTable6.getColumnModel().getColumn(5).setPreferredWidth(80); // Status
    jTable6.getColumnModel().getColumn(6).setPreferredWidth(100); // Accept Button
    jTable6.getColumnModel().getColumn(7).setPreferredWidth(100); // Decline Button

    // Add buttons for "Accept" and "Decline"

    String adminID = SessionManager.getInstance().getSessionID(); // Replace with your actual session retrieval

    // Set Accept button
    jTable6.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Accept"));
    jTable6.getColumnModel().getColumn(6).setCellEditor(new PaymentButtonEditor(new javax.swing.JCheckBox(), jTable6, "Accept", adminID));

    // Set Decline button
    jTable6.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Decline"));
    jTable6.getColumnModel().getColumn(7).setCellEditor(new PaymentButtonEditor(new javax.swing.JCheckBox(), jTable6, "Decline", adminID));
    jScrollPane6.setViewportView(jTable6);
    loadPaymentsIntoTable(jTable6);

    jPanel7.add(jScrollPane6);
    jScrollPane6.setBounds(0, 0, 830, 530);

    jTabbedPanel.addTab("Transactions", jPanel7);

    getContentPane().add(jTabbedPanel);
    jTabbedPanel.setBounds(170, 0, 810, 550);

    jPanel8.setBackground(new java.awt.Color(255, 255, 237));
    jPanel8.setToolTipText("");
    jPanel8.setLayout(null);

    jPanel9.setBackground(new java.awt.Color(255, 219, 187));
    jPanel9.setLayout(null);

    dashboardButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
    dashboardButton.setForeground(new java.awt.Color(16, 55, 86));
    dashboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-dashboard-24.png"))); // NOI18N
    dashboardButton.setText("Dashboard");
    dashboardButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    dashboardButton.setContentAreaFilled(false);
    dashboardButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    dashboardButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            dashboardButtonActionPerformed(evt);
        }
    });
    jPanel9.add(dashboardButton);
    dashboardButton.setBounds(0, 0, 170, 40);

    jPanel8.add(jPanel9);
    jPanel9.setBounds(0, 140, 170, 40);

    jPanel10.setBackground(new java.awt.Color(255, 219, 187));
    jPanel10.setLayout(null);

    registrationButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
    registrationButton.setForeground(new java.awt.Color(16, 55, 86));
    registrationButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-registration-24.png"))); // NOI18N
    registrationButton.setText("<html>Users<html>");
    registrationButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    registrationButton.setContentAreaFilled(false);
    registrationButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    registrationButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            registrationButtonActionPerformed(evt);
        }
    });
    jPanel10.add(registrationButton);
    registrationButton.setBounds(0, 0, 170, 40);

    runnerButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
    runnerButton.setForeground(new java.awt.Color(16, 55, 86));
    runnerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-registration-24.png"))); // NOI18N
    runnerButton.setText("Runner");
    runnerButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    runnerButton.setContentAreaFilled(false);
    runnerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    runnerButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            runnerButtonActionPerformed(evt);
        }
    });
    jPanel10.add(runnerButton);
    runnerButton.setBounds(0, 120, 170, 40);

    vendorButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
    vendorButton.setForeground(new java.awt.Color(16, 55, 86));
    vendorButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-registration-24.png"))); // NOI18N
    vendorButton.setText("Vendor");
    vendorButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    vendorButton.setContentAreaFilled(false);
    vendorButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    vendorButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            vendorButtonActionPerformed(evt);
        }
    });
    jPanel10.add(vendorButton);
    vendorButton.setBounds(0, 80, 170, 40);

    customerButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
    customerButton.setForeground(new java.awt.Color(16, 55, 86));
    customerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-registration-24.png"))); // NOI18N
    customerButton.setText("Customers");
    customerButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    customerButton.setContentAreaFilled(false);
    customerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    customerButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            customerButtonActionPerformed(evt);
        }
    });
    jPanel10.add(customerButton);
    customerButton.setBounds(0, 40, 170, 40);

    jPanel8.add(jPanel10);
    jPanel10.setBounds(0, 200, 170, 160);

    transactionButton1.setBackground(new java.awt.Color(255, 219, 187));
    transactionButton1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
    transactionButton1.setForeground(new java.awt.Color(16, 55, 86));
    transactionButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-transaction-24.png"))); // NOI18N
    transactionButton1.setText("Order History");
    transactionButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    transactionButton1.setContentAreaFilled(false);
    transactionButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    transactionButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            transactionButton1ActionPerformed(evt);
        }
    });
    jPanel8.add(transactionButton1);
    transactionButton1.setBounds(0, 380, 170, 40);

    transactionButton.setBackground(new java.awt.Color(255, 219, 187));
    transactionButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
    transactionButton.setForeground(new java.awt.Color(16, 55, 86));
    transactionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-transaction-24.png"))); // NOI18N
    transactionButton.setText("Transactions");
    transactionButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    transactionButton.setContentAreaFilled(false);
    transactionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    transactionButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            transactionButtonActionPerformed(evt);
        }
    });
    jPanel8.add(transactionButton);
    transactionButton.setBounds(0, 420, 170, 40);

    jPanel12.setBackground(new java.awt.Color(255, 219, 187));
    jPanel12.setLayout(null);

    logoutButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
    logoutButton.setForeground(new java.awt.Color(16, 55, 86));
    logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-log-out-24.png"))); // NOI18N
    logoutButton.setText("Sign Out");
    logoutButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    logoutButton.setContentAreaFilled(false);
    logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    logoutButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            logoutButtonActionPerformed(evt);
        }
    });
    jPanel12.add(logoutButton);
    logoutButton.setBounds(-10, 0, 180, 60);

    jPanel8.add(jPanel12);
    jPanel12.setBounds(0, 490, 170, 70);

    jPanel13.setBackground(new java.awt.Color(232, 252, 255));
    jPanel8.add(jPanel13);
    jPanel13.setBounds(0, 380, 170, 80);

    jLabel2.setFont(new java.awt.Font("STSong", 1, 14)); // NOI18N
    jLabel2.setText("Welcome, Administrator");
    jPanel8.add(jLabel2);
    jLabel2.setBounds(10, 90, 150, 20);

    jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-burger-48.png"))); // NOI18N
    jPanel8.add(jLabel3);
    jLabel3.setBounds(10, 10, 50, 70);

    jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    jLabel1.setText("FoodHub");
    jPanel8.add(jLabel1);
    jLabel1.setBounds(60, 35, 100, 30);
    jPanel8.add(jSeparator1);
    jSeparator1.setBounds(0, 120, 170, 10);

    getContentPane().add(jPanel8);
    jPanel8.setBounds(0, 0, 170, 560);

    jPanel11.setBackground(new java.awt.Color(255, 219, 187));
    jPanel11.setLayout(null);

    registrationButton1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
    registrationButton1.setForeground(new java.awt.Color(16, 55, 86));
    registrationButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/icon/icons8-registration-24.png"))); // NOI18N
    registrationButton1.setText("Users");
    registrationButton1.setBorder(null);
    registrationButton1.setContentAreaFilled(false);
    registrationButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    registrationButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            registrationButton1ActionPerformed(evt);
        }
    });
    jPanel11.add(registrationButton1);
    registrationButton1.setBounds(0, 4, 80, 30);

    getContentPane().add(jPanel11);
    jPanel11.setBounds(0, 150, 170, 40);

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
       
        this.dispose();
        Login login = new Login();
        login.setVisible(true);
        
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void dashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardButtonActionPerformed
        
        jTabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_dashboardButtonActionPerformed

    private void transactionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transactionButtonActionPerformed
       
        jTabbedPanel.setSelectedIndex(6);
    }//GEN-LAST:event_transactionButtonActionPerformed

    private void customerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPanel.setSelectedIndex(2);
    }//GEN-LAST:event_customerButtonActionPerformed

    private void vendorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendorButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPanel.setSelectedIndex(3);
    }//GEN-LAST:event_vendorButtonActionPerformed

    private void runnerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runnerButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPanel.setSelectedIndex(4);
    }//GEN-LAST:event_runnerButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        SwingUtilities.invokeLater(() -> new AdminCreateUserForm(this).setVisible(true));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void transactionButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transactionButton1ActionPerformed
        // TODO add your handling code here:
        jTabbedPanel.setSelectedIndex(5);
    }//GEN-LAST:event_transactionButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        JFrame frame = new JFrame("Ordered Items Analysis");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        // Add the chart panel to the frame
        frame.add(OrderedItemsChart.createOrderedItemsChartPanel());

        frame.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        JFrame frame = new JFrame("Transaction Analysis");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        // Add the chart panel to the frame
        frame.add(TransactionTimeSeriesChart.createTransactionChartPanel());

        frame.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        filterJTable7();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        filterJTable7();
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        filterJTable7();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void registrationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrationButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPanel.setSelectedIndex(1);
    }//GEN-LAST:event_registrationButtonActionPerformed

    private void registrationButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrationButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registrationButton1ActionPerformed

    
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
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminMenu().setVisible(true);
            }
        });
    }
    



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton customerButton;
    private javax.swing.JButton dashboardButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPanel;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton registrationButton;
    private javax.swing.JButton registrationButton1;
    private javax.swing.JButton runnerButton;
    private javax.swing.JButton transactionButton;
    private javax.swing.JButton transactionButton1;
    private javax.swing.JButton vendorButton;
    // End of variables declaration//GEN-END:variables
}
