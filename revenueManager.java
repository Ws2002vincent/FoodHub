
package foodhub;

import static foodhub.FilePaths.USER_FILE;
import static foodhub.FilePaths.VENDOR_FILE;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class revenueManager {
private double totalrevenue;
private String username;
private double highestrevenue;
private int vendorcount;
DialogManager dialogManagerError = new DialogManager("Error Occured!");
DialogManager dialogManagerNotice = new DialogManager("Notice!");

    public int getVendorcount() {
        return vendorcount;
    }

    public void setVendorcount(int vendorcount) {
        this.vendorcount = vendorcount;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getHighestrevenue() {
        return highestrevenue;
    }

    public void setHighestrevenue(double highestrevenue) {
        this.highestrevenue = highestrevenue;
    }

    public double getTotalrevenue() {
        return totalrevenue;
    }

    public void setTotalrevenue(double totalrevenue) {
        this.totalrevenue = totalrevenue;
    }



 static class tempoUser {
        String id;
        String name;
        String phone;
        int role;
        
        public tempoUser(String id, String name, String phone, int role) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.role = role;
           
        }
        
        public int getRole() {
            return role;
        }
        
         public String getUsername() {
            return name;
        }
    }
 
  // Method to read the file and parse the data into a list of User objects
    public static List<tempoUser> readUsersFromFile(String filePath) {
        List<tempoUser> tempousers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                
                String[] parts = line.split("\\|");
                
                if (parts.length >= 6) {
                    String id = parts[0].trim();
                    String name = parts[2].trim();
                    String phone = parts[3].trim();
                    int role = Integer.parseInt(parts[4].trim());
                    
                    tempousers.add(new tempoUser(id, name, phone, role));
                    System.out.println("Successfully added\n" + "Name: " + name + "\n" + "role: " + role + "\n");
                    
                }
            }
        } catch (IOException e) {
            DialogManager dialogManager = new DialogManager("No vendors found?");
             dialogManager.showInfoMessage("The file may not contained any vendor data, or the file may be corrupted.");     
        }

        return tempousers;
    }

 //calculate the total revenue for a specific role
    public static double calculateTotalRevenue(List<VendorModel> vendormodels) {
      return vendormodels.stream().mapToDouble(VendorModel::getRevenue).sum();                                      
    }
    
    //user with the highest revenue for vendor
    public static VendorModel findHighestRevenueUser(List<VendorModel> vendormodels ) {
        VendorModel highestRevenueUser = null;

        for (VendorModel vendormodel : vendormodels) {
                if (highestRevenueUser == null || vendormodel.getRevenue() > highestRevenueUser.getRevenue()) {
                    highestRevenueUser = vendormodel;
            }
        }

        return highestRevenueUser;
    }
 
    public void getRevenueActive() {
        
        
        List<VendorModel> vendors = readVendorsFromFile();

        double totalRevenueForRole2 = calculateTotalRevenue(vendors);
        System.out.println("Total Revenue for role Vendor: " + totalRevenueForRole2);
        
        this.setTotalrevenue(totalRevenueForRole2);
        System.out.println("Successfully set totalRevenue to: " + this.getTotalrevenue());
        
        
    }
    
    public void getBestSeller() {
        
        List<VendorModel> vendors = readVendorsFromFile();
        
         VendorModel highestRevenueUser = findHighestRevenueUser(vendors);
        
         if (highestRevenueUser != null) 
         {
                System.out.println("User with the highest revenue (Role 2):");
                System.out.println("Username: " + highestRevenueUser.getUsername());
                System.out.println("Revenue: " + highestRevenueUser.getRevenue());
                
                this.username = highestRevenueUser.getUsername();
                this.highestrevenue = highestRevenueUser.getRevenue();
            } 
         else 
         {
                System.out.println("No user with the specified role found.");
                
                dialogManagerError.showInfoMessage("The file may not contained any vendor data, or the file may be corrupted.");     
            }
    }
    
    //count the number of users with a specific role
    public static int countUsersByRole(List<tempoUser> tempousers, int targetRole) {
        int count = 0;

        for (tempoUser tempouser : tempousers) {
            if (tempouser.getRole() == targetRole) {
                count++;
            }
        }

        return count;
    }
    
    public void getVendorCount() {
            
        List<VendorModel> vendors = readVendorsFromFile();
        int totalVendorCount = vendors.size();  
        System.out.println("Total number of vendors: " + totalVendorCount);
        this.vendorcount = totalVendorCount;
            
    }
    
    
  public void loadVendorsToTable(DefaultTableModel model, String statusFilter) {
    List<VendorModel> vendors = readVendorsFromFile();
    model.setRowCount(0); // Clear existing rows

    // Iterate over vendors and filter by status
    for (VendorModel vendor : vendors) {
        // If no statusFilter is provided (null or empty), load all vendors
        if (statusFilter == null || statusFilter.isEmpty() || statusFilter.equalsIgnoreCase(vendor.getStatus())) {
            Object[] rowData = {
                vendor.getUserId(),
                vendor.getUsername(),
                vendor.getRevenue(),
                vendor.getStatus()
            };
            model.addRow(rowData);
        }
    }
}
    
    private List<VendorModel> readVendorsFromFile() {
    List<VendorModel> vendors = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(VENDOR_FILE))) {
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            String[] fields = currentLine.split("\\|");

            
            if (fields.length >= 6) {
                String status = fields[4].trim().equals("true") ? "active" : "inactive";

                VendorModel vendor = new VendorModel(
                    fields[0].trim(),    // userId
                    fields[1].trim(),    // username
                    Double.parseDouble(fields[3].trim()),    // revenue
                    status //activation
                );
                vendors.add(vendor);
            } else {
                System.err.println("Skipping invalid line: " + currentLine);
            }
        }
    } catch (IOException e) {
        dialogManagerError.showInfoMessage("The file may not contain any vendor data, or the file may be corrupted.");
    }

    return vendors;
}
}
