package foodhub.runner;

public class Task {
    private String orderId;
    private String customer;
    private String serviceType;
    private String dateTime;
    private double amount;
    private String orderStatus;
    private String orderedItems;
    private String address;
    private String deliveryFee;

    public Task(String orderId, String customer, String serviceType, String dateTime, double amount, String orderStatus, String orderedItems, String deliveryFee, String address) {
        this.orderId = orderId;
        this.customer = customer;
        this.serviceType = serviceType;
        this.dateTime = dateTime;
        this.amount = amount;
        this.orderStatus = orderStatus;
        this.orderedItems = orderedItems;
        this.deliveryFee = deliveryFee;
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomer() {
        return customer;
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

    public String getAddress() {
        return address;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderedItems(String orderedItems) {
        this.orderedItems = orderedItems;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
