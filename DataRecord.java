/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

class DataRecord {
    private final String userId;
    private final String username;
    private final double revenue;
    

    public DataRecord(String userId, String username, double revenue) {
        this.userId = userId;
        this.username = username;
        this.revenue = revenue;
        
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public double getRevenue() {
        return revenue;
    }
}

class RunnerRecord extends runnerRating {
    private final String runnerId;
    private final String name;
    private final double rating;
    
    public RunnerRecord(String runnerId, String name, double rating) {
        this.runnerId = runnerId;
        this.name = name;
        this.rating = rating;
    }

    public String getRunnerId() {
        return runnerId;
    }

    @Override
    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }
}