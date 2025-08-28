package foodhub.vendor;

public class Order {

    private String orderId;
    private String customer;
    private String vendorID;
    private String serviceType;
    private String dateTime;
    private double amount;
    private String orderStatus;
    private String orderedItems;

    public Order(String orderId, String customer, String vendorID, String serviceType, String dateTime, double amount, String orderStatus, String orderedItems) {
        this.orderId = orderId;
        this.customer = customer;
        this.vendorID = vendorID;
        this.serviceType = serviceType;
        this.dateTime = dateTime;
        this.amount = amount;
        this.orderStatus = orderStatus;
        this.orderedItems = orderedItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomer() {
        return customer;
    }

    public String getVendorID() {
        return vendorID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getDateTime() {
        return dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderedItems() {
        return orderedItems;
    }
}
