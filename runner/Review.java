package foodhub.runner;

public class Review {
    private String runnerID;
    private String customerName;
    private String comment;
    private int rating;
    
    public Review(String runnerID, String customerName, String comment, int rating) {
        this.runnerID = runnerID;
        this.customerName = customerName;
        this.comment = comment;
        this.rating = rating;
    }

    public String getRunnerID() {
        return runnerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}
