package foodhub.vendor;

public class Feedback {

    private String feedbackID;
    private String orderID;
    private String vendorID;
    private String customerName;
    private int rating;
    private String comment;

    public Feedback(String feedbackID, String orderID, String vendorID, String customerName, int rating, String comment) {
        this.feedbackID = feedbackID;
        this.orderID = orderID;
        this.vendorID = vendorID;
        this.customerName = customerName;
        this.rating = rating;
        this.comment = comment;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

}
