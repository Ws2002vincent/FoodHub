/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

import static foodhub.FilePaths.USER_FILE;
import foodhub.runner.RunnerMenu;
import foodhub.vendor.RevenueDashboard;
import foodhub.vendor.SessionVendor;
import foodhub.vendor.SessionVendorID;
import java.awt.HeadlessException;
import foodhub.vendor.VendorMenu;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author akwc
 */
public class UserManager {
    private ArrayList<?> activeList; // Generic list to point to the correct list
    private ArrayList<User> userList;
    private ArrayList<CustomerModel> customerList;
    private ArrayList<Vendor> vendorList;
    private ArrayList<Runner> runnerList;
    private DialogManager dialogManager;
    private String filePath;

    public UserManager(String filePath) {
        this.userList = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.vendorList = new ArrayList<>();
        this.runnerList = new ArrayList<>();
        this.dialogManager = new DialogManager("An unexpected error occurred:");
        this.filePath = filePath;

        // Determine the active list based on the file path
        if (filePath.contains("Customer")) {
            this.activeList = customerList;
        } else if (filePath.contains("Vendor")) {
            this.activeList = vendorList;
        } else if (filePath.contains("Runner")) {
            this.activeList = runnerList;
        } else {
            this.activeList = userList;
        }

        loadUsersFromFile(filePath); // Load users from the specified file
    }

