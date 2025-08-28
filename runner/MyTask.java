package foodhub.runner;

public class MyTask {
    private String taskID;
    private String runnerID;
    private String orderID;
    private String customerName;
    private String address;
    private String orderedItems;
    private String quests;
    private String status;
    
    public MyTask(String taskID, String runnerID, String orderID, String customerName, String address, String orderedItems, String quests, String status) {
        this.taskID = taskID;
        this.runnerID = runnerID;
        this.orderID = orderID;
        this.customerName = customerName;
        this.address = address;
        this.orderedItems = orderedItems;
        this.quests = quests;
        this.status = status;
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

    public String getOrderedItems() {
        return orderedItems;
    }

    public String getQuests() {
        return quests;
    }

    public String getStatus() {
        return status;
    }
}
