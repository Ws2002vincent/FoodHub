package foodhub.runner;

public class TaskHistory {
    private String taskID;
    private String runnerID;
    private String orderID;
    private String customerName;
    private String address;
    private String status;
    private String deliveredDate;
    private String deliveryFee;
    private String orderedItems;
    
    public TaskHistory(String taskID, String runnerID, String orderID, String customerName, String address, String status, String deliveredDate, String deliveryFee, String orderedItems) {
        this.taskID = taskID;
        this.runnerID = runnerID;
        this.orderID = orderID;
        this.customerName = customerName;
        this.address = address;
        this.status = status;
        this.deliveredDate = deliveredDate;
        this.deliveryFee = deliveryFee;
        this.orderedItems = orderedItems;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getRunnerID() {
        return runnerID;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public String getOrderedItems() {
        return orderedItems;
    }
}
