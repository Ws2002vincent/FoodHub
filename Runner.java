package foodhub;

/**
 *
 * @author User
 */
public class Runner extends User{
    private String runnerID;
    private String username;
    private double revenue;
    private String profilePic; // New field for profile picture

    // Constructor
    public Runner(String runnerID, String username, double revenue, String profilePic) {
        this.runnerID = runnerID;
        this.username = username;
        this.revenue = revenue;
        this.profilePic = (profilePic == null || profilePic.isBlank()) ? "default.png" : profilePic;
    }

    // Getters
    public String getRunnerID() {
        return runnerID;
    }

    public String getUsername() {
        return username;
    }

    public double getRevenue() {
        return revenue;
    }

    public String getProfilePic() {
        return profilePic;
    }

    // Setters
    public void setRunnerID(String runnerID) {
        this.runnerID = runnerID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    // Convert to file format
    public String toFileFormat() {
        return String.format("%s | %s | %.2f | %s",
            runnerID, username, revenue, profilePic);
    }

    // Create runner object from file string
    public static Runner fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 4) {
            String runnerID = parts[0].trim();
            String username = parts[1].trim();
            double revenue = Double.parseDouble(parts[2].trim());
            String profilePic = parts[3].trim();

            return new Runner(runnerID, username, revenue, profilePic);
        }
        return null;
    }
}
