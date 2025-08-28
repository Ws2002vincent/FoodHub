/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

import static foodhub.FilePaths.RUNNER_FILE;
import static foodhub.FilePaths.JOINEDRUNNERPATING_FILE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class runnerManager {
    DialogManager dialogManagerError = new DialogManager("Error Occured!");
    DialogManager dialogManagerNotice = new DialogManager("Notice!");
    
    private double totalrevenue;
    int revenueThreshold;  

    public int getRevenueThreshold() {
        return revenueThreshold;
    }

    public void setRevenueThreshold(int revenueThreshold) {
        this.revenueThreshold = revenueThreshold;
    }
    
    

    public double getTotalrevenue() {
        return totalrevenue;
    }

    public void setTotalrevenue(double totalrevenue) {
        this.totalrevenue = totalrevenue;
    }
    
    static class RunnerModel {
        String id;
        String name;
        double revenue;
        double rating;
        
        public RunnerModel (String id, String name, double revenue, double rating) {
            this.id = id;
            this.name = name;
            this.revenue = revenue;
           
        }
        
        public double getRevenue() {
            return revenue;
        }
        
         public String getUsername() {
            return name;
        }
         
        public double getRating() {
            return rating;
        }
    }
    
    public static List<runnerManager.RunnerModel> readRunnersFromFile(String filePath) {
        List<runnerManager.RunnerModel> runnermodel = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(JOINEDRUNNERPATING_FILE))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                
                String[] parts = line.split("\\|");
                
                if (parts.length >= 4) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    double revenue = Double.parseDouble(parts[2].trim());
                    double rating = Double.parseDouble(parts[3].trim());
                    
                    runnermodel.add(new runnerManager.RunnerModel(id, name, rating, revenue));
                    System.out.println("""
                                       Successfully added
                                        runner id: """ + id + "\n" + "Name: " + name + "\n" + "revenue: " + revenue + "\n");
                    
                }
            }
        } catch (IOException e) {
            DialogManager dialogManager = new DialogManager("No runners found?");
             dialogManager.showErrorMessage("Please report to the Administrator");     
        }

        return runnermodel;
    }
    
    public static double calculateTotalRevenue(List<RunnerModel> runnermodel) {
      return runnermodel.stream().mapToDouble(RunnerModel::getRevenue).sum();                                      
    }
    
    public void getRevenueActive() {
        
        
        List<RunnerModel> runners = readRunnersFromFile(RUNNER_FILE);

        double totalRevenueForRole3 = calculateTotalRevenue(runners);
        System.out.println("Total Revenue for role Runner: " + totalRevenueForRole3);
        
        this.setTotalrevenue(totalRevenueForRole3);
        System.out.println("Successfully set totalRevenue to: " + this.getTotalrevenue());
        
        
    }
    
     public static RunnerModel findHighestRevenueUser(List<RunnerModel> runnermodels ) {
        RunnerModel  highestRevenueUser = null;
        for (RunnerModel runnermodel: runnermodels) {
                if (highestRevenueUser == null || runnermodel.getRevenue() > highestRevenueUser.getRevenue()) {
                    highestRevenueUser = runnermodel;
            }
        }
            return highestRevenueUser;
     }
     
    class RunnerTable {
        private String runnerid;
        private String username;
        private String rating;
        private String revenue;

    public RunnerTable(String runnerid, String username, String rating, String revenue) {
        this.runnerid = runnerid;
        this.username = username;
        this.revenue = revenue;
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public String getRating() {
        return rating;
    }
    
    public String getRunnerid() {
        return runnerid;
    }

    public String getRevenue() {
        return revenue;
    }
    
    @Override
    public String toString() {
        return runnerid + " | " + username + " | " + rating +   " | " + revenue;
        }
    }
    
    private List<RunnerTable> readRunnersFromFile() {
        List<RunnerTable> runners = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(JOINEDRUNNERPATING_FILE))) {
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] fields = currentLine.split("\\|");
                    RunnerTable runner = new RunnerTable(
                        fields[0].trim(),    // userId
                        fields[1].trim(),    // username
                        fields[2].trim(),   //rating
                        fields[3].trim()    // revenue
                        
                    );
                    runners.add(runner);
                
            }
        } catch (IOException e) {
            
            dialogManagerError.showErrorMessage("Cannot read the files, Please report to the Administrator");     
        }

        return runners;
    }
    
    public void loadRunnersToTable(DefaultTableModel model) {
        List<RunnerTable> runners = readRunnersFromFile();
        model.setRowCount(0);
        for (RunnerTable runner : runners) {
            Object[] rowData = {
                runner.getRunnerid(),
                runner.getUsername(),
                runner.getRating(),
                runner.getRevenue()
            };
            model.addRow(rowData);
        }
    }
    
   public void loadRunnersToTable2(DefaultTableModel model, String revenueFilter) {
    List<RunnerTable> runners = readRunnersFromFile();
    model.setRowCount(0); // Clear existing rows

    // Check if a revenue filter is selected
    double minRevenue = 0;
    double maxRevenue = Integer.MAX_VALUE; // No filter if it's not set

    // Check if the revenue filter is a range
    if (revenueFilter != null && !revenueFilter.equals("all")) {
        // Extract min and max revenue based on the filter
        String[] parts = revenueFilter.split("-");
        minRevenue = Double.parseDouble(parts[0]);
        maxRevenue = Double.parseDouble(parts[1]);
    }

    // Iterate over runners and apply both status and revenue filters
    for (RunnerTable runner : runners) {
        // Convert the revenue from String to int
        double revenue = 0;
        try {
            revenue = Double.parseDouble(runner.getRevenue());
        } catch (NumberFormatException e) {
            System.out.println("Invalid revenue value: " + runner.getRevenue());
            continue; // Skip this runner if revenue is not a valid number
        }
        if ( revenue > revenueThreshold) {
            // Apply the revenue range filter
            if (revenue >= minRevenue && revenue <= maxRevenue) {
                Object[] rowData = {
                    runner.getRunnerid(),
                    runner.getUsername(),
                    runner.getRating(),
                    revenue
                };
                model.addRow(rowData);
            }
        }
    }
}
   
   public void loadRunnersToTable3(DefaultTableModel model, String revenueFilter) {
    List<RunnerTable> runners = readRunnersFromFile();
    model.setRowCount(0); // Clear existing rows

    // Check if a revenue filter is selected
    double minRevenue = 0;
    double maxRevenue = Integer.MAX_VALUE; // No filter if it's not set

    // Check if the revenue filter is a range
    if (revenueFilter != null && !revenueFilter.equals("all")) {
        // Extract min and max revenue based on the filter
        String[] parts = revenueFilter.split("-");
        minRevenue = Double.parseDouble(parts[0]);
        maxRevenue = Double.parseDouble(parts[1]);
    }

    // Iterate over runners and apply both status and revenue filters
    for (RunnerTable runner : runners) {
        // Convert the revenue from String to int
        double rating = 0;
        try {
           rating = Double.parseDouble(runner.getRating());
        } catch (NumberFormatException e) {
            System.out.println("Invalid revenue value: " + runner.getRating());
            continue; // Skip this runner if revenue is not a valid number
        }
        if ( rating > revenueThreshold) {
            // Apply the revenue range filter
            if (rating >= minRevenue && rating <= maxRevenue) {
                Object[] rowData = {
                    runner.getRunnerid(),
                    runner.getUsername(),
                    runner.getRevenue(),
                    rating
                };
                model.addRow(rowData);
            }
        }
    }
}


}


     

    



