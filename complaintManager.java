package foodhub;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class complaintManager {
    private final String filePath;
    private final List<complaintModel> complaints;
    
    public complaintManager(String filePath) {
        this.filePath = filePath;
        this.complaints = new ArrayList<>();
        loadComplaints();
    }

    // Load complaints from the file
    private void loadComplaints() {
        complaints.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    complaints.add(new complaintModel(parts[0], parts[1], parts[2], parts[3], parts[4].equals("null") ? null : parts[4]));

                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Cannot load Complaints from database!", "Error loading database", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Save complaints back to the file
    public void saveComplaints() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (complaintModel complaint : complaints) {
                writer.write(String.format("%s|%s|%s|%s|%s%n",
                        complaint.getComplainID(),
                        complaint.getUsername(),
                        complaint.getCategory(),
                        complaint.getComment(),
                        complaint.getAnswer() == null ? "null" : complaint.getAnswer()));
            }
        } catch (IOException e) {
           JOptionPane.showMessageDialog(null, "Cannot save reply to database!", "Error saving reply", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Get all complaints
    public List<complaintModel> getComplaints() {
        return complaints;
    }

    public List<complaintModel> filterComplaints(String category, String answerStatus) {
    List<complaintModel> filtered = new ArrayList<>();
    
    System.out.println("Filtering by category: " + category);
    
    for (complaintModel complaint : complaints) {
        // Debugging each category being checked
        System.out.println("Checking category: " + complaint.getCategory());
        System.out.println("Checking if its null: '" + complaint.getAnswer() + "'");
        
        boolean matchesCategory = category.equals("All") || 
                                  complaint.getCategory().trim().equalsIgnoreCase(category.trim());

       boolean matchesAnswer = answerStatus.equals("All") ||
                (answerStatus.equals("Resolved") && complaint.getAnswer() != null && !"null".equals(complaint.getAnswer().trim())) ||
                (answerStatus.equals("Unresolve") && (complaint.getAnswer() == null || "null".equalsIgnoreCase(complaint.getAnswer().trim())));

        if (matchesCategory && matchesAnswer) {
            filtered.add(complaint);
        } else {
            System.out.println("No one matches the requirement");
        }
    }

    System.out.println("Number of complaints displayed: " + filtered.size());
    return filtered;
}
}
