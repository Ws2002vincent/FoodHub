package foodhub;

import static foodhub.FilePaths.RUNNER_FILE;
import static foodhub.FilePaths.JOINEDRUNNERPATING_FILE;
import static foodhub.FilePaths.DELIVERIES_FILE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class runnerRating extends runnerManager {
        private Map<String, Double> totalRatings; 
        private Map<String, Integer> ratingCounts;
        private String bestrunner;
        private double bestrated;
        private String name;
        private String averagerating;
        private String rowCount = countNonEmptyRows(RUNNER_FILE );
        DialogManager dialogManagerError = new DialogManager("Error Occured!");
        DialogManager dialogManagerNotice = new DialogManager("Notice!");

        // Constructor
        public runnerRating() {
            this.totalRatings = new HashMap<>();
            this.ratingCounts = new HashMap<>();
        }

    public String getRowCount() {
        return rowCount;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getBestrunner() {
        return bestrunner;
    }

    public void setBestrunner(String bestrunner) {
        this.bestrunner = bestrunner;
    }

    public double getBestrated() {
        return bestrated;
    }

    public void setBestrated(double bestrated) {
        this.bestrated = bestrated;
    }

    public String getAveragerating() {
        return averagerating;
    }
    
    public void setAveragerating(String averagerating) {
        this.averagerating = averagerating;
    }
        // Method to add a rating
        public void addRating(String runnerId, double rating) {
            totalRatings.put(runnerId, totalRatings.getOrDefault(runnerId, 0.0) + rating);
            ratingCounts.put(runnerId, ratingCounts.getOrDefault(runnerId, 0) + 1);
        }

        // Method to calculate the average rating for a specific runner
        public double getAverageRating(String runnerId) {
            if (!totalRatings.containsKey(runnerId)) {
                return 0.0; // Runner not found
            }
            return totalRatings.get(runnerId) / ratingCounts.get(runnerId);
        }

        // Method to calculate the average ratings for all runners
        public Map<String, Double> getAllAverageRatings() {
            Map<String, Double> averageRatings = new HashMap<>();
            for (String runnerId : totalRatings.keySet()) {
                averageRatings.put(runnerId, getAverageRating(runnerId));
            }
            return averageRatings;
        }

        // Method to find the best-rated runner (highest average rating)
        public String getBestRatedRunner() {
            String bestRunner = null;
            double highestAverage = 0.0;

            for (String runnerId : totalRatings.keySet()) {
                double average = getAverageRating(runnerId);
                if (average > highestAverage) {
                    highestAverage = average;
                    bestRunner = runnerId;
                }
            }

            return bestRunner;
        }
    
    
   public void getShowBestRunner() {
    runnerRating analyzer = new runnerRating();
    
    // Log the file path for debugging purposes
    System.out.println("Reading data from: " + DELIVERIES_FILE);
    
    try (BufferedReader br = new BufferedReader(new FileReader(DELIVERIES_FILE))) {
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("Reading line: " + line); 
            String[] parts = line.split("\\|");
            if (parts.length == 5) {  
                String runnerId = parts[1].trim(); 
                try {
                    double rating = Double.parseDouble(parts[3].trim()); // Rating (1-5)
                    analyzer.addRating(runnerId, rating);
                } catch (NumberFormatException e) {
                    dialogManagerError.showInfoMessage("Invalid rating format detected, database may be corrupted.");     
                    System.err.println("Invalid rating format: " + parts[3]);
                }
            } else {
                    dialogManagerError.showInfoMessage("Invalid line format detected, database may be corrupted."); 
                System.err.println("Invalid line format: " + line);
            }
        }
    } catch (IOException e) {
         
        dialogManagerError.showInfoMessage("Error reading the file, database may be corrupted."); 
        System.err.println("Error reading the file: " + e.getMessage());
        
    }

    // Retrieve and print the average rating 
    System.out.println("\nAverage Ratings for All Runners:");
    Map<String, Double> allAverageRatings = analyzer.getAllAverageRatings();
    
    // Calculate the overall average rating 
    double totalRatingSum = 0.0;
    int totalRunners = 0;

    if (allAverageRatings.isEmpty()) {
        dialogManagerNotice.showInfoMessage("The database does not contained any rating."); 
        System.out.println("No ratings found.");
    } else {
        for (Map.Entry<String, Double> entry : allAverageRatings.entrySet()) {
            System.out.println(entry.getKey() + ": " + String.format("%.2f", entry.getValue()));
            totalRatingSum += entry.getValue(); 
            totalRunners++; 
        }

        // Calculate the overall average rating for all users
        double overallAverageRating = (totalRunners > 0) ? (totalRatingSum / totalRunners) : 0.0;
        System.out.println("\nOverall Average Rating for All Runners: " + String.format("%.2f", overallAverageRating));
        this.averagerating = String.format("%.2f", overallAverageRating);
    }

    // Find and print the best-rated runner
    String bestRunner = analyzer.getBestRatedRunner();
    if (bestRunner != null) {
        this.bestrunner = bestRunner;
        this.bestrated = analyzer.getAverageRating(bestRunner);
        System.out.println("\nBest Rated Runner: " + bestRunner + " with an average rating of "
                + String.format("%.2f", analyzer.getAverageRating(bestRunner)));
    } else {
         
         dialogManagerNotice.showInfoMessage("No rating available, the database has no rating records."); 
        System.out.println("No ratings available.");
    }

    // Call method to generate the joined runner data
    generateJoinedRunnerData(allAverageRatings);
}
    // Method to generate the joined data 
    public void generateJoinedRunnerData(Map<String, Double> allAverageRatings) {
    Map<String, RunnerInfo> runnerInfo = new HashMap<>();

    
    try (BufferedReader br = new BufferedReader(new FileReader(RUNNER_FILE))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length == 4) {
                String runnerId = parts[0].trim();
                String username = parts[1].trim();
                String revenue = parts[2].trim();
                runnerInfo.put(runnerId, new RunnerInfo(username, revenue)); 
            }
        }
    } catch (IOException e) {
         dialogManagerError.showInfoMessage("Error reading the file, database may be corrupted."); 
        System.err.println("Error reading Runner.txt: " + e.getMessage());
    }

    // List to store the final joined data 
    List<String> joinedDataList = new ArrayList<>();
    for (Map.Entry<String, Double> entry : allAverageRatings.entrySet()) {
        String runnerId = entry.getKey();
        double averageRating = entry.getValue();
        RunnerInfo info = runnerInfo.get(runnerId);

        if (info != null) {
            String joinedData = runnerId + " | " + info.getUsername() + " | " + String.format("%.2f", averageRating) + " | " + info.getRevenue();
            joinedDataList.add(joinedData);
        }
    }

    // Sort the list based on runnerId 
    joinedDataList.sort((data1, data2) -> {
        String runnerId1 = data1.split(" | ")[0].trim(); 
        String runnerId2 = data2.split(" | ")[0].trim(); 
        int id1 = Integer.parseInt(runnerId1.substring(1)); 
        int id2 = Integer.parseInt(runnerId2.substring(1)); 
        return Integer.compare(id1, id2); 
    });

    // Write the sorted data back to the file (overwrite the existing file)
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(JOINEDRUNNERPATING_FILE , false))) {
        for (String joinedData : joinedDataList) {
            writer.write(joinedData);
            writer.newLine();
        }
        System.out.println("Sorted joined data has been written to " + JOINEDRUNNERPATING_FILE );
    } catch (IOException e) {
         dialogManagerError.showInfoMessage("Error reading the file, database may be corrupted."); 
        System.err.println("Error writing to the output file: " + e.getMessage());
    }
}
    
    public void getUsername() {
        try (BufferedReader br = new BufferedReader(new FileReader(RUNNER_FILE))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length == 4) {  
                if (this.bestrunner.equals(parts[0].trim()) )
                this.name = parts[1].trim();
                System.out.println("The best runner is : " + this.name);
            } 
        }
    } catch (IOException e) {
         dialogManagerError.showInfoMessage("Error reading the file, database may be corrupted."); 
        System.err.println("Error reading the file: " + e.getMessage());

    }
        
    }
    
    
public class RunnerInfo {
    private String username;
    private String revenue;
    

    public RunnerInfo(String username, String revenue) {
        this.username = username;
        this.revenue = revenue;
    }

    public String getUsername() {
        return username;
    }

    public String getRevenue() {
        return revenue;
    }

    @Override
    public String toString() {
        return username + " | " + revenue;
    }
}

 public static String countNonEmptyRows(String filePath) {
        DialogManager dialogManagerError = new DialogManager("Error Occured!");
        int rowCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    rowCount++;
                }
            }
        } catch (IOException e) {
            
            dialogManagerError.showInfoMessage("Error reading the file, database may be corrupted."); 
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return String.valueOf(rowCount);
    }
   

}

