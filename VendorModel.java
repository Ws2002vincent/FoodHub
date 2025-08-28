/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

public class VendorModel {
    private String userId;
    private String username;
    private double revenue;
    private String status;

    // Constructor
    public VendorModel(String userId, String username, double revenue, String status) {
        this.userId = userId;
        this.username = username;
        this.revenue = revenue;
        this.status = status;
    }
    
    // Getters and Setters
     public String getStatus() {
        return status;
    }
     
    public void setStatus(String status) {    
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "VendorModel{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", revenue=" + revenue + '\'' +
                ", status='" + status + 
                '}';
    }
}