    /**
     * Loads all users from the text file into the appropriate list.
     */
    private void loadUsersFromFile(String filePath) {
        // Determine which list to clear and populate
        List<?> targetList = null;

        if (filePath.toLowerCase().contains("customer")) {
            targetList = customerList;
        } else if (filePath.toLowerCase().contains("vendor")) {
            targetList = vendorList;
        } else if (filePath.toLowerCase().contains("runner")) {
            targetList = runnerList;
        } else {
            targetList = userList;
        }

        // Clear the determined list
        if (targetList != null) {
            targetList.clear();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parse and add the object to the appropriate list
                if (filePath.toLowerCase().contains("customer") && targetList == customerList) {
                    CustomerModel customer = CustomerModel.fromString(line);
                    if (customer != null) {
                        customerList.add(customer);
                    }
                } else if (filePath.toLowerCase().contains("vendor") && targetList == vendorList) {
                    Vendor vendor = Vendor.fromString(line);
                    if (vendor != null) {
                        vendorList.add(vendor);
                    }
                } else if (filePath.toLowerCase().contains("runner") && targetList == runnerList) {
                    Runner runner = Runner.fromString(line);
                    if (runner != null) {
                        runnerList.add(runner);
                    }
                } else if (targetList == userList) { // For generic users
                    User user = User.fromString(line);
                    if (user != null) {
                        userList.add(user);
                    }
                }
            }

            // Debugging: Log the size of the loaded list
            if (filePath.toLowerCase().contains("customer")) {
                System.out.println("Loaded customers: " + customerList.size());
            } else if (filePath.toLowerCase().contains("vendor")) {
                System.out.println("Loaded vendors: " + vendorList.size());
            } else if (filePath.toLowerCase().contains("runner")) {
                System.out.println("Loaded runners: " + runnerList.size());
            } else {
                System.out.println("Loaded users: " + userList.size());
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }


    /**
     * Returns the currently active list based on the file path.
     */
    public ArrayList<?> getActiveList() {
        return activeList;
    }

    public boolean registerUser(
        String username,
        String phoneNumber,
        int role,            // 0=Manager,1=Admin,2=Vendor,3=Runner,4=Customer
        String password,
        String profilePic
    ) {
        // 1. Determine the prefix and the role file path
        String prefix;
        String roleFilePath;

        // By your mapping:
        // Manager         - 0 => prefix = "M", file = Manager.txt
        // Administrator   - 1 => prefix = "A", file = Administrator.txt
        // Vendors         - 2 => prefix = "V", file = Vendor.txt
        // Delivery runner - 3 => prefix = "R", file = Runner.txt
        // Customer        - 4 => prefix = "C", file = Customer.txt
        
        

        switch (role) {
            // Uncomment when Manager and Admin registration is implemented
            case 0: // Manager
                prefix = "M";
                roleFilePath = FilePaths.MANAGER_FILE;
                break;
            case 1: // Administrator
                prefix = "A";
                roleFilePath = FilePaths.ADMIN_FILE;
                break;
            case 2: // Vendor
                prefix = "V";
                roleFilePath = FilePaths.VENDOR_FILE;
                break;
            case 3: // Runner
                prefix = "R";
                roleFilePath = FilePaths.RUNNER_FILE;
                break;
            case 4: // Customer
                prefix = "C";
                roleFilePath = FilePaths.CUSTOMER_FILE;
                break;
            default:
                System.err.println("Invalid role selected!");
                return false;
        }

        // 2. Generate the next ID for that role
        String newID = IdGenerator.getNextRoleID(prefix, roleFilePath);

        // 3. Hash the password (or keep plain if you must—NOT recommended)
        String hashedPassword = Encryption.hashPassword(password);

        // 4. Prepare any default fields
        double defaultRevenueOrCredit = 0.00;  // by requirement
        String addressStreet = "";
        String addressPostcode = "";
        String addressState = "";
        boolean defaultAvailable = true; // for Vendor, etc.

        // 5. Write to the role file 
        // Depending on the role, the data format can differ:
        String roleLine = "";
        String stallName = username + "'s Stall"; // Vendor Default Stall Name
        String IMAGES_FOLDER = "src/foodhub/images/";

        switch (role) {
            case 0: // Manager => Manager_ID, Username, Phone
                roleLine = newID + " | " + username + " | " + phoneNumber;
                break;
                
            case 1: // Admin => Admin_ID, Username, Phone
                roleLine = newID + " | " + username + " | " + phoneNumber;
                break;
                
            case 2: // Vendor => Vendor_ID, Username, Stall Name, Revenue, Available, Profile Picture
                if (profilePic == null || profilePic.isBlank()) {
                    profilePic = "default.png";
                }
                roleLine = newID + " | " + username + " | "
                        + stallName + " | "
                        + String.format("%.2f", defaultRevenueOrCredit) + " | "
                        + defaultAvailable + " | "
                        + profilePic + " | "
                        + "It is " + newID;
                break;

            case 3: // Runner => Runner_ID, Username, Revenue, Profile Picture
                if (profilePic == null || profilePic.isBlank()) {
                    profilePic = "default.png";
                }
                roleLine = newID + " | " + username + " | " 
                        + String.format("%.2f", defaultRevenueOrCredit) + " | " 
                        + profilePic;
                break;

            case 4: // Customer => Customer_ID, Username, Credit, Street, Postcode, State, Profile Picture
                if (profilePic == null || profilePic.isBlank()) {
                    profilePic = "default.png";
                }
                roleLine = newID + " | " + username + " | "
                        + String.format("%.2f", 0.00) + " | "
                        + addressStreet + " | " + addressPostcode + " | " 
                        + addressState + " | " 
                        + profilePic;
                break;

            default:
                System.out.println("❌ Error: Invalid Role Detected!");
        }

        // Actually write the line to the specific role file
        if (!appendLineToFile(roleFilePath, roleLine)) {
            return false; // if something failed, abort
        }

        // 6. Generate the global ID for User.txt
        String globalID = IdGenerator.getNextRoleID("U", filePath);

        // 7. Write to the User.txt (the global file for this UserManager instance)
        User user = new User(
            globalID,           // 1) The global user ID
            newID,              // 2) The role-specific ID
            username, 
            phoneNumber, 
            String.valueOf(role), 
            hashedPassword, 
            profilePic
        );

        // Convert the User object to the file format string
        String userLine = user.toFileFormat();

        // Write to the User.txt file
        if (!appendLineToFile(filePath, userLine)) {
            return false; // if something failed
        }

        // If all good
        return true;

    }
    
    /**
     * Utility method to append a line to a text file.
     */
    private boolean appendLineToFile(String filePath, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(line);
            bw.newLine();  // Ensure it properly ends with a new line
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }




    /**
     * Writes all users in userList back to the text file (overwrites it).
     */
    private void saveUsersToFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            List<?> listToSave;

            // Determine the correct list based on the file path
            if (filePath.toLowerCase().contains("customer")) {
                listToSave = customerList;
            } else if (filePath.toLowerCase().contains("vendor")) {
                listToSave = vendorList;
            } else if (filePath.toLowerCase().contains("runner")) {
                listToSave = runnerList;
            } else {
                listToSave = userList;
            }

            // Save each entry in the determined list
            for (Object obj : listToSave) {
                if (obj instanceof User) {
                    bw.write(((User) obj).toFileFormat());
                } else if (obj instanceof CustomerModel) {
                    bw.write(((CustomerModel) obj).toFileFormat());
                } else if (obj instanceof Vendor) {
                    bw.write(((Vendor) obj).toFileFormat());
                } else if (obj instanceof Runner) {
                    bw.write(((Runner) obj).toFileFormat());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving users to " + filePath + ": " + e.getMessage());
        }
    }


    /**
     * Finds and returns a user by their userID. Returns null if not found.
     */
    public User getUserById(String userID) {
        for (User user : userList) {
            if (user.getUserID().equalsIgnoreCase(userID)) {
                return user;
            }
        }
        return null;
    }
    
    public CustomerModel getCustomerById(String customerID) {
        for (CustomerModel customer : customerList) {
            if (customer.getCustomerID().equalsIgnoreCase(customerID)) {
                return customer;
            }
        }
        return null;
    }
    
    public Vendor getVendorById(String vendorID) {
        for (Vendor vendor : vendorList) {
            if (vendor.getVendorID().equalsIgnoreCase(vendorID)) {
                return vendor;
            }
        }
        return null;
    }

    public Runner getRunnerById(String runnerID) {
        for (Runner runner : runnerList) {
            if (runner.getRunnerID().equalsIgnoreCase(runnerID)) {
                return runner;
            }
        }
        return null;
    }
    
    /**
     * Updates an existing user in the list and saves changes to the file.
     */
    public boolean updateUser(User updatedUser) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserID().equalsIgnoreCase(updatedUser.getUserID())) {
                userList.set(i, updatedUser);
                saveUsersToFile(filePath);
                return true;
            }
        }
        return false; // User not found
    }

    
    public boolean updateCustomer(CustomerModel updatedCustomer) {
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getCustomerID().equalsIgnoreCase(updatedCustomer.getCustomerID())) {
                customerList.set(i, updatedCustomer);
                saveUsersToFile(filePath);
                return true;
            }
        }
        return false; // User not found
    }
    
    public boolean updateVendor(Vendor updatedVendor) {
        for (int i = 0; i < vendorList.size(); i++) {
            if (vendorList.get(i).getVendorID().equalsIgnoreCase(updatedVendor.getVendorID())) {
                vendorList.set(i, updatedVendor);
                saveUsersToFile(filePath);
                return true;
            }
        }
        return false; // User not found
    }
    
    public boolean updateRunner(Runner updatedRunner) {
        for (int i = 0; i < runnerList.size(); i++) {
            if (runnerList.get(i).getRunnerID().equalsIgnoreCase(updatedRunner.getRunnerID())) {
                runnerList.set(i, updatedRunner);
                saveUsersToFile(filePath);
                return true;
            }
        }
        return false; // User not found
    }

    /**
     * Removes a user by userID and saves the updated list to the file.
     */
    /**
    * Removes a user from User
    */
   public boolean removeUser(String userID) {
       List<String> updatedLines = new ArrayList<>();
       boolean removed = false;

       try (BufferedReader br = new BufferedReader(new FileReader(FilePaths.USER_FILE))) {
           String line;
           while ((line = br.readLine()) != null) {
               if (line.startsWith(userID + " |")) {
                   removed = true;
                   continue;
               }
               updatedLines.add(line);
           }
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }

       // Write updated content back to User.txt
       try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePaths.USER_FILE))) {
           for (String line : updatedLines) {
               bw.write(line);
               bw.newLine();
           }
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }

       return removed;
   }

    public boolean removeCustomer(String customerID) {
        boolean removed = customerList.removeIf(user -> user.getCustomerID().equalsIgnoreCase(customerID));
        if (removed) {
            saveUsersToFile(filePath);
        }
        return removed;
    }

    public boolean removeVendor(String vendorID) {
        boolean removed = vendorList.removeIf(user -> user.getVendorID().equalsIgnoreCase(vendorID));
        if (removed) {
            saveUsersToFile(filePath);
        }
        return removed;
    }

    public boolean removeRunner(String runnerID) {
        boolean removed = runnerList.removeIf(user -> user.getRunnerID().equalsIgnoreCase(runnerID));
        if (removed) {
            saveUsersToFile(filePath);
        }
        return removed;
    }

    /**
     * Returns a copy of the current user list for reading.
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(userList);
    }
    
    public List<CustomerModel> getAllCustomers() {
        List<CustomerModel> customers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FilePaths.CUSTOMER_FILE))) { // Explicitly use CUSTOMER_FILE
            String line;
            while ((line = br.readLine()) != null) {
                CustomerModel c = CustomerModel.fromString(line); // Parse the line as a Customer
                if (c != null) {
                    customers.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FilePaths.VENDOR_FILE))) { // Explicitly use VENDOR_FILE
            String line;
            while ((line = br.readLine()) != null) {
                Vendor v = Vendor.fromString(line); // Parse the line as a Vendor
                if (v != null) {
                    vendors.add(v);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vendors;
    }



    public List<Runner> getAllRunners() {
        List<Runner> runners = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FilePaths.RUNNER_FILE))) { // Explicitly use RUNNER_FILE
            String line;
            while ((line = br.readLine()) != null) {
                Runner r = Runner.fromString(line); // Parse the line as a Runner
                if (r != null) {
                    runners.add(r);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return runners;
    }




    /**
     * Checks if a username already exists.
     */
    public boolean isUsernameExists(String username) {
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Authenticates a user based on their username and password.
     */
    public boolean authenticate(String username, String password) {
        for (User user : userList) {
            if (user.matches(username, password)) {
                System.out.println("User authenticated successfully.");
                String role = user.getRole();
                if (role == null) {
                    dialogManager.showErrorMessage("Error! Unidentified Role. Please report to the administrator.");
                } else {
                    switch (role) {
                        case "0" -> {System.out.println("Manager logged in!"); new ManagerMenu().setVisible(true); setPersonID(FilePaths.MANAGER_FILE); }
                        case "1" -> {System.out.println("Admin logged in!"); new AdminMenu().setVisible(true); setPersonID(FilePaths.ADMIN_FILE); }
                        case "2" -> {System.out.println("Vendor logged in!" );
                        setPersonID(FilePaths.VENDOR_FILE); 
                         String vendorID = getVendorIDFromVendorFile(username); // Fetch vendorID from Vendor.txt
                            if (vendorID != null) {
                                System.out.println("Vendor " + vendorID + " logged in!");
                                SessionVendorID cucumber = new SessionVendorID();
                                cucumber.setSessionid(vendorID);
                                SessionVendor.getInstance().setSessionID(vendorID);
                                 System.out.println("Authentication: Vendor ID successfully passed: " + cucumber.getSessionid());
                                new VendorMenu(vendorID, username).setVisible(true); // Pass vendor ID to VendorMenu
                                
                                
                            } else {
                                dialogManager.showErrorMessage("Vendor details not found in Vendor.txt.");
                            }}
                        
                        case "3" -> {
                            System.out.println("Runner logged in!");
                            String runnerID = getRunnerIDFromRunnerFile(username); // Fetch vendorID from Runner.txt
                            if (runnerID != null) {
                                System.out.println("Runner " + runnerID + " logged in!");
                                new RunnerMenu(runnerID, username).setVisible(true); // Pass vendor ID to VendorMenu
                            } else {
                                dialogManager.showErrorMessage("Vendor details not found in Vendor.txt.");
                            }
                        }
                        
                        case "4" -> {System.out.println("Customer logged in!"); new CustomerMenu(username).setVisible(true); setPersonID(FilePaths.CUSTOMER_FILE); }
                        default -> dialogManager.showInfoMessage("Invalid role. Please contact support.");
                    }
                }
                return true;
            }
        }
        System.out.println("Authentication failed.");
        return false;
    }
    
    private String getRunnerIDFromRunnerFile(String runnerName) {
        String runnerFilePath = FilePaths.RUNNER_FILE;
        try (BufferedReader br = new BufferedReader(new FileReader(runnerFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    String fileUsername = parts[1].trim();
                    if (fileUsername.equalsIgnoreCase(runnerName)) {
                        return parts[0].trim(); // Return the Vendor_ID
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Vendor.txt: " + e.getMessage());
        }
        return null;
    }
    
    public void setPersonID(String filePath) {
        
        DialogManager message = new DialogManager("Error: "); 
        
         try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2) { 
                    String id = parts[0].trim();
                   
                    try {
                         SessionID session = new SessionID();
                        session.setSessionid(id);
                        SessionManager.getInstance().setSessionID(id);
                        System.out.println("successfully settle session");
                        System.out.println("Session ID: " + id);
                    } catch (HeadlessException e) {
                        message.showErrorBackend(1);
                    }
                    
                }
            }
        } catch (IOException e) {
             message.showErrorBackend(2);
        }
        }
    

    // Pass vendorID function (added)
    private String getVendorIDFromVendorFile(String username) {
        String vendorFilePath = FilePaths.VENDOR_FILE;
        try (BufferedReader br = new BufferedReader(new FileReader(vendorFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    String fileUsername = parts[1].trim();
                    if (fileUsername.equalsIgnoreCase(username)) {
                        return parts[0].trim(); // Return the Vendor_ID
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Vendor.txt: " + e.getMessage());
        }
        return null;
    }
    
    /**
    * Retrieves a user object by username.
    */
   public User getUserByUsername(String username) {
       for (User user : userList) {
           if (user.getUsername().equalsIgnoreCase(username)) {
               return user;
           }
       }
       return null;
   }

    // Helper: removes a user from a role file based on the role ID (assumed to be in column 0).
    public boolean removeUserFromRoleFile(String roleFile, String roleID) {
        List<String> updatedLines = new ArrayList<>();
        boolean removed = false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(roleFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\|");
                if (tokens.length > 0) {
                    String fileRoleID = tokens[0].trim(); // First token should be the role ID.
                    if (fileRoleID.equalsIgnoreCase(roleID)) {
                        removed = true;
                        continue; // Skip this line (i.e. remove the user).
                    }
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        // Write the updated lines back to the file.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(roleFile))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return removed;
    }

    /**
     * Formats the user entry according to the role file format.
     * Only retrieves extra fields if the role requires them.
     */
    private String formatUserEntry(User user) {
        // Managers for each user type (if needed)
        UserManager CustomerManager = new UserManager(FilePaths.CUSTOMER_FILE);
        UserManager VendorManager   = new UserManager(FilePaths.VENDOR_FILE);
        UserManager RunnerManager   = new UserManager(FilePaths.RUNNER_FILE);

        // Basic user data
        String roleCode   = user.getRole();
        String roleID     = user.getRoleID();  // e.g., "A1", "C2", "V5"
        String username   = user.getUsername();
        String phoneNumber = user.getPhone();
        String profilePic = (user.getProfilePic() != null) ? user.getProfilePic() : "default.png";

        // Step 1: Ensure `roleID` does NOT have duplicate prefixes
        if (roleID.matches("^[MAVRC]\\d+$")) {
            roleID = roleID.substring(1);  // Remove first character if it's M/A/V/R/C
        }

        // Step 2: Determine the correct role prefix
        String prefix;
        switch (roleCode) {
            case "0": prefix = "M"; break; // Manager
            case "1": prefix = "A"; break; // Admin
            case "2": prefix = "V"; break; // Vendor
            case "3": prefix = "R"; break; // Runner
            case "4": prefix = "C"; break; // Customer
            default:  prefix = "";   break;
        }

        // Step 3: Construct the final role ID (prefix + cleaned numeric ID)
        String finalRoleID = prefix + roleID;

        // Default/Optional fields
        String stallName      = "No Data";
        double revenueOrCredit= 0.00;
        String available      = "false";
        String addressStreet  = "No Data";
        String addressPostcode= "No Data";
        String addressState   = "No Data";

        // Fetch additional fields for certain roles
        if ("4".equals(roleCode)) { // Customer
            CustomerModel customer = CustomerManager.getCustomerById(user.getRoleID());
            if (customer != null) {
                revenueOrCredit = customer.getCredit();
                addressStreet   = (customer.getStreet()    != null) ? customer.getStreet()    : "No Data";
                addressPostcode = (customer.getPostcode()  != null) ? customer.getPostcode()  : "No Data";
                addressState    = (customer.getState()     != null) ? customer.getState()     : "No Data";
            }
        } else if ("2".equals(roleCode)) { // Vendor
            Vendor vendor = VendorManager.getVendorById(user.getRoleID());
            if (vendor != null) {
                stallName       = (vendor.getStallName()   != null) ? vendor.getStallName()    : "No Data";
                revenueOrCredit = vendor.getRevenue();
                available       = vendor.getAvailable();
            }
        }

        // Step 4: Build the formatted output with the corrected role ID
        switch (roleCode) {
            case "0": // Manager
                return finalRoleID + " | " + username + " | " + phoneNumber;

            case "1": // Admin
                return finalRoleID + " | " + username + " | " + phoneNumber;

            case "2": // Vendor
                return finalRoleID + " | " + username + " | " + stallName + " | "
                       + String.format("%.2f", revenueOrCredit) + " | " + available + " | " + profilePic;

            case "3": // Runner
                return finalRoleID + " | " + username + " | "
                       + String.format("%.2f", revenueOrCredit) + " | " + profilePic;

            case "4": // Customer
                return finalRoleID + " | " + username + " | "
                       + String.format("%.2f", revenueOrCredit) + " | "
                       + addressStreet + " | " + addressPostcode + " | " + addressState + " | " + profilePic;

            default:
                return "";
        }
    }

    public static String getRoleFilePath(String roleID) {
        if (roleID.startsWith("A")) return FilePaths.ADMIN_FILE;
        if (roleID.startsWith("C")) return FilePaths.CUSTOMER_FILE;
        if (roleID.startsWith("V")) return FilePaths.VENDOR_FILE;
        if (roleID.startsWith("R")) return FilePaths.RUNNER_FILE;
        if (roleID.startsWith("M")) return FilePaths.MANAGER_FILE;
        return null;
    }

    public static boolean removeUserFromGlobalFile(String globalID) {
        File inputFile = new File(FilePaths.USER_FILE);
        File tempFile = new File(FilePaths.USER_FILE + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            boolean found = false;
            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split(",");
                if (data.length > 0 && data[0].trim().equals(globalID)) {
                    found = true;
                    continue;
                }
                writer.write(currentLine + System.lineSeparator());
            }

            if (!found) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return inputFile.delete() && tempFile.renameTo(inputFile);
    }


    public static boolean updateRoleUser(String roleFile, String roleID, User user) {
        List<String> updatedLines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(roleFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(roleID + " | ")) {
                    updatedLines.add(roleID + " | " + user.getUsername() + " | " + user.getPhone());
                    updated = true;
                    continue;
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Write updated content back to role file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(roleFile))) {
            for (String line : updatedLines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return updated;
    }
}
