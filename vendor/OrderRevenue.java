/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub.vendor;

import java.util.Date;

public class OrderRevenue {
    private String orderId;
    private String customerName;
    private String vendorId;
    private String type;
    private String foodItems; // Food IDs and quantities (can be a list)
    private Date orderDate;
    private double deliveryFee;
    private double foodPrice;
    private String status;
    private boolean isCompleted;

    public OrderRevenue (String orderId, String customerName, String vendorId, String type, String foodItems, Date orderDate, double deliveryFee, double foodPrice, String status, boolean isCompleted) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.vendorId = vendorId;
        this.type = type;
        this.foodItems = foodItems;
        this.orderDate = orderDate;
        this.deliveryFee = deliveryFee;
        this.foodPrice = foodPrice;
        this.status = status;
        this.isCompleted = isCompleted;
    }

    // Getters and setters
    public String getVendorId() {
        return vendorId;
    }
    
    public double getFoodPrice() {
        return foodPrice;
    }

    public double getTotalRevenue() {
        return foodPrice;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
}



