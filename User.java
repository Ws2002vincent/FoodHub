/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

/**
 *
 * @author User
 */
public class User {
    private String userID;  
    private String roleID;  
    private String username;
    private String phone;
    private String role;    
    private String hashedPassword;
    private String profilePic;

    // Constructor
    public User(){
    }
    
    public User(String username, String hashedPassword, String role) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    public User(String userID, String roleID, String username, String phone, String role, String hashedPassword, String profilePic) {
            this.userID = userID;
            this.roleID = roleID;
            this.username = username;
            this.phone = phone;
            this.role = role;
            this.hashedPassword = hashedPassword;
            this.profilePic = (profilePic == null || profilePic.isBlank()) ? "default.png" : profilePic;
        }



    // Getters
    public String getUserID() {
        return userID;
    }
    
    public String getRoleID() {
        return roleID;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }


    public String getProfilePic() {
        return profilePic;
    }
    
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Setters
    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public void setRoleID(String userID) {
        this.userID = userID;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    // Method to update the profile picture when the user uploads a new one
    public void updateProfilePicture() {
        this.profilePic = this.userID.toLowerCase() + ".png";
        // remember to update both user and customer database
    }

    // Method to check if this user matches another user's credentials
    public boolean matches(String username, String password) {
        // Hash the entered password and compare with the stored hashed password
        String hashedInputPassword = Encryption.hashPassword(password);
        return this.username.equals(username) && this.hashedPassword.equals(hashedInputPassword);
    }

    public String toFileFormat() {
        return String.format("%s | %s | %s | %s | %s | %s | %s",
            userID, roleID, username, phone, role, hashedPassword, profilePic);
    }

    // Create user object from file string
    public static User fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 6) {
            String userID = parts[0].trim();
            String roleID = parts[1].trim();
            String username = parts[2].trim();
            String phone = parts[3].trim();
            String role = parts[4].trim();
            String hashedPassword = parts[5].trim();

            // If profilePic is missing, default to "default.png"
            String profilePic = (parts.length >= 7) ? parts[6].trim() : "default.png";

            // Use the updated constructor that includes profilePic
            return new User(userID, roleID, username, phone, role, hashedPassword, profilePic);
        }
        return null;
    }
    
        // Convert role code to human-readable role
    public String getRoleName() {
        switch (role) {
            case "0": return "Manager";
            case "1": return "Admin";
            case "2": return "Vendor";
            case "3": return "Runner";
            case "4": return "Customer";
            default:  return "Unknown";
        }
    }
}